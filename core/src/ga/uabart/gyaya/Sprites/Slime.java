package ga.uabart.gyaya.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import ga.uabart.gyaya.Gyaya;
import ga.uabart.gyaya.Screens.PlayScreen;

public class Slime extends Enemy {
    public static final String SLIME_NAME = "SlimePink";
    private  float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Slime(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 3 ; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion(SLIME_NAME), i*23, 0, 23, 23 ));
            walkAnimation = new Animation(0.2f, frames);
            stateTime = 0;
            setBounds(getX(), getY(), 16 / Gyaya.PPM, 16 / Gyaya.PPM);
        }
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float delta){
        TextureRegion region = null;
        stateTime += delta;
        if (setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            region = new TextureRegion(screen.getAtlas().findRegion(SLIME_NAME), 69, 0, 23, 23);
            setRegion(region);
            stateTime = 0;
        }
        else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 2 / Gyaya.PPM);
            region = walkAnimation.getKeyFrame(stateTime, true);
            setRegion(region);
            if (b2body.getLinearVelocity().x < -0.1f && region.isFlipX()){
                region.flip(true, false);
            }
            else if (b2body.getLinearVelocity().x > 0.1f && !region.isFlipX()){
                region.flip(true, false);
            }
        }
    }

    @Override
    public void draw(Batch batch) {
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4f / Gyaya.PPM);
        fixtureDef.filter.categoryBits = Gyaya.ENEMY_BIT;
        fixtureDef.filter.maskBits = Gyaya.GROUND_BIT | Gyaya.BRICK_BIT | Gyaya.COIN_BIT |
                Gyaya.ENEMY_BIT | Gyaya.OBJECT_BIT | Gyaya.PLAYER_BIT;
        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-1f, 4.5f).scl(1 / Gyaya.PPM);
        vertice[1] = new Vector2(1f, 4.5f).scl(1 / Gyaya.PPM);
        vertice[2] = new Vector2(-1f, 2f).scl(1 / Gyaya.PPM);
        vertice[3] = new Vector2(1f, 2f).scl(1 / Gyaya.PPM);
        head.set(vertice);
        fixtureDef.shape = head;
        fixtureDef.restitution = 0.5f;
        fixtureDef.filter.categoryBits = Gyaya.ENEMY_HEAD_BIT;
        b2body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }
}
