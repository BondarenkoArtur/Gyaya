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

	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;

	public static AssetManager manager;

	@Override
	public void create () {
//        Irc irc = new Irc(this);
//        irc.init();
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/background.ogg", Music.class);
		manager.load("audio/sounds/uaBArt - Coin.mp3", Sound.class);
		manager.load("audio/sounds/uaBArt - Miss.mp3", Sound.class);
		manager.finishLoading();
		playScreen = new PlayScreen(this);
		setScreen(playScreen);
	}

	@Override
	public void render () {
		super.render();

	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}
}
