package game.particle;

import com.badlogic.gdx.graphics.Color;

public class Particle {

	public float x, y;
	public float vx, vy;
	public float width, height;
	public float life, duration;
	
	public Color color;
	
	public Particle() {
		
		x = 0f;
		y = 0f;
		
		width = 6f;
		height = 6f;
		
		life = 0f;
		duration = 0f;
	}
	
	public boolean isAlive() {
		
		return duration <= life;
	}
}
