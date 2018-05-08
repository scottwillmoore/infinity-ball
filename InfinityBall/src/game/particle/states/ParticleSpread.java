package game.particle.states;

import game.main.Utils;
import game.particle.Particle;
import game.particle.ParticleState;

import java.util.ArrayList;

public class ParticleSpread implements ParticleState {

	private float speed, variance;
	private float angle, spread;

	public ParticleSpread(float speed, float variance, float angle, float spread) {
		
		this.speed = speed;
		this.variance = variance;
		
		this.angle = angle;
		this.spread = spread;
	}
	
	@Override
	public void start(ArrayList<Particle> particles) {

		for(Particle p : particles) {

			float rs = Utils.randf(-spread, spread);
			float rv = Utils.randf(-variance, variance);
			
			p.vx = (float) (Math.cos(angle + rs) * (speed + rv));
			p.vy = (float) (Math.sin(angle + rs) * (speed + rv));
		}
	}

	@Override
	public void update(ArrayList<Particle> particles, float delta) {

		// Ignored
	}

	@Override
	public void reset(ArrayList<Particle> particles) {

		start(particles);
	}

}
