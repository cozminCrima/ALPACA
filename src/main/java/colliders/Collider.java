package colliders;

import java.awt.Desktop.Action;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import entities.Entity;

public class Collider implements CollisionEvents {
	
	private ColliderTag tag;
	private Rectangle2D.Float hitbox;
	private CollisionEvents e;
	private boolean alreadyColliding = false;

	/**
	 * Constructor pentru collider
	 * 
	 * @param x      pozitia pe axa x
	 * @param y      pozitia pe axa y
	 * @param width  latimea colliderului
	 * @param height inaltime colliderului
	 */
	public Collider(float x, float y, float width, float height,ColliderTag tag,CollisionEvents e) {
		this.hitbox = new Rectangle2D.Float(x, y, height, width);
		this.tag = tag;
		this.e = e;
	}

	public void drawCollider(Graphics g) {
		g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	/**
	 * Functie care verifica daca exista coliziune intre 2 entitati
	 * 
	 * @param c Collider to check collision with
	 * @return
	 */
	public boolean checkForCollision(Collider c) {

		Rectangle2D.Float h_col = c.getHitbox();
		Point2D.Float checkingPoint = new Point2D.Float(hitbox.x, hitbox.y);
		
		if (isPointInBoundingBox(checkingPoint, h_col))
			return true;

		checkingPoint.setLocation(hitbox.x + hitbox.width, hitbox.y);
		if (isPointInBoundingBox(checkingPoint, h_col))
			return true;

		checkingPoint.setLocation(hitbox.x, hitbox.y + hitbox.height);
		if (isPointInBoundingBox(checkingPoint, h_col))
			return true;

		checkingPoint.setLocation(hitbox.x + hitbox.width, hitbox.y + hitbox.height);
		if (isPointInBoundingBox(checkingPoint, h_col))
			return true;

		checkingPoint.setLocation(hitbox.x + 0.5 * hitbox.width, hitbox.y);
		if (isPointInBoundingBox(checkingPoint, h_col))
			return true;

		checkingPoint.setLocation(hitbox.x + 0.5 * hitbox.width, hitbox.y + hitbox.height);
		if (isPointInBoundingBox(checkingPoint, h_col))
			return true;

		checkingPoint.setLocation(hitbox.x, hitbox.y + 0.5 * hitbox.height);
		if (isPointInBoundingBox(checkingPoint, h_col))
			return true;

		checkingPoint.setLocation(hitbox.x + hitbox.width, hitbox.y + 0.5 * hitbox.height);
		if (isPointInBoundingBox(checkingPoint, h_col))
			return true;

		return false;
	}

	/**
	 * Functie care verifica daca un punct se afla intr-un perimetru
	 * 
	 * @param point  Punctul de verificat
	 * @param hitbox Perimetrul
	 * @return
	 */
	public boolean isPointInBoundingBox(Point2D.Float point, Rectangle2D.Float hitbox) {
		if (point.x > hitbox.x && point.x < hitbox.x + hitbox.width && point.y > hitbox.y
				&& point.y < hitbox.y + hitbox.height)
			return true;
		return false;
	}

	/**
	 * Functie pentru updatarea pozitie hitboxului
	 * 
	 * @param x
	 * @param y
	 */
	public void updateHitbox(float x, float y) {
		hitbox.x = x;
		hitbox.y = y;
	}
	

	/**
	 * Getter pentru hitboxul colliderului
	 * @return referinta la hitbox
	 */
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	
	
	public ColliderTag getTag()
	{
		return tag;
	}
	
	public boolean compareTag(ColliderTag tag)
	{
		if(this.tag == tag)return true;
		return false;
	}
	
	public boolean isColliding()
	{
		return alreadyColliding;
	}
	
	public void setIsColliding(boolean b)
	{
		alreadyColliding = b;
	}

	@Override
	public void OnCollisionEnter(Collider col) {
		System.out.println(getTag() + " Starting to collide with "+ col.getTag());
		e.OnCollisionEnter(col);
	}

	@Override
	public void OnCollisionStay(Collider col) {
		//System.out.println("Colliding with "+ col.getTag());
		e.OnCollisionStay(col);
	}

	@Override
	public void OnCollisionExit(Collider col) {
		//System.out.println("Finished colliding with " + col.getTag());
		e.OnCollisionExit(col);
	}

}
