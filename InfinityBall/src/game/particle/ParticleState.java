package game.particle;

import java.util.ArrayList;

public interface ParticleState {

	public void start(ArrayList<Particle> particles);

	public void update(ArrayList<Particle> particles, float delta);

	public void reset(ArrayList<Particle> particles);
}
