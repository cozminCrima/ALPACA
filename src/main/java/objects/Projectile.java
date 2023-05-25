package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import colliders.*;
import entities.Entity;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.Projectiles.*;

/**
 * 
 * Clasa Projectile reprezintă un obiect care poate fi lansat și se deplasează
 * într-o anumită direcție.
 * 
 * Este utilizată pentru gestionarea proiectilelor din joc.
 */
public class Projectile implements CollisionEvents {
  private Collider col;
  private int dir;
  private boolean active = true;
  private BufferedImage img;
  private CollisionManager cm;

  /**
   * 
   * Construiește un obiect de tip Projectile.
   * 
   * @param x        Poziția inițială pe axa X a proiectilului.
   * @param y        Poziția inițială pe axa Y a proiectilului.
   * @param width    Lățimea proiectilului.
   * @param height   Înălțimea proiectilului.
   * @param dir      Direcția de deplasare a proiectilului (-1 pentru stânga, 1
   *                 pentru dreapta).
   * @param tag      Eticheta collider-ului asociat proiectilului.
   * @param cm       Managerul de coliziuni pentru gestionarea coliziunilor.
   * @param pathName Numele fișierului de imagine pentru proiectil.
   */
  public Projectile(int x, int y, int width, int height, int dir, ColliderTag tag, CollisionManager cm,
      String pathName) {
    col = new Collider(x, y, width, height, tag, this);
    this.dir = dir;
    this.cm = cm;
    img = LoadSave.GetSpriteAtlas(pathName);
  }

  /**
   * 
   * Actualizează poziția proiectilului pe baza direcției și vitezei.
   */
  public void updatePos() {
    col.updateHitbox((int) (col.getHitbox().x + dir * SPEED * 1.2), col.getHitbox().y);
  }

  /**
   * 
   * Desenează proiectilul pe ecran.
   * 
   * @param g          Contextul grafic pe care se desenează.
   * @param xLvlOffset Deplasarea orizontală a nivelului.
   */
  public void draw(Graphics g, int xLvlOffset) {
    if (active)
      g.drawImage(img, (int) (col.getHitbox().x - xLvlOffset), (int) col.getHitbox().y, dir * CANNON_BALL_WIDTH,
          CANNON_BALL_HEIGHT, null);
    col.drawCollider(g);

  }

  /**
   * 
   * Setează poziția proiectilului la coordonatele specificate.
   * 
   * @param x Poziția pe axa X.
   * @param y Poziția pe axa Y.
   */
  public void setPos(int x, int y) {
    col.updateHitbox(x, y);
  }

  /**
   * 
   * Setează starea activă a proiectilului.
   * 
   * @param active Valoarea true pentru a activa proiectilul, false pentru a-l
   *               dezactiva.
   */
  public void setActive(boolean active) {
    this.active = active;
    if (active == false) {
      cm.removeCollider(this.col);
    }
  }

  /**
   * 
   * Verifică dacă proiectilul este activ sau inactiv.
   * 
   * @return true dacă proiectilul este activ, false în caz contrar.
   */
  public boolean isActive() {
    return active;
  }

  /**
   * 
   * Obține collider-ul asociat proiectilului.
   * 
   * @return Collider-ul proiectilului.
   */
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