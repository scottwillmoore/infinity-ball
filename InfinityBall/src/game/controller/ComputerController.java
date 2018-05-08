package game.controller;

import game.entity.Ball;
import game.entity.Entity;
import game.main.Settings;
import game.screen.GameScreen;

public class ComputerController extends Controller {

	public int side;
	
	public ComputerController(int side) {
		
		this.side = side;
	}
	
	@Override
	public void control(GameScreen game, Entity p, float delta) {

		Ball nearest = null;
		float sml = Float.POSITIVE_INFINITY;
		for(Ball b : game.court.getEntityType(Ball.class)) {
			
			float dx = p.getCX() - b.getCX();
			float ticks = dx / b.vx;
			
			boolean movingToward = side < 0 ? b.vx < 0 : b.vx > 0;
			//boolean makeIt = p.y + (Settings.AI_SPEED * ticks) > b.y + (b.vy * ticks);
			if(ticks < sml && movingToward) {
				
				nearest = b;
				sml = ticks;
			}
		}
		
		if(nearest != null) {
			
			float disToBall = p.getCX() - nearest.getCX();
			float ticks = disToBall / nearest.vx;
			
			float ty = nearest.getCY() + (nearest.vy * ticks);
			
			float disToCollision = p.getCY() - ty;
			float vy = (float) (-(disToCollision / ticks) * ((Math.random() / 2) + 1));
			
			p.vy = Math.abs(vy) < Settings.AI_SPEED ? vy : disToCollision < 0 ? Settings.AI_SPEED : -Settings.AI_SPEED;
			
		} else {
			
			if(p.getCY() > game.camera.viewportHeight / 2 + 7.5) {
				
				p.vy = -Settings.AI_SPEED;
			} else if(p.getCY() < game.camera.viewportHeight / 2 - 7.5) {
				
				p.vy = Settings.AI_SPEED;
			} else {
				
				p.vy = 0;
				p.y = game.camera.viewportHeight / 2 - p.height / 2;
			}
		}
	}

}
