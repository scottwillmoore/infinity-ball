package game.entity;

import game.main.Renderer;
import game.screen.GameScreen;

import com.badlogic.gdx.graphics.Color;

public abstract class Entity {
	
	public float x, y;
	public float width, height;
	
	public float vx, vy;
	
	public Color color;
	
	public boolean remove;
	
	public GameScreen game;
	
	public Entity(GameScreen game) {
		
		this.game = game;
		
		color = Color.WHITE;
	}
	
	public abstract void update(float delta);
	
	public abstract void render(Renderer renderer);
	
	public float getCX() {
		
		return x + (width / 2);
	}
	
	public float getCY() {
		
		return y + (height / 2);
	}
}
