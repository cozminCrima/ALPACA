package colliders;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import entities.Entity;

/**
 * Clasa care reprezintă un collider pentru coliziuni.
 */
public class Collider implements CollisionEvents {

  private ColliderTag tag;
  private Rectangle2D.Float hitbox;
  private CollisionEvents e;
  private boolean alreadyColliding = false;

  /**
   * Constructor pentru Collider.
   * 
   * @param x      poziția pe axa x
   * @param y      poziția pe axa y
   * @param width  lățimea collider-ului
   * @param height înălțimea collider-ului
   * @param tag    eticheta collider-ului
   * @param e      evenimentele de coliziune
   */
  public Collider(float x, float y, float width, float height, ColliderTag tag, CollisionEvents e) {
    this.hitbox = new Rectangle2D.Float(x, y, height, width);
    this.tag = tag;
    this.e = e;
  }

  /**
   * Desenează collider-ul.
   * 
   * @param g contextul grafic
   */
  public void drawCollider(Graphics g) {
    // g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int)
    // hitbox.height);
  }

  /**
   * Verifică dacă există coliziune între două entități.
   * 
   * @param c collider-ul cu care se verifică coliziunea
   * @return true dacă există coliziune, false în caz contrar
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
   * Verifică dacă un punct se află într-un perimetru.
   * 
   * @param point  punctul de verificat
   * @param hitbox perimetrul
   * @return true dacă punctul se află în perimetru, false în caz contrar
   */
  public boolean isPointInBoundingBox(Point2D.Float point, Rectangle2D.Float hitbox) {
    if (point.x > hitbox.x && point.x < hitbox.x + hitbox.width && point.y > hitbox.y
        && point.y < hitbox.y + hitbox.height)
      return true;
    return false;
  }

  /**
   * Actualizează poziția hitbox-ului.
   * 
   * @param x noua poziție pe axa x
   * @param y noua poziție pe axa y
   */
  public void updateHitbox(float x, float y) {
    hitbox.x = x;
    hitbox.y = y;
  }

  /**
   * Returnează hitbox-ul collider-ului.
   * 
   * @return referința la hitbox
   */
  public Rectangle2D.Float getHitbox() {
    return hitbox;
  }

  /**
   * Returnează eticheta collider-ului.
   * 
   * @return eticheta collider-ului
   */
  public ColliderTag getTag() {
    return tag;
  }

  /**
   * Compară eticheta collider-ului cu o altă etichetă.
   * 
   * @param tag eticheta cu care se compară
   * @return true dacă etichetele coincid, false în caz contrar
   */
  public boolean compareTag(ColliderTag tag) {
    if (this.tag == tag)
      return true;
    return false;
  }

  /**
   * Verifică dacă collider-ul se află într-o stare de coliziune.
   * 
   * @return true dacă collider-ul este în coliziune, false în caz contrar
   */
  public boolean isColliding() {
    return alreadyColliding;
  }

  /**
   * Setează starea de coliziune a collider-ului.
   * 
   * @param b true pentru a seta collider-ul în coliziune, false altfel
   */
  public void setIsColliding(boolean b) {
    alreadyColliding = b;
  }

  @Override
  public void OnCollisionEnter(Collider col) {
    System.out.println(getTag() + " Starting to collide with " + col.getTag());
    e.OnCollisionEnter(col);
  }

  @Override
  public void OnCollisionStay(Collider col) {
    // System.out.println("Colliding with " + col.getTag());
    e.OnCollisionStay(col);
  }

  @Override
  public void OnCollisionExit(Collider col) {
    // System.out.println("Finished colliding with " + col.getTag());
    e.OnCollisionExit(col);
  }

}
