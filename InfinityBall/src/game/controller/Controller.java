package game.controller;

import game.entity.Entity;
import game.screen.GameScreen;

public abstract class Controller {
	
	public Controller() {
		
		
	}
	
	public abstract void control(GameScreen game, Entity e, float delta);
}
