package game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Renderer {
	
	private SpriteBatch batch;
	private ShapeRenderer shape;
	private BitmapFont font;
	
	public Renderer() {
		
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		
		Texture texture = new Texture(Gdx.files.internal("font/visitor.png"), true);
		texture.setFilter(TextureFilter.MipMapNearestNearest, TextureFilter.Nearest);
		
		font = new BitmapFont(Gdx.files.internal("font/visitor.fnt"), new TextureRegion(texture), true);
	}
	
	public SpriteBatch getBatch() {
		
		return batch;
	}
	
	public ShapeRenderer getShapeRenderer() {
		
		return shape;
	}
	
	public BitmapFont getFont() {
		
		return font;
	}
	
	public void update(OrthographicCamera camera, float delta) {
		
		batch.setProjectionMatrix(camera.combined);
		shape.setProjectionMatrix(camera.combined);
	}
	
	public void dispose() {
		
		batch.dispose();
		shape.dispose();
		font.dispose();
	}
	
	public void setColor(Color color) {
		
		shape.setColor(color);
		font.setColor(color);
	}
	
	public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		
		shape.begin(ShapeType.Line);
			shape.triangle(x1, y1, x2, y2, x3, y3);
		shape.end();
	}
	
	public void drawRect(float x, float y, float width, float height) {
		
		shape.begin(ShapeType.Line);
			shape.rect(x, y, width, height);
		shape.end();
	}
	
	public void drawTexturedRect(TextureRegion region, float x, float y, float width, float height) {
		
		batch.begin();
			batch.draw(region, x, y, width, height);
		batch.end();
	}
	
	public void drawOval(float x, float y, float width, float height) {
		
		shape.begin(ShapeType.Line);
			shape.ellipse(x, y, width, height);
		shape.end();
	}
	
	public void fillTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		
		shape.begin(ShapeType.Filled);
			shape.triangle(x1, y1, x2, y2, x3, y3);
		shape.end();
	}
	
	public void fillRect(float x, float y, float width, float height) {
		
		shape.begin(ShapeType.Filled);
			shape.rect(x, y, width, height);
		shape.end();
	}
	
	public void fillOval(float x, float y, float width, float height) {
		
		shape.begin(ShapeType.Filled);
			shape.ellipse(x, y, width, height);
		shape.end();
	}
	
	public void fillAlphaRect(float x, float y, float width, float height) {
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		shape.begin(ShapeType.Filled);
			shape.rect(x, y, width, height);
		shape.end();
		
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}
	
	public void drawText(String text, float x, float y, float scale) {

		font.setScale(scale);
		
		batch.begin();
			font.draw(batch, text, x, y);
		batch.end();
		
		font.setScale(1f);
	}
	
	public void drawCenteredText(String text, float x, float y, float scale) {


		batch.begin();
			font.setScale(scale);
			float cx = x - (font.getBounds(text).width) / 2;
			float cy = y - (font.getBounds(text).height) / 2;
			font.draw(batch, text, cx, cy);
		batch.end();
		
		font.setScale(1f);
	}
}
