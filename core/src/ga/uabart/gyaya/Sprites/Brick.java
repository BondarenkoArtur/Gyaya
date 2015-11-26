package ga.uabart.gyaya.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ga.uabart.gyaya.Gyaya;
import ga.uabart.gyaya.Scenes.Hud;
import ga.uabart.gyaya.Screens.PlayScreen;

/**
 * Created by Arthur on 11/4/2015.
 */
public class Brick extends InteractiveTileObject {

    public Brick(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Gyaya.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(Gyaya.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(10);
        Gyaya.manager.get("audio/sounds/uaBArt - Miss.mp3", Sound.class).play();
    }

}
