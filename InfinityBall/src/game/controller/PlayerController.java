package game.controller;


import com.badlogic.gdx.Gdx;

import game.entity.Entity;
import game.screen.GameScreen;

public class PlayerController extends Controller {
	
	private int MOVE_UP;
	private int MOVE_DOWN;
	
	public PlayerController(int up, int down) {
		
		MOVE_UP = up;
		MOVE_DOWN = down;
	}
	
	@Override
	public void control(GameScreen game, Entity e, float delta) {

		int i = 0;
		
		if(Gdx.input.isKeyPressed(MOVE_UP)) {
			
			i--;
		}
		if(Gdx.input.isKeyPressed(MOVE_DOWN)) {
			
			i++;
		}
		
		e.vy = i * 30;
	}

}
