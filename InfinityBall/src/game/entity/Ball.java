package game.entity;

import game.effect.Trail;
import game.main.Colors;
import game.main.Renderer;
import game.main.Settings;
import game.main.Stats;
import game.particle.ParticleBatch;
import game.particle.states.ParticleFade;
import game.particle.states.ParticleIntegrator;
import game.particle.states.ParticleRandomLife;
import game.particle.states.ParticleSpread;
import game.screen.GameScreen;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import com.badlogic.gdx.graphics.Color;

public class Ball extends Entity {

	public float mass;
	
	public Paddle lastTouchedPaddle;
	
	public Trail trail;
	public float trailCooldown;
	
	public Ball(GameScreen game, float x, float y, float radius) {
		
		super(game);
		
		this.x = x - (radius / 2);
		this.y = y - (radius / 2);
		
		this.width = radius * 2;
		this.height = radius * 2;
		
		this.mass = radius;
		
		vx = (float) ((Math.random() - 0.5f) * 20f);
		vx = vx < 0 ? vx - 10 : vx + 10;
		vy = (float) ((Math.random() - 0.5f) * 20f);
		vy = vy < 0 ? vy - 10 : vy + 10;
		
		color = Colors.BALL;
		
		trail = new Trail(width / 4);
	}

	@Override
	public void update(float delta) {

		x += vx * delta * 10;
		y += vy * delta  * 10;
		
		if(trailCooldown > 0.01f) {
			
			trail.addSegment(getCX(), getCY());
			trailCooldown = 0;
		} else { trailCooldown += delta; }
		
		if(handleCollideWithBounds()) {
			
			game.shaker.shake(-vx, -vy);
			game.sounds.playRandomSound(game.sounds.hit, 0.4f, 0.8f);
			createCollideParticleEffect();
		}
		
		if(handleCollideWithPaddle(game.court.leftPaddle)) {
			
			lastTouchedPaddle = game.court.leftPaddle;
			game.sounds.playRandomSound(game.sounds.hit, 0.4f, 0.8f);
			createCollideParticleEffect();
		}
		if(handleCollideWithPaddle(game.court.rightPaddle))  {
			
			lastTouchedPaddle = game.court.rightPaddle;
			game.sounds.playRandomSound(game.sounds.hit, 0.4f, 0.8f);
			createCollideParticleEffect();
		}
		
		for(Solid s : game.court.getEntityType(Solid.class)) {
			
			if(handleCollideWithSolid(s)) {
				
				game.shaker.shake(-vx, -vy);
			}
		}
		
		for(Ball b : game.court.getEntityType(Ball.class)) {
			
			if(handleCollideWithBall(b)) {
				
				game.shaker.shake(-vx, -vy);
			}
		}
	}

	@Override
	public void render(Renderer renderer) {

		if(Settings.BALL_TRAIL) {
			
			trail.render(renderer);
		}
		
		renderer.setColor(color);
		renderer.fillRect(x, y, width, height);
	}
	
	public void createCollideParticleEffect() {
		
		ParticleBatch particleBatch = new ParticleBatch(10);
		particleBatch.addState(new ParticleSpread((float) Math.sqrt(x * x + y * y) / 20, 0f, (float) Math.atan2(vy, vx), (float) Math.toRadians(3f)));
		particleBatch.addState(new ParticleIntegrator(x, y));
		particleBatch.addState(new ParticleRandomLife(0.6f, 0.8f));
		particleBatch.addState(new ParticleFade(Color.WHITE.cpy()));
		
		game.particleManager.addBatch(particleBatch);
	}
	
	public boolean handleCollideWithBounds() {
		
		float mx = game.camera.viewportWidth - 5;
		float my = game.camera.viewportHeight - 5;
		boolean colliding = false;
		
		/* Test x-axis */
		if(x > mx - width && vx > 0) {
			
			colliding = true;
			remove = true;
			Stats.LEFT_SCORE += 1;
			
		} else if(x < 5 && vx < 0) {
			
			colliding = true;
			remove = true;
			Stats.RIGHT_SCORE += 1;
		}
		
		/* Test y-axis */
		if(y > my - height && vy > 0) {
			
			colliding = true;
			vy = -vy;
		} else if(y < 5 && vy < 0) {
			
			colliding = true;
			vy = -vy;
		}
		
		return colliding;
	}
	
	public boolean handleCollideWithBall(Ball b) {
		
		float xd = x - b.x;
		float yd = y - b.y;
		float dis = (float) Math.sqrt((xd * xd) + (yd * yd));
		
		if(dis <= width / 2 + b.width / 2) {
			
			float xv = b.vx - vx;
			float yv = b.vy - vy;
			float dot = (xd * xv) + (yd * yv);
			
			if(dot > 0) {
				
				float collisionScale = dot / (dis * dis);
				float xc = xd * collisionScale;
				float yc = yd * collisionScale;
				
				float combinedMass = mass + b.mass;
				float collisionWeightA = 2 * b.mass / combinedMass;
				float collisionWeightB = 2 * mass / combinedMass;
				
				vx += collisionWeightA * xc;
				vy += collisionWeightA * yc;
				b.vx -= collisionWeightB * xc;
				b.vy -= collisionWeightB * yc;
			}
			return true;
		}
		return false;
	}
	
	public boolean handleCollideWithSolid(Solid s) {
		
		boolean colliding = false;
		Rectangle2D.Float bounds = new Rectangle2D.Float(x, y, width, height);
		
		/* Collide With Left || Right */
		if(bounds.intersectsLine(new Line2D.Float(s.x, s.y, s.x, s.y + s.height)) && vx > 0 ||
				bounds.intersectsLine(new Line2D.Float(s.x + s.width, s.y, s.x + s.width, s.y + s.height)) && vx < 0) {
			
			colliding = true;
			vx = -vx;
		}
		/* Collide With Top || Bottom */
		if(bounds.intersectsLine(new Line2D.Float(s.x, s.y, s.x + s.width, s.y)) && vy > 0 ||
				bounds.intersectsLine(new Line2D.Float(s.x, s.y + s.height, s.x + s.width, s.y + s.height)) && vy < 0) {
			
			colliding = true;
			vy = -vy;
		}

		return colliding;
	}
	
	public boolean handleCollideWithPaddle(Paddle p) {
		
		if(handleCollideWithSolid(p))  {
			
			float dy = getCY() - p.getCY();
			vy = dy / 5 * (Math.abs(vx / 20));
			//vy += p.vy;
			
			if(vx > 0 && vx < 90) {
				
				vx += 6;
			} else if(vx > -90) {
				
				vx -= 6;
			}
			return true;
		}
		return false;
	}
}
