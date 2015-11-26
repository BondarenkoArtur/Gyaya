package ga.uabart.gyaya.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import ga.uabart.gyaya.Screens.PlayScreen;

/**
 * Created by Arthur on 11/27/2015.
 */
public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    protected Body b2body;
    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
    }

    protected abstract void defineEnemy();
}
