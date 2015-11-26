package ga.uabart.gyaya.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ga.uabart.gyaya.Gyaya;
import ga.uabart.gyaya.Scenes.Hud;
import ga.uabart.gyaya.Screens.PlayScreen;

/**
 * Created by Arthur on 11/4/2015.
 */
public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 234;

    public Coin(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        tileSet = map.getTileSets().getTileSet("sprites");
        fixture.setUserData(this);
        setCategoryFilter(Gyaya.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        if (getCell().getTile().getId() == BLANK_COIN)
            Gyaya.manager.get("audio/sounds/uaBArt - Miss.mp3", Sound.class).play();
        else {
            Gyaya.manager.get("audio/sounds/uaBArt - Coin.mp3", Sound.class).play();
            Hud.addScore(100);
        }

        getCell().setTile(tileSet.getTile(BLANK_COIN));
    }
}
