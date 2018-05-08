package game.powerup;

import game.entity.Ball;
import game.screen.GameScreen;

public class BiggerPaddle extends Powerup {

	public BiggerPaddle(GameScreen game, float x, float y) {

		super(game);
		
		this.x = x;
		this.y = y;
		this.size = 64;
		this.textureName = "bigger";
	}

	@Override
	public void onHit(Ball b) {

		if(b.lastTouchedPaddle != null) {

			b.lastTouchedPaddle.height = 150f;
		}
	}

}
