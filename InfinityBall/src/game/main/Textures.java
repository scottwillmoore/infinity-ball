package game.main;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {
	
	public Texture powerups;
	
	public HashMap<String, TextureRegion> powerupTextures;
	
	public void init() {

		powerups = new Texture(Gdx.files.internal("res/powerups.png"));
		
		powerupTextures = new HashMap<String, TextureRegion>();
		powerupTextures.put("bigger", new TextureRegion(powerups, 0, 0, 32, 32));
		powerupTextures.put("smaller", new TextureRegion(powerups, 33, 0, 32, 32));
		powerupTextures.put("balls", new TextureRegion(powerups, 0, 33, 32, 32));
		powerupTextures.put("three", new TextureRegion(powerups, 33, 33, 32, 32));
	}
	
	public TextureRegion getPowerup(String type) {
		
		return powerupTextures.get(type);
	}
	
	public void dispose() {

		powerups.dispose();
	}
}
