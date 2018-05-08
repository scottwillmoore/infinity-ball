package game.effect;

import game.main.Colors;
import game.main.Renderer;

import java.util.LinkedList;


public class Trail {
	
	private LinkedList<Segment> segments;
	private float width;
	
	public Trail(float width) {
		
		segments = new LinkedList<Segment>();
		this.width = width;
	}
	
	public void addSegment(float cx, float cy) {
		
		Segment seg = null;
		
		while(segments.size() > 30) {
			
			seg = segments.removeFirst();
		}
		
		if(seg == null) {
			
			seg = new Segment();
		}
		
		seg.x = cx;
		seg.y = cy;
		segments.addLast(seg);
	}
	
	public void reset() {
		
		segments.clear();
	}
	
	public void render(Renderer renderer) {
		
		Segment curr = null;
		Segment prev = null;
		LinkedList<Float> verts = new LinkedList<Float>();
		
		for(int i = 0; i < segments.size(); i++) {
			
			curr = segments.get(i);
			
			if(prev != null) {
				
				float ang = (float) (Math.atan2(curr.y - prev.y, curr.x - prev.x) + Math.PI / 2);
				float sin = (float) Math.sin(ang);
				float cos = (float) Math.cos(ang);
				
				for(int j = 0; j < 2; j++) {
					
					float offset = (-1f + (j * 2)) * width;
					
					float scale = (float)i / segments.size();
					offset *= scale;
					
					verts.add(curr.x + cos * offset);
					verts.add(curr.y + sin * offset);
				}
			}
			prev = curr;
		}
		
		if(verts.size() >= 8) {
			
			renderer.setColor(Colors.BALL_TRAIL);
			for(int i = 0; i < verts.size() / 4; i++) {
				
				if(verts.size() < i * 4 + 7) break;

				renderer.fillTriangle(
						verts.get(i * 4 + 0), verts.get(i * 4 + 1), 
						verts.get(i * 4 + 2), verts.get(i * 4 + 3), 
						verts.get(i * 4 + 4), verts.get(i * 4 + 5));
				renderer.fillTriangle(
						verts.get(i * 4 + 2), verts.get(i * 4 + 3),
						verts.get(i * 4 + 4), verts.get(i * 4 + 5),
						verts.get(i * 4 + 6), verts.get(i * 4 + 7));
			}
		}
	}
	
	class Segment {
		
		float x;
		float y;
	}
}
