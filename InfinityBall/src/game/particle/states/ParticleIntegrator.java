package game.particle.states;

import game.particle.Particle;
import game.particle.ParticleState;

import java.util.ArrayList;

public class ParticleIntegrator implements ParticleState {

	private float x, y;
	
	public ParticleIntegrator(float x, float y) {
		
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void start(ArrayList<Particle> particles) {

		for(Particle p : particles) {
			
			p.x = x;
			p.y = y;
			
			p.duration = 0;
		}
	}

	@Override
	public void update(ArrayList<Particle> particles, float delta) {

		for(Particle p : particles) {
			
			p.x += p.vx * delta;
			p.y += p.vy * delta;
			
			p.duration += delta;
		}
	}

	@Override
	public void reset(ArrayList<Particle> particles) {

		start(particles);
	}

}
