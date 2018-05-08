package game.gui;

import game.main.Renderer;
import game.screen.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Gui {	
	
	public Stage stage;
	public Skin skin;
	
	protected GameScreen game;
	
	protected boolean exiting = false;
	
	public void create(GameScreen game) {
		
		this.game = game;
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		resize(game.camera);
		
		skin = new Skin();
		TextureAtlas ui = new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas"), true);

		skin.addRegions(ui);
		skin.add("default-font", game.renderer.getFont());
		skin.load(Gdx.files.internal("ui/uiskin.json"));
	}
	
	public void exitTo(Gui gui) {
		
		
	}
	
	public void update(float delta) {
		
		stage.act(delta);
	}
	
	public void render(Renderer renderer) {
		
		stage.draw();
	}
	
	public void resize(OrthographicCamera camera) {
		
		stage.setCamera(camera);
		stage.setViewport(camera.viewportWidth, camera.viewportHeight, false);
	}
	
	public void dispose() {
		
		stage.dispose();
	}
}
