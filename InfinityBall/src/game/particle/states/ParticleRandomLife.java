package game.particle.states;

import game.main.Utils;
import game.particle.Particle;
import game.particle.ParticleState;

import java.util.ArrayList;

public class ParticleRandomLife implements ParticleState {

	private float minLife, maxLife;
	
	public ParticleRandomLife(float minLife, float maxLife) {
		
		this.minLife = minLife;
		this.maxLife = maxLife;
	}
	
	@Override
	public void start(ArrayList<Particle> particles) {

		for(Particle p : particles) {
			
			p.life = Utils.randf(minLife, maxLife);
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
