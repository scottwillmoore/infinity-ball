package game.entity;

import game.main.Colors;
import game.main.Renderer;
import game.screen.GameScreen;

public class Solid extends Entity {

	public Solid(GameScreen game, float x, float y, float width, float height) {

		super(game);
		
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		color = Colors.SOLID;
	}

	@Override
	public void update(float delta) {

		
	}

	@Override
	public void render(Renderer renderer) {

		renderer.setColor(color);
		renderer.fillRect(x, y, width, height);
	}

	
}
