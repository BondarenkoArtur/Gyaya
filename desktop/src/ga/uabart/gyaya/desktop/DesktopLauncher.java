package ga.uabart.gyaya.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ga.uabart.gyaya.Gyaya;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//config.setWidth(480*2);
		//config.setHeight(320*2);
		config.setForegroundFPS(60);
		config.setTitle("Gyaya");
		new Lwjgl3Application(new Gyaya(), config);
	}
}
