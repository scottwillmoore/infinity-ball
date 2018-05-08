package game.particle;

import game.main.Renderer;

import java.util.ArrayList;

public class ParticleBatch {

	private ArrayList<Particle> aliveParticles;
	private ArrayList<Particle> deadParticles;
	private ArrayList<ParticleState> states;
	
	public ParticleBatch(int particlesNum) {
		
		aliveParticles = new ArrayList<Particle>();
		deadParticles = new ArrayList<Particle>();
		states = new ArrayList<ParticleState>();
		
		for(int i = 0; i < particlesNum; i++) {
			
			deadParticles.add(new Particle());
		}
	}
	
	public void start() {
		
		for(ParticleState state : states) {
			
			state.start(deadParticles);
		}
		
		aliveParticles.addAll(deadParticles);
		
		deadParticles.clear();
	}
	
	public void reset() {
		
		if(!deadParticles.isEmpty()) {
			
			for(ParticleState state : states) {
				
				state.reset(deadParticles);
			}
		}
		
		aliveParticles.addAll(deadParticles);
		
		deadParticles.clear();
	}
	
	public void update(float delta) {
		
		for(ParticleState state : states) {
			
			state.update(aliveParticles, delta);
		}
		
		for(int i = 0; i < aliveParticles.size(); i++) {
			
			Particle p = aliveParticles.get(i);
			
			if(!p.isAlive()) {
				
				deadParticles.add(aliveParticles.remove(i));
			}
		}
	}
	
	public void render(Renderer renderer) {
		
		for(Particle p : aliveParticles) {
			
			renderer.setColor(p.color);
			renderer.fillAlphaRect(p.x - p.width / 2, p.y - p.height / 2, p.width, p.height);
		}
	}
	
	public boolean finished() {
		
		return aliveParticles.size() <= 0;
	}
	
	public void addState(ParticleState state) {
		
		if(state != null) {
			
			states.add(state);
		}
	}
}
