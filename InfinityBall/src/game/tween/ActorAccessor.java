package game.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorAccessor implements TweenAccessor<Actor> {

	public static final int POSITION = 0;
	public static final int SIZE = 1;
	public static final int COLOR = 2;
	public static final int TRANSPARENCY = 3;
	
	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {

		switch(tweenType) {
		
		case POSITION:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case SIZE:
			returnValues[0] = target.getScaleX();
			return 1;
		case COLOR:
			returnValues[0] = target.getColor().r;
			returnValues[1] = target.getColor().g;
			returnValues[2] = target.getColor().b;
			return 3;
		case TRANSPARENCY:
			returnValues[0] = target.getColor().a;
			return 1;
		default: 
			assert false;
			return -1;
	}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {

		switch(tweenType) {
		
		case POSITION:
			target.setX(newValues[0]);
			target.setY(newValues[1]);
			break;
		case SIZE:
			target.setScale(newValues[0]);
			break;
		case COLOR:
			target.getColor().r = newValues[0];
			target.getColor().g = newValues[1];
			target.getColor().b = newValues[2];
			break;
		case TRANSPARENCY:
			target.getColor().a = newValues[0];
			break;
		default: 
			assert false;
			break;
	}
	}
	
	
}
