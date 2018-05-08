package game.main;

import game.controller.ComputerController;
import game.entity.Ball;
import game.entity.Entity;
import game.entity.Paddle;
import game.powerup.BiggerPaddle;
import game.powerup.Powerup;
import game.screen.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;

public class Court {
	
	public GameScreen game;
	
	public Paddle leftPaddle;
	public Paddle rightPaddle;
	
	protected ArrayList<Entity> entities;
	protected ArrayList<Powerup> powerups;
	
	public Court(GameScreen game) {
		
		this.game = game;
	}
	
	public void init() {
		
		/* Initialize Paddles */
		leftPaddle = new Paddle(game, new ComputerController(-1), 50f, (game.camera.viewportHeight - 5) / 2 - 50, 25f, 100f);
		rightPaddle = new Paddle(game, new ComputerController(1), game.camera.viewportWidth - 75, (game.camera.viewportHeight - 5) / 2 - 50, 25f, 100f);
		
		/* Initialize Entity List */
		entities = new ArrayList<Entity>();
		
		/* Initialize Powerups List */
		powerups = new ArrayList<Powerup>();
		
		/* Initialize Balls */
		entities.add(new Ball(game, game.camera.viewportWidth / 2, game.camera.viewportHeight / 2, 10f));
	}
	
	public void dispose() {
		
		
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Entity> ArrayList<E> getEntityType(Class<E> type) {
		
		ArrayList<E> match = new ArrayList<E>();
		
		Iterator<Entity> i = entities.iterator();
		while(i.hasNext()) {
			
			Entity e = i.next();
			if(type.isInstance(e)) {
				
				match.add((E) e);
			}
		}
		
		return match;
	}
	
	public void addEntity(Entity e) {
		
		entities.add(e);
	}
	
	public void addPowerup(Powerup p) {
		
		powerups.add(p);
	}
	
	public void removeEntityType(Class<? extends Entity> type) {
		
		Iterator<Entity> i = entities.iterator();
		while(i.hasNext()) {
			
			if(type.isInstance(i.next())) {
				
				i.remove();
			}
		}
	}
	
	public ArrayList<Entity> getEntities() {
		
		return entities;
	}
	
	public ArrayList<Powerup> getPowerups() {
		
		return powerups;
	}
	
	public void update(float delta) {
		
		/* Update Entities */
		Iterator<Entity> i = entities.iterator();
		while(i.hasNext()) {
			
			Entity e = i.next();
			if(e.remove) {
				
				i.remove();
				continue;
			}
			e.update(delta);
		}
		
		/* Update Powerups */
		for(Powerup powerup : powerups) {
			
			for(Ball b : getEntityType(Ball.class)) {
				
				if(powerup.collidesWithBall(b)) {
					
					powerup.onHit(b);
				}
			}
		}
		
		/* Update Paddles */
		leftPaddle.update(delta);
		rightPaddle.update(delta);
		
		/* If No Balls In Court - Add One */
		if(getEntities().size() == 0) {
			
			removeEntityType(Ball.class);
			addEntity(new Ball(game, game.camera.viewportWidth / 2, game.camera.viewportHeight / 2, 10f));
		}
	}
	
	public void render(Renderer renderer) {
		
		/* Render Background and Foreground */
		renderer.setColor(Colors.BACKGROUND);
		renderer.fillRect(0, 0, game.camera.viewportWidth, game.camera.viewportHeight);

		renderer.setColor(Colors.FOREGROUND);
		renderer.fillRect(0 + 5, 0 + 5, game.camera.viewportWidth - 10, game.camera.viewportHeight - 10);
		
		/* Render Net */
		renderer.setColor(Colors.BACKGROUND);
		for(int i = 0; i < game.camera.viewportHeight / 20; i++) {
			
			renderer.fillRect(game.camera.viewportWidth / 2 - 2.5f, i * 40, 5f, 20);
		}
		
		/* Update Powerups */
		for(Powerup powerup : powerups) {

			powerup.render(renderer);
		}
		
		/* Render Paddles */
		leftPaddle.render(renderer);
		rightPaddle.render(renderer);
		
		/* Render Entities */
		Iterator<Entity> i = entities.iterator();
		while(i.hasNext()) {
			
			Entity e = i.next();
			e.render(renderer);
		}
	}
}
