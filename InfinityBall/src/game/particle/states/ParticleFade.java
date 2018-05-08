package game.particle.states;

import game.particle.Particle;
import game.particle.ParticleState;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

public class ParticleFade implements ParticleState {

	private Color startColor;
	
	public ParticleFade(Color startColor) {
		
		this.startColor = startColor;
	}
	
	@Override
	public void start(ArrayList<Particle> particles) {

		for(Particle p : particles) {
			
			p.color = startColor;
		}
	}

	@Override
	public void update(ArrayList<Particle> particles, float delta) {

		for(Particle p : particles) {
			
			p.color.a = (p.life - p.duration) / p.life;
		}
	}

	@Override
	public void reset(ArrayList<Particle> particles) {

		start(particles);
	}

}
