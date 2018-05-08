package game.effect;

import game.screen.GameScreen;

public class Shaker {
	
	private GameScreen game;
	
	private float x, y;
	private float vx, vy;
	
	private float drag = 5f; // Length of Shake (lower = longer)
	private float elasticity = 100f; // Intensity of Shake (lower = stronger)
	
	public Shaker(GameScreen game) {
		
		this.game = game;
	}
	
	public void shake(float powerX, float powerY) {
		
		vx += powerX;
		vy += powerY;
	}
	
	public void shakeRandom(float power) {
		
		vx = (float) (power * (Math.random() - 0.5f) * 60);
		vy = (float) (power * (Math.random() - 0.5f) * 60);
	}
	
	public void reset() {
		
		x = y = 0;
		vx = vy = 0;
	}
	
	public void update(float delta) {
		
		vx -= vx * drag * delta;
		vy -= vy * drag * delta;
		
		vx -= x * elasticity * delta;
		vy -= y * elasticity * delta;
		
		x += vx * delta;
		y += vy * delta;
		
		game.camera.translate(vx * delta, vy * delta);
	}
}
