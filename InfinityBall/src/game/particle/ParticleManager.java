package game.particle;

import game.main.Renderer;

import java.util.ArrayList;
import java.util.Iterator;

public class ParticleManager {

	public ArrayList<ParticleBatch> particleBatches;
	
	public ParticleManager() {
		
		particleBatches = new ArrayList<ParticleBatch>();
	}
	
	public void addBatch(ParticleBatch batch) {
		
		particleBatches.add(batch);
		batch.start();
	}
	
	public void reset() {
		
		particleBatches.clear();
	}
	
	public void update(float delta) {
		
		Iterator<ParticleBatch> iterator = particleBatches.iterator();
		while(iterator.hasNext()) {
			
			ParticleBatch batch = iterator.next();
			
			batch.update(delta);
			
			if(batch.finished()) {
				
				iterator.remove();
			}
		}
	}
	
	public void render(Renderer renderer) {
		
		for(ParticleBatch batch : particleBatches) {
			
			batch.render(renderer);
		}
	}
}
