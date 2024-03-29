package ga.uabart.gyaya.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import ga.uabart.gyaya.Gyaya;
import ga.uabart.gyaya.Screens.PlayScreen;

public class Player extends Sprite {

    private static final Array<String> CHARS = new Array<String>(new String[]{"CharCyan", "CharBlue", "CharPink", "CharYellow", "CharWhite"});

    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD };
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private TextureRegion playerDead;
    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerJump;
    private Animation<TextureRegion> playerFalling;
    private boolean runningRight;
    private float stateTimer;
    private boolean playerIsDead;
    public boolean ableToJump;

    private String charName;
    private String charNameFace;
    private TextureRegion faceTexture;

    public Player(PlayScreen screen){
        charName = CHARS.get(MathUtils.random(0, 4));
        charNameFace = charName + "Face";
//        Gdx.app.log("charnameface", charNameFace);
        faceTexture = new TextureRegion(screen.getAtlas().findRegion(charNameFace), 0, 0, 23, 23);
        world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 9; i < 11; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion(charName), i * 23, 0, 23, 23));
        playerRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 1; i < 3; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion(charName), i * 23, 0, 23, 23));
        playerJump = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 7; i < 9; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion(charName), i * 23, 0, 23, 23));
        playerFalling = new Animation(0.1f, frames);
        frames.clear();

        definePlayer();
        playerStand = new TextureRegion(screen.getAtlas().findRegion(charName), 0, 0, 23, 23);
        playerDead = new TextureRegion(screen.getAtlas().findRegion(charName), 4 * 23, 0, 23, 23);
        setBounds(0, 0, 23 / Gyaya.PPM, 23 / Gyaya.PPM);
        setRegion(playerStand);
    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 2 / Gyaya.PPM);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case DEAD:
                region = playerDead;
                break;
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = playerFalling.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = playerStand;
                break;
        }

        if ((b2body.getLinearVelocity().x < -0.1f || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        else if ((b2body.getLinearVelocity().x > 0.1f || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;

        return region;

    }

    public void hit(){
        Gyaya.manager.get("audio/music/background.ogg", Music.class).stop();
        Gyaya.manager.get("audio/sounds/uaBArt - Bell.mp3", Sound.class).play();
        playerIsDead = true;
        Filter filter = new Filter();
        filter.maskBits = Gyaya.NOTHING_BIT;
        for (Fixture fixture : b2body.getFixtureList())
            fixture.setFilterData(filter);
        b2body.setLinearVelocity(new Vector2(0, 4f));
    }

    public boolean isDead(){
        return playerIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public TextureRegion getFaceTexture() {
        return faceTexture;
    }


    public State getState() {
        if (playerIsDead)
            return State.DEAD;
        else if (b2body.getLinearVelocity().y > 0.1)
//                || b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < -0.1)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    private void definePlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / Gyaya.PPM, 32 / Gyaya.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8f / Gyaya.PPM);
        fixtureDef.filter.categoryBits = Gyaya.PLAYER_BIT;
        fixtureDef.filter.maskBits = Gyaya.GROUND_BIT | Gyaya.BRICK_BIT | Gyaya.COIN_BIT |
                Gyaya.OBJECT_BIT | Gyaya.ENEMY_BIT | Gyaya.ENEMY_HEAD_BIT ;

        fixtureDef.shape = shape;

        b2body.createFixture(fixtureDef);
        shape.setPosition(new Vector2(0, 4 / Gyaya.PPM));
        shape.setRadius(6f / Gyaya.PPM);
        b2body.createFixture(fixtureDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-3 / Gyaya.PPM, 10f / Gyaya.PPM), new Vector2(3 / Gyaya.PPM, 10f / Gyaya.PPM));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;
        b2body.createFixture(fixtureDef).setUserData("head");

        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-6 / Gyaya.PPM, -9f / Gyaya.PPM), new Vector2(6 / Gyaya.PPM, -9f / Gyaya.PPM));
        fixtureDef.shape = feet;
        fixtureDef.isSensor = true;
        b2body.createFixture(fixtureDef).setUserData("feet");
    }

    public void setAbleToJump(boolean ableToJump) {
        this.ableToJump = ableToJump;
    }
}
