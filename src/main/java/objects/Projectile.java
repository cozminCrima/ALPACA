package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import colliders.*;
import entities.Entity;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.Projectiles.*;

public class Projectile implements CollisionEvents {
	private Collider col;
	private int dir;
	private boolean active = true;
	private BufferedImage img;
	private CollisionManager cm;

	public Projectile(int x, int y, int dir, ColliderTag tag,CollisionManager cm) {
		col = new Collider(x, y, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, tag, this);
		this.dir = dir;
		this.cm = cm;
		img = LoadSave.GetSpriteAtlas(LoadSave.PHLEGM);
	}

	public void updatePos() {
		col.updateHitbox(col.getHitbox().x + dir * SPEED, col.getHitbox().y);
	}

	public void draw(Graphics g, int xLvlOffset) {
		if (active)
			g.drawImage(img, (int) (col.getHitbox().x - xLvlOffset), (int) col.getHitbox().y, dir * CANNON_BALL_WIDTH,
					CANNON_BALL_HEIGHT, null);
			col.drawCollider(g);
			

	}

	public void setPos(int x, int y) {
		col.updateHitbox(x, y);
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public Collider getCollider() {
		return col;
	}

	@Override
	public void OnCollisionEnter(Collider col) {
		// TODO Auto-generated method stub
		setActive(false);
		cm.removeCollider(this.col);
	}

}