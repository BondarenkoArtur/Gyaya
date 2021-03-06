package ga.uabart.gyaya;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import ga.uabart.gyaya.Screens.PlayScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Gyaya extends Game {
	public SpriteBatch batch;
	private PlayScreen playScreen;

	public static final int V_WIDTH = 240;
	public static final int V_HEIGHT = 160;

	public static final float PPM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;

	public static AssetManager manager;
	public int level = 1;
	public boolean changeLvl;
    private static boolean debugMode = false;
    private static boolean drawControls = false;
    public static String debugString = "";

    public Gyaya(boolean drawControls) {
        setDrawControls(drawControls);
    }

    public Gyaya() {
    }

    @Override
	public void create () {
//        Irc irc = new Irc(this);
//        irc.init();
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/background.ogg", Music.class);
		manager.load("audio/sounds/uaBArt - Coin.mp3", Sound.class);
		manager.load("audio/sounds/uaBArt - Miss.mp3", Sound.class);
        manager.load("audio/sounds/uaBArt - Bell.mp3", Sound.class);
        manager.finishLoading();
		playScreen = new PlayScreen(this, "level1");
		setScreen(playScreen);
	}

	@Override
	public void render () {
		super.render();
		if (changeLvl)
			changeLevel();
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}

	public void changeLevel() {
		changeLvl = false;
		level++;
		if (level > 3) level = 1;
		playScreen = new PlayScreen(this, "level" + level);
		setScreen(playScreen);
	}

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setDebugMode(boolean debugMode) {
        Gyaya.debugMode = debugMode;
    }

    public static void switchDebug() {
        setDebugMode(!debugMode);
    }

    public static boolean isDrawControls() {
        return drawControls;
    }

    public static void setDrawControls(boolean drawControls) {
        Gyaya.drawControls = drawControls;
    }

    public static void switchDrawControls() {
        setDrawControls(!drawControls);
    }

}
