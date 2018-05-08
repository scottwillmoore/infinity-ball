package game.powerup;

import game.entity.Ball;
import game.main.Renderer;
import game.screen.GameScreen;

import java.awt.geom.Rectangle2D;

public abstract class Powerup {
	
	public float x, y;
	public float size;
	
	public String textureName;
	
	private GameScreen game;
	
	public Powerup(GameScreen game) {
		
		this.game = game;
		
		size = 64;
	}
	
	public abstract void onHit(Ball b);
	
	public boolean collidesWithBall(Ball b) {
		
		Rectangle2D.Float ball = new Rectangle2D.Float(b.x, b.y, b.width, b.height);
		Rectangle2D.Float powerup = new Rectangle2D.Float(x - size / 2, y - size / 2, size, size);
		
		return ball.intersects(powerup);
	}
	
	public void render(Renderer renderer) {
		
		renderer.drawTexturedRect(game.textures.getPowerup(textureName), x - size / 2, y - size / 2, size, size);
	}
}
