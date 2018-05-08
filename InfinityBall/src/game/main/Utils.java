package game.main;

import java.util.Random;

public class Utils {
	
	public static Random random = new Random(System.currentTimeMillis());
	
	public static float randf(float min, float max) {
		
		float n = max - min + 1;
		float i = random.nextInt() % n;
		
		return max + i;
	}
}
