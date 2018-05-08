package game.gui;

import game.controller.ComputerController;
import game.main.Colors;
import game.main.Renderer;
import game.screen.GameScreen;
import game.tween.ActorAccessor;
import game.tween.Vector2Accessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class Menu extends Gui {

	Vector2 titlePos;
	
	TextButton onePlayer;
	TextButton twoPlayer;
	TextButton options;
	TextButton exit;
	
	@Override
	public void create(final GameScreen game) {
		
		super.create(game);
		
		game.court.leftPaddle.setController(new ComputerController(-1));
		game.court.rightPaddle.setController(new ComputerController(1));
		
		/* Create Position For Title */
		titlePos = new Vector2(game.camera.viewportWidth / 2, -100);
		
		/* One Player Button */
		onePlayer = new TextButton("One Player", skin);
		onePlayer.setPosition(-700, 200);
		onePlayer.setSize(600, 50);
		onePlayer.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				onePlayer.setChecked(false);
				tweenExit(0);
			}
		});
		
		/* Two Player Button */
		twoPlayer = new TextButton("Two Player", skin);
		twoPlayer.setPosition(-700, 275);
		twoPlayer.setSize(600, 50);
		twoPlayer.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				twoPlayer.setChecked(false);
				tweenExit(1);
			}
		});
		
		/* Options Button */
		options = new TextButton("Options", skin);
		options.setPosition(-700, 350);
		options.setSize(600, 50);
		options.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				options.setChecked(false);
				tweenExit(2);
			}
		});
		
		/* Exit Button */
		exit = new TextButton("Exit", skin);
		exit.setPosition(-700, 425);
		exit.setSize(600, 50);
		exit.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				exit.setChecked(false);
				tweenExit(3);
			}
		});
		
		/* Add UI Elements */
		stage.addActor(onePlayer);
		stage.addActor(twoPlayer);
		stage.addActor(options);
		stage.addActor(exit);
		
		tweenEntrance();
	}
	
	public void tweenEntrance() {
		
		float cx = GameScreen.WIDTH / 2 - onePlayer.getWidth() / 2;
		
		Timeline.createParallel()
		.push(Timeline.createSequence()
				.pushPause(0f)
				.push(Tween.to(onePlayer, ActorAccessor.POSITION, 1).target(cx, 200).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(0.5f)
				.push(Tween.to(twoPlayer, ActorAccessor.POSITION, 1).target(cx, 275).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(1f)
				.push(Tween.to(options, ActorAccessor.POSITION, 1).target(cx, 350).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(1.5f)
				.push(Tween.to(exit, ActorAccessor.POSITION, 1).target(cx, 425).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(0)
				.push(Tween.to(titlePos, Vector2Accessor.POSITION, 1).target(game.camera.viewportWidth / 2, 100).ease(Back.OUT)))
		.start(game.tweenManager);
	}
	
	public void tweenExit(final int changeTo) {
		
		Timeline.createParallel()
		.push(Timeline.createSequence()
				.pushPause(0f)
				.push(Tween.to(onePlayer, ActorAccessor.POSITION, 1).target(960, 200).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(0.5f)
				.push(Tween.to(twoPlayer, ActorAccessor.POSITION, 1).target(960, 275).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(1f)
				.push(Tween.to(options, ActorAccessor.POSITION, 1).target(960, 350).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(1.5f)
				.push(Tween.to(exit, ActorAccessor.POSITION, 1).target(960, 425).ease(Back.IN)))
				.pushPause(3f)
		.push(Timeline.createSequence()
				.pushPause(0)
				.push(Tween.to(titlePos, Vector2Accessor.POSITION, 1).target(game.camera.viewportWidth / 2, -100).ease(Back.IN)))
		.setCallback(new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {

				if(type == TweenCallback.COMPLETE) {
					
					switch(changeTo) {
						case 0: game.setGui(new Countdown(false)); break;
						case 1: game.setGui(new Countdown(true)); break;
						case 2: game.setGui(new Options()); break;
						case 3: Gdx.app.exit(); break;
						default: break;
					}
				}
			}
		})
		.start(game.tweenManager);
	}
	
	@Override
	public void render(Renderer renderer) {
		
		super.render(renderer);
		
		renderer.setColor(Color.WHITE);
		renderer.drawCenteredText("Infinity Ball", titlePos.x, titlePos.y, 2f);
		renderer.setColor(Colors.SOLID);
		renderer.fillRect(titlePos.x - 300, titlePos.y + 30, 600, 5);
		renderer.fillRect(titlePos.x - 300, titlePos.y - 30, 600, 5);
	}
}
