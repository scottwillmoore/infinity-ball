package game.main;

import game.screen.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Version;

public class InfinityBall extends Game {

	public static final String NAME = "Infinity Ball";
	public static final String VERSION = "0.8b";
	public static final String LOG = "[Game]";
	
	@Override
	public void create() {

		Gdx.app.log(LOG, "Creating game using libGDX version: " + Version.VERSION);
		
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {

		super.dispose();
		this.getScreen().dispose();
		
		Gdx.app.log(LOG, "Disposing game.");
	}

	@Override
	public void render() {

		super.render();
	}

	@Override
	public void resize(int width, int height) {

		super.resize(width, height);
		Gdx.app.log(LOG, "Resizing to " + width + " x " + height);
	}

	@Override
	public void pause() {

		super.pause();
		Gdx.app.log(LOG, "Pausing game.");
	}

	@Override
	public void resume() {

		super.resume();
		Gdx.app.log(LOG, "Resuming game.");
	}
}
