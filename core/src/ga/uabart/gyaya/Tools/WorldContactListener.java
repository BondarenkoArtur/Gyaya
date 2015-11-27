package ga.uabart.gyaya.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import ga.uabart.gyaya.Gyaya;
import ga.uabart.gyaya.Sprites.Enemy;
import ga.uabart.gyaya.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {

    private Gyaya gyaya;

    public WorldContactListener(Gyaya gyaya) {
        this.gyaya = gyaya;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case Gyaya.ENEMY_HEAD_BIT | Gyaya.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Gyaya.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
                else
                    ((Enemy)fixB.getUserData()).hitOnHead();
                break;
            case Gyaya.PLAYER_BIT | Gyaya.BRICK_BIT:
            case Gyaya.PLAYER_BIT | Gyaya.COIN_BIT:
                if (fixA.getUserData() == "head")
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit();
                else if (fixB.getUserData() == "head")
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit();
                break;
            case Gyaya.ENEMY_BIT | Gyaya.BRICK_BIT:
            case Gyaya.ENEMY_BIT | Gyaya.COIN_BIT:
                if (fixA.getFilterData().categoryBits == Gyaya.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Gyaya.ENEMY_BIT | Gyaya.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Gyaya.PLAYER_BIT | Gyaya.ENEMY_BIT:
                Gdx.app.log("Player", "died");
                break;
            case Gyaya.PLAYER_BIT | Gyaya.OBJECT_BIT:
                if (fixA.getUserData() == "exit" || fixB.getUserData() == "exit"){
                    Gdx.app.log("Player", "exit");
                    gyaya.changeLvl = true;
                }
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
