package ga.uabart.gyaya.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import ga.uabart.gyaya.Gyaya;
import ga.uabart.gyaya.Screens.PlayScreen;

/**
 * Created by Arthur on 11/27/2015.
 */
public class Slime extends Enemy {
    private  float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    public Slime(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 3 ; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("SlimePink"), i*23, 0, 23, 23 ));
            walkAnimation = new Animation(0.2f, frames);
            stateTime = 0;
            setBounds(getX(), getY(), 16 / Gyaya.PPM, 16 / Gyaya.PPM);
        }
    }

    public void update(float delta){
        stateTime += delta;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 2 / Gyaya.PPM);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
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
        b2body.createFixture(fixtureDef);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-3.5f, 4.5f).scl(1 / Gyaya.PPM);
        vertice[1] = new Vector2(3.5f, 4.5f).scl(1 / Gyaya.PPM);
        vertice[2] = new Vector2(-2f, 2f).scl(1 / Gyaya.PPM);
        vertice[3] = new Vector2(2f, 2f).scl(1 / Gyaya.PPM);
        head.set(vertice);
        fixtureDef.shape = head;
        fixtureDef.restitution = 0.5f;
        fixtureDef.filter.categoryBits = Gyaya.ENEMY_HEAD_BIT;
        b2body.createFixture(fixtureDef).setUserData(this);
    }
}
