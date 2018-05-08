package game.gui;

import game.entity.Ball;
import game.main.Colors;
import game.main.Renderer;
import game.main.Settings;
import game.screen.GameScreen;
import game.tween.ActorAccessor;
import game.tween.Vector2Accessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Options extends Gui {
	
	Vector2 titlePos;
	
	Label volumeLabel;
	Slider volumeSlider;
	
	Label difficultyLabel;
	Slider difficultySlider;
	
	Label screenShakeLabel;
	Button screenShake;
	
	Label ballTrailLabel;
	Button ballTrail;
	
	Label particleEffectLabel;
	Button particleEffect;
	
	Label powerupLabel;
	Button powerup;
	
	@Override
	public void create(final GameScreen game) {
		
		super.create(game);
		
		/* Create Position For Title */
		titlePos = new Vector2(game.camera.viewportWidth / 2, -100);
		
		/* Volume Label */
		volumeLabel = new Label("Volume", skin);
		volumeLabel.setPosition(-600, 175);
		volumeLabel.setSize(600, 16);
		volumeLabel.setFontScale(1f);
		volumeLabel.setAlignment(Align.center);
		
		/* Volume Slider */
		volumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
		volumeSlider.setPosition(-600, 200);
		volumeSlider.setSize(600, 16);
		volumeSlider.setValue(Settings.VOLUME);
		volumeSlider.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				game.sounds.setVolume(volumeSlider.getValue() / 2);
				Settings.VOLUME = volumeSlider.getValue();
			}
		});
		
		difficultyLabel = new Label("Difficulty", skin);
		difficultyLabel.setPosition(-600, 250);
		difficultyLabel.setSize(600, 16);
		difficultyLabel.setFontScale(1f);
		difficultyLabel.setAlignment(Align.center);
		
		difficultySlider = new Slider(10f, 50f, 2f, false, skin);
		difficultySlider.setPosition(-600, 275);
		difficultySlider.setSize(600, 16);
		difficultySlider.setValue(Settings.AI_SPEED);
		difficultySlider.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				Settings.AI_SPEED = difficultySlider.getValue();
			}
		});
		
		screenShakeLabel = new Label("Screen Shake", skin);
		screenShakeLabel.setPosition(-525, 325);
		screenShakeLabel.setSize(200, 50);
		screenShakeLabel.setFontScale(0.6f);
		screenShakeLabel.setAlignment(Align.left);
		
		screenShake = new Button(skin, "toggle");
		screenShake.setPosition(-600, 325);
		screenShake.setSize(50, 50);
		screenShake.setChecked(Settings.SCREEN_SHAKE);
		screenShake.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				Settings.SCREEN_SHAKE = screenShake.isChecked();
				game.shaker.reset();
			}
		});
		
		ballTrailLabel = new Label("Ball Trail", skin);
		ballTrailLabel.setPosition(-525, 325);
		ballTrailLabel.setSize(200, 50);
		ballTrailLabel.setFontScale(0.6f);
		ballTrailLabel.setAlignment(Align.left);
		
		ballTrail = new Button(skin, "toggle");
		ballTrail.setPosition(-600, 325);
		ballTrail.setSize(50, 50);
		ballTrail.setChecked(Settings.BALL_TRAIL);
		ballTrail.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				Settings.BALL_TRAIL = ballTrail.isChecked();
				for(Ball b : game.court.getEntityType(Ball.class)) {
					
					b.trail.reset();
				}
			}
		});
		
		particleEffectLabel = new Label("Particle Effect", skin);
		particleEffectLabel.setPosition(-525, 400);
		particleEffectLabel.setSize(200, 50);
		particleEffectLabel.setFontScale(0.6f);
		particleEffectLabel.setAlignment(Align.left);
		
		particleEffect = new Button(skin, "toggle");
		particleEffect.setPosition(-600, 400);
		particleEffect.setSize(50, 50);
		particleEffect.setChecked(Settings.PARTICLE_EFFECT);
		particleEffect.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {


				Settings.PARTICLE_EFFECT = particleEffect.isChecked();
				game.particleManager.reset();
			}
		});
		
		powerupLabel = new Label("Power-Up", skin);
		powerupLabel.setPosition(-525, 400);
		powerupLabel.setSize(200, 50);
		powerupLabel.setFontScale(0.6f);
		powerupLabel.setAlignment(Align.left);
		
		powerup = new Button(skin, "toggle");
		powerup.setPosition(-600, 400);
		powerup.setSize(50, 50);
		powerup.setChecked(Settings.POWER_UP);
		powerup.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {

				Settings.POWER_UP = powerup.isChecked();
			}
		});
		
		/* Add UI Elements */
		stage.addActor(volumeLabel);
		stage.addActor(volumeSlider);
		
		stage.addActor(difficultyLabel);
		stage.addActor(difficultySlider);
		
		stage.addActor(screenShakeLabel);
		stage.addActor(screenShake);
		
		stage.addActor(ballTrailLabel);
		stage.addActor(ballTrail);
		
		stage.addActor(particleEffectLabel);
		stage.addActor(particleEffect);
		
		stage.addActor(powerupLabel);
		stage.addActor(powerup);
		
		tweenEntrance();
	}
	
	@Override
	public void exitTo(Gui gui) {
		
		if(!exiting) {
			
			exiting = true;
			tweenExit(gui);
		}
	}
	
	public void tweenEntrance() {
		
		Timeline.createParallel()
		.push(Timeline.createSequence()
				.pushPause(0f)
				.push(Tween.to(titlePos, Vector2Accessor.POSITION, 1).target(game.camera.viewportWidth / 2, 100).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(0f)
				.push(Tween.to(volumeLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 - 300, 175).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(0f)
				.push(Tween.to(volumeSlider, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 - 300, 200).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(0.5f)
				.push(Tween.to(difficultyLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 - 300, 250).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(0.5f)
				.push(Tween.to(difficultySlider, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 - 300, 275).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(1f)
				.push(Tween.to(ballTrailLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 + 100, 325).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(1f)
				.push(Tween.to(ballTrail, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 + 25, 325).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(1.5f)
				.push(Tween.to(screenShakeLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 - 225, 325).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(1.5f)
				.push(Tween.to(screenShake, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 - 300, 325).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(2f)
				.push(Tween.to(powerupLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 + 100, 400).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(2f)
				.push(Tween.to(powerup, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 + 25, 400).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(2.5f)
				.push(Tween.to(particleEffectLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 - 225, 400).ease(Back.OUT)))
		.push(Timeline.createSequence()
				.pushPause(2.5f)
				.push(Tween.to(particleEffect, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH / 2 - 300, 400).ease(Back.OUT)))
		.start(game.tweenManager);
	}
	
	public void tweenExit(final Gui gui) {
		
		Timeline.createParallel()
		.push(Timeline.createSequence()
				.pushPause(0f)
				.push(Tween.to(titlePos, Vector2Accessor.POSITION, 1).target(game.camera.viewportWidth / 2, -100).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(0f)
				.push(Tween.to(volumeLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH, 175).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(0f)
				.push(Tween.to(volumeSlider, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH, 200).ease(Back.IN)))//******
		.push(Timeline.createSequence()
				.pushPause(0.5f)
				.push(Tween.to(difficultyLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH, 250).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(0.5f)
				.push(Tween.to(difficultySlider, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH, 275).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(1f)
				.push(Tween.to(ballTrailLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH + 375, 325).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(1f)
				.push(Tween.to(ballTrail, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH + 300, 325).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(1.5f)
				.push(Tween.to(screenShakeLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH + 75, 325).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(1.5f)
				.push(Tween.to(screenShake, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH, 325).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(2f)
				.push(Tween.to(powerupLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH + 375, 400).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(2f)
				.push(Tween.to(powerup, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH + 300, 400).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(2.5f)
				.push(Tween.to(particleEffectLabel, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH + 75, 400).ease(Back.IN)))
		.push(Timeline.createSequence()
				.pushPause(2.5f)
				.push(Tween.to(particleEffect, ActorAccessor.POSITION, 1).target(GameScreen.WIDTH, 400).ease(Back.IN)))
		.setCallback(new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {

				if(type == TweenCallback.COMPLETE) {
					
					game.setGui(gui);
				}
			}
		})
		.start(game.tweenManager);
	}
	
	@Override
	public void render(Renderer renderer) {
		
		super.render(renderer);
		
		renderer.setColor(Color.WHITE);
		renderer.drawCenteredText("Options", titlePos.x, titlePos.y, 2f);
		
		renderer.setColor(Colors.SOLID);
		renderer.fillRect(titlePos.x - 300, titlePos.y + 30, 600, 5);
		renderer.fillRect(titlePos.x - 300, titlePos.y - 30, 600, 5);
	}
}
