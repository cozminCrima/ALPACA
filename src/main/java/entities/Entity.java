package entities;

import static utilz.Constants.PlayerConstants.IDLE_R;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import colliders.*;

import main.Game;
import objects.Projectile;
import utilz.HelpMethods;

/**
 * Clasă abstractă care servește drept bază pentru entitățile din joc.
 */
public abstract class Entity implements CollisionEvents {

  protected float x, y;
  protected int width, height;

  protected int aniTick = 0, aniIndex = 0, aniSpeed = 10;
  protected int playerAction = IDLE_R;
  protected boolean moving = false;
  protected boolean left, up, down, right, jump;
  protected float playerSpeed = 1.1f;

  protected float airSpeed = 0f;
  protected float gravity = 0.1f;
  protected float jumpSpeed = 5f * Game.SCALE;
  protected float fallSpeedAfterCollision = 0.3f * Game.SCALE;
  protected boolean inAir = true;
  protected int image_direction = 0;
  protected int lastDir = 1;
  protected int[][] lvlData;
  protected int health = 100;
  protected boolean isDead = false;
  protected int lvlOffset;

  protected Collider collider;
  protected ColliderTag tag;
  private ArrayList<Projectile> projectiles = new ArrayList<>();
  protected CollisionManager colManager;

  /**
   * Constructorul clasei Entity.
   *
   * @param x      Coordonata x a entității.
   * @param y      Coordonata y a entității.
   * @param width  Lățimea entității.
   * @param height Înălțimea entității.
   * @param tag    Eticheta colliderului entității.
   * @param cm     Managerul de coliziuni al jocului.
   */
  public Entity(float x, float y, int width, int height, ColliderTag tag, CollisionManager cm) {
    this.x = x;
    this.y = y;
    this.height = height;
    this.width = width;
    this.tag = tag;
    colManager = cm;
    collider = new Collider(x, y, 37 * Game.SCALE, 37 * Game.SCALE, tag, this);
  }

  /**
   * Metoda care permite entității să tragă un proiectil.
   *
   * @param projectileTag Eticheta colliderului proiectilului.
   * @param x             Coordonata x de la care se lansează proiectilul.
   * @param y             Coordonata y de la care se lansează proiectilul.
   * @param width         Lățimea proiectilului.
   * @param height        Înălțimea proiectilului.
   * @param pathName      Numele fișierului de imagine al proiectilului.
   */
  protected void shootProjectile(ColliderTag projectileTag, int x, int y, int width, int height, String pathName) {
    int dir;
    if (lastDir == 0) {
      dir = -1;
    } else {
      dir = 1;
    }
    Projectile p = new Projectile(x, y, width, height, dir, projectileTag, colManager, pathName);
    projectiles.add(p);
    colManager.addCollider(p.getCollider());
  }

  /**
   * Metoda care actualizează poziția proiectilelor.
   *
   * @param lvlData Matricea de date a nivelului jocului.
   */
  protected void updateProjectiles(int[][] lvlData) {
    for (Projectile p : projectiles) {
      if (p.isActive()) {
        p.updatePos();
      }
    }
  }

  /**
   * Metoda care desenează proiectilele pe ecran.
   *
   * @param g         Obiectul grafic în care se realizează desenarea.
   * @param lvlOffset Offsetul nivelului pentru poziționarea corectă pe ecran.
   */
  protected void drawProjectiles(Graphics g, int lvlOffset) {
    for (Projectile p : projectiles) {
      if (p.isActive()) {
        p.draw(g, lvlOffset);
      }
    }
  }

  /**
   * Metoda care dezactivează proiectilele care nu sunt vizibile pe ecran.
   */
  protected void disableNotVisibleProjectiles() {
    for (Projectile p : projectiles) {
      p.setActive(HelpMethods.IsInFOV(p.getCollider().getHitbox(), lvlOffset));
    }
  }

  /**
   * Metoda care aplică entității o anumită cantitate de daună.
   *
   * @param amount Cantitatea de daună.
   */
  protected void getDamage(int amount) {
    if (health - amount < 0) {
      isDead = true;
      colManager.removeCollider(collider);
    } else {
      health -= amount;
    }
  }

  /**
   * Metoda care returnează colliderul entității.
   *
   * @return Colliderul entității.
   */
  public Collider getCollider() {
    return collider;
  }

  /**
   * Metoda care verifică dacă entitatea este moartă.
   *
   * @return {@code true} dacă entitatea este moartă, {@code false} în caz contrar.
   */
  public boolean isDead() {
    return isDead;
  }

  /**
   * Metoda care setează starea de viață a entității.
   *
   * @param isDead Starea de viață a entității: {@code true} dacă este mortă,
   *               {@code false} în caz contrar.
   */
  public void setDead(boolean isDead) {
    this.isDead = isDead;
  }

}
