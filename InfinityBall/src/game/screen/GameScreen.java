package game.screen;

import game.effect.Shaker;
import game.entity.Ball;
import game.gui.Gui;
import game.gui.Menu;
import game.main.Court;
import game.main.InfinityBall;
import game.main.Settings;
import game.main.Sounds;
import game.main.Stats;
import game.main.Textures;
import game.particle.ParticleManager;
import game.powerup.BiggerPaddle;
import game.tween.ActorAccessor;
import game.tween.Vector2Accessor;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameScreen extends DisplayScreen {
	
	/*Tween Manager */
	public TweenManager tweenManager;
	
	/* Camera Shaker Manager */
	public Shaker shaker;
	
	/* Sound */
	public Sounds sounds;
	
	/* Textures */
	public Textures textures;
	
	/* Court */
	public Court court;
	
	/* Particle Batch */
	public ParticleManager particleManager;
	
	/* Gui */
	public Gui gui;
	
	public GameScreen(InfinityBall game) {

		super(game);
	}
	
	@Override
	public String getName() {
		
		return "[Screen]: [InGame]";
	}
	
	public void setGui(Gui gui) {
		
		this.gui = gui;
		
		if(gui != null) {

			gui.create(this);
		}
	}
	
	@Override
	public void init() {
		
		super.init();

		/* Initialize Tween Managers and Accessors */
		tweenManager = new TweenManager();
		Tween.registerAccessor(Vector2.class, new Vector2Accessor());
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		/* Initialize Screen Shaker */
		shaker = new Shaker(this);
		
		/* Initialize Sounds */
		sounds = new Sounds();
		sounds.init();
		
		/* Initialize Textures */
		textures = new Textures();
		textures.init();
		
		/* Initialize Court (Entities) */
		court = new Court(this);
		court.init();
		
		/* Initialize Particle Batch */
		particleManager = new ParticleManager();
		
		/* Initialize Menu */
		gui = new Menu();
		gui.create(this);
	}
	
	@Override
	public void dispose() {
		
		super.dispose();
		
		sounds.dispose();
		textures.dispose();
		court.dispose();
		
		if(gui != null) {
			
			gui.dispose();
		}
	}
	
	@Override
	public void resize(int width, int height) {
		
		super.resize(width, height);
		
		if(gui != null) {
			
			gui.resize(camera);
		}
	}
	
	@Override
	public void update(float delta) {
		
		super.update(delta);
		
		/* Reset Ball Position */
		if(Gdx.input.isKeyPressed(Keys.SPACE))  {

			court.removeEntityType(Ball.class);
			court.addEntity(new Ball(this, camera.viewportWidth / 2, camera.viewportHeight / 2, 10f));
		}
		
		if(Gdx.input.isKeyPressed(Keys.B)) {
			
			if(court.getPowerups().size() == 0) {
				
				court.addPowerup(new BiggerPaddle(this, camera.viewportWidth / 2, camera.viewportHeight / 2));
			}
		}
		
		/* Check For Return To Menu */
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			
			if(gui == null) {
				
				setGui(new Menu());
			} else {
				
				gui.exitTo(new Menu());
			}
		}
		
		/* Update Tweens */
		tweenManager.update(delta);

		/* Update Cameras Screen Shake Position*/
		if(Settings.SCREEN_SHAKE) {
			
			shaker.update(delta);
		}
		
		/* Update Court (Entities) */
		court.update(delta);
		
		/* Update Particle Manager */
		particleManager.update(delta);
		
		/* Update Gui */
		if(gui != null) {
			
			gui.update(delta);
		}
	}
	
	@Override
	public void render(float delta) {
		
		super.render(delta);
		
		/* Render Court (Entities) */
		court.render(renderer);

		/* Render Particles */
		if(Settings.PARTICLE_EFFECT) {
			
			particleManager.render(renderer);
		}
		
		/* Render FPS */
		renderer.setColor(Color.WHITE);
		renderer.drawText("FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20, 0.5f);
		
		/* Ingame Graphics */
		if(gui == null) {
			
			/* Render Score */
			renderer.setColor(Color.WHITE);
			renderer.drawCenteredText(String.valueOf(Stats.LEFT_SCORE), camera.viewportWidth / 2 - 50, 50, 2f);
			renderer.drawCenteredText(String.valueOf(Stats.RIGHT_SCORE), camera.viewportWidth / 2 + 50, 50, 2f);
		}
		
		/* Render Menu */
		if(gui != null) {
			
			gui.render(renderer);
		}
	}
}
