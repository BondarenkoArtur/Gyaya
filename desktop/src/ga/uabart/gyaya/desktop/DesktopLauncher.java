package ga.uabart.gyaya.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ga.uabart.gyaya.Gyaya;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 480*2;
		config.height = 320*2;
		config.title = "Gyaya";
		new LwjglApplication(new Gyaya(), config);
	}
}
