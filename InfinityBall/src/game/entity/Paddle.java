package game.entity;

import game.controller.Controller;
import game.main.Colors;
import game.main.Renderer;
import game.screen.GameScreen;

public class Paddle extends Solid {

	public Controller controller;
	
	public Paddle(GameScreen game, Controller controller, float x, float y, float width, float height) {

		super(game, x, y, width, height);
		
		this.controller = controller;
		
		color = Colors.PADDLE;
	}
	
	@Override
	public void update(float delta) {
		
		super.update(delta);
		
		controller.control(game, this, delta);
		
		x += vx * delta * 10;
		y += vy * delta  * 10;
		
		if(y < 5) {
			
			y = 5;
		}
		if(y + height > game.camera.viewportHeight - 5) { 
			
			y = game.camera.viewportHeight - height - 5;
		}
	}
	
	@Override
	public void render(Renderer renderer) {
		
		super.render(renderer);
	}
	
	public void setController(Controller controller) {
		
		this.controller = controller;
	}
}
