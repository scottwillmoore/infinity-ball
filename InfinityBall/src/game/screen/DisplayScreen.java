package game.screen;

import game.main.InfinityBall;
import game.main.Renderer;

import java.util.Random;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DisplayScreen implements Screen {
	
	public static final int WIDTH = 960;
	public static final int HEIGHT = 540;
	public static final float ASPECT_RATIO = (float) WIDTH / (float) HEIGHT;
	
	protected InfinityBall game;
	
	protected Random random = new Random();
	
	public OrthographicCamera camera;
	public Rectangle viewport;
	public Renderer renderer;
	
	public DisplayScreen(InfinityBall game) {
		
		this.game = game;
		
		init();
	}
	
	public String getName() {
		
		return "[Screen]";
	}
	
	public void init() {
		
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		viewport = new Rectangle();
		renderer = new Renderer();
	}
	
	public void update(float delta) {
	}
	
	@Override
	public void render(float delta) {
		
		camera.update();
		camera.apply(Gdx.gl10);
		camera.setToOrtho(true, WIDTH, HEIGHT);
		
		Gdx.gl.glViewport(
				(int) viewport.x, (int) viewport.y, 
				(int) viewport.width, (int) viewport.height);
		Gdx.gl.glClearColor(73 * 2f / 255f, 10 * 2f / 255f, 61 * 2f / 255f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		camera.update();
		
		renderer.update(camera, delta);
		camera.update();
	}

	@Override
	public void resize(int width, int height) {
		
		Gdx.app.log(getName(), "Resizing screen to " + width + " x " + height);
		
		// calculate new viewport
        float aspectRatio = (float)width/(float)height;
        float scale = 1f;
        Vector2 crop = new Vector2(0f, 0f);
		
		if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height/(float)HEIGHT;
            crop.x = (width - WIDTH*scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)WIDTH;
            crop.y = (height - HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float) width / (float) WIDTH;
        }

        float w = (float)WIDTH*scale;
        float h = (float)HEIGHT*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
	}

	@Override
	public void show() {
		
		Gdx.app.log(getName(), "Showing screen");
	}

	@Override
	public void hide() {
		
		Gdx.app.log(getName(), "Hiding screen");
	}

	@Override
	public void pause() {
		
		Gdx.app.log(getName(), "Pausing screen");
	}

	@Override
	public void resume() {
		
		Gdx.app.log(getName(), "Resuming screen");
	}

	@Override
	public void dispose() {
		
		Gdx.app.log(getName(), "Disposing screen");
		
		renderer.dispose();
	}

}
