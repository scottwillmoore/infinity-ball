package game.gui;

import game.controller.PlayerController;
import game.entity.Ball;
import game.main.Renderer;
import game.main.Stats;
import game.screen.GameScreen;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.primitives.MutableFloat;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

public class Countdown extends Gui {

	public boolean twoPlayer;
	
	public float count;
	public MutableFloat size;
	public MutableFloat alpha;
	
	public Countdown(boolean twoPlayer) {
		
		this.twoPlayer = twoPlayer;
	}
	
	@Override
	public void create(final GameScreen game) {
		
		super.create(game);
		
		count = 3;
		size = new MutableFloat(0f);
		alpha = new MutableFloat(0f);
		
		Timeline.createSequence()
			.push(Timeline.createParallel()
					.push(Tween.set(size, 0).target(15f))
					.push(Tween.set(alpha, 0).target(0f)))
			.pushPause(0.1f)
			.push(Timeline.createParallel()
				.push(Tween.to(size, 0, 1f).target(5f).ease(Back.OUT))
				.push(Tween.to(alpha, 0, 1f).target(1f)))
			.repeat((int) count - 1, 0f)
			.setCallbackTriggers(TweenCallback.END | TweenCallback.COMPLETE)
			.setCallback(new TweenCallback() {

				@Override
				public void onEvent(int type, BaseTween<?> source) {

					if(type == TweenCallback.END) {
						
						count -= 1;
					}
					if(type == TweenCallback.COMPLETE) {
						
						game.setGui(null);
						game.court.leftPaddle.setController(new PlayerController(Keys.W, Keys.S));
						if(twoPlayer) {
							
							game.court.rightPaddle.setController(new PlayerController(Keys.UP, Keys.DOWN));
						}
						
						Stats.LEFT_SCORE = 0;
						Stats.RIGHT_SCORE = 0;
					}
				}
			})
		.start(game.tweenManager);
	}
	
	@Override
	public void exitTo(Gui gui) {
		
		if(!exiting) {
			
			exiting = true;
			game.tweenManager.killAll();
			game.setGui(gui);
		}
	}
	
	@Override
	public void update(float delta) {
		
		super.update(delta);
		
		game.court.removeEntityType(Ball.class);
	}
	
	@Override
	public void render(Renderer renderer) {
		
		super.render(renderer);
		
		renderer.setColor(Color.WHITE.cpy().sub(0, 0, 0, alpha.floatValue()));
		renderer.drawCenteredText(String.valueOf((int) count), game.camera.viewportWidth / 2 + 10, game.camera.viewportHeight / 2, size.floatValue() + 0.1f);
	}
}
