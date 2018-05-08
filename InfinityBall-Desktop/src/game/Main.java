package game;

import game.main.InfinityBall;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	
	public static void main(String[] args) {
		
		/* Create Configurations for Game */
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		/* Get Display Mode */
		DisplayMode dm = LwjglApplicationConfiguration.getDesktopDisplayMode();
		
		/* Set Configurations for Game */
		cfg.title = InfinityBall.NAME + " [" + InfinityBall.VERSION + "]";
		
		cfg.useGL20 = false;
		cfg.resizable = true;
		cfg.samples = 3;
		
		cfg.fullscreen = true;
		cfg.width = (int) dm.width;
		cfg.height = (int) dm.height;
		
		//cfg.fullscreen = false;
		//cfg.width = 1440;
		//cfg.height = 810;

		/* Launch Game */
		new LwjglApplication(new InfinityBall(), cfg);
	}
}
