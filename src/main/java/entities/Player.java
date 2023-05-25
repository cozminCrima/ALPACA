package entities;

import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Projectiles.*;
import static utilz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import colliders.*;
import main.Game;
import utilz.LoadSave;

/**
 * Clasa reprezintă entitatea jucător din joc.
 */
public class Player extends Entity {

  private BufferedImage[][] animations;
  private boolean attacking;
  private float xOffset = 30 * Game.SCALE;
  private float yOffset = 35 * Game.SCALE;

  /**
   * Constructorul clasei Player.
   *
   * @param x      Coordonata x a jucătorului.
   * @param y      Coordonata y a jucătorului.
   * @param width  Lățimea jucătorului.
   * @param height Înălțimea jucătorului.
   * @param cm     Managerul de coliziuni al jocului.
   */
  public Player(float x, float y, int width, int height, CollisionManager cm) {
    super(x, y, width, height, ColliderTag.Player, cm);
    loadAnimations();
  }

  /**
   * Metoda care actualizează starea jucătorului.
   */
  public void update() {
    checkIfFalling();
    checkIfInTrap();
    updatePos();
    collider.updateHitbox(x, y);
    super.updateProjectiles(lvlData);
    updateAnimationTick(playerAction);
    setAnimation();
  }

  /**
   * Metoda care desenează jucătorul pe ecran.
   *
   * @param g         Obiectul grafic în care se realizează desenarea.
   * @param lvlOffset Offsetul nivelului pentru poziționarea corectă pe ecran.
   */
  public void render(Graphics g, int lvlOffset) {
    this.lvlOffset = lvlOffset;
    g.drawImage(animations[playerAction][aniIndex], (int) (x - xOffset) - lvlOffset, (int) (y - yOffset), width, height,
        null);

    super.drawProjectiles(g, lvlOffset);

    collider.drawCollider(g);
  }

  private void checkIfFalling() {
    if (!inAir && !IsEntityOnFloor(collider.getHitbox(), lvlData)) {
      inAir = true;
    }
  }

  private void checkIfInTrap() {
    if (y > 625) {
      isDead = true;
    }
  }

  private void updatePos() {
    moving = false;

    if (jump)
      jump();

    if (!left && !right && !inAir)
      return;

    float xSpeed = 0;

    if (left)
      xSpeed -= playerSpeed;
    else if (right)
      xSpeed += playerSpeed;

    if (inAir) {
      if (canMoveHere(collider.getHitbox().x, collider.getHitbox().y + airSpeed, collider.getHitbox().width,
          collider.getHitbox().height, lvlData)) {
        this.y += airSpeed;
        airSpeed += gravity;
        updateXPos(xSpeed);
      } else {
        collider.updateHitbox(collider.getHitbox().x, GetEntityYNextToWall(collider.getHitbox(), airSpeed));
        if (airSpeed > 0)
          resetInAir();
        else
          airSpeed = fallSpeedAfterCollision;
        updateXPos(xSpeed);
      }
    } else {
      updateXPos(xSpeed);
    }

    moving = true;
  }

  private void jump() {
    if (inAir)
      return;
    inAir = true;
    airSpeed = -jumpSpeed;
  }

  private void resetInAir() {
    inAir = false;
    airSpeed = 0;
  }

  private void updateXPos(float xSpeed) {
    if (canMoveHere(collider.getHitbox().x + xSpeed, collider.getHitbox().y, collider.getHitbox().width,
        collider.getHitbox().height, lvlData)) {
      this.x += xSpeed;
    } else {
      this.x = GetEntityXPosNextToWall(collider.getHitbox(), xSpeed);
    }
  }

  private void setAnimation() {
    int startAni = playerAction;

    if (!moving) {
      if (lastDir == 0) {
        playerAction = IDLE_L;
      } else {
        playerAction = IDLE_R;
      }
    } else {
      if (inAir) {
        if (airSpeed < 0 && right) {
          playerAction = JUMP_R;
        } else if (airSpeed < 0 && left) {
          playerAction = JUMP_L;
        } else if (airSpeed - 1 > 0 && right)
          playerAction = FALLING_R;
        else if (airSpeed - 1 > 0 && left)
          playerAction = FALLING_L;
      } else {
        if (left && IsEntityOnFloor(collider.getHitbox(), lvlData)) {
          playerAction = RUNNING_L;
          lastDir = 0;
        }
        if (right && IsEntityOnFloor(collider.getHitbox(), lvlData)) {
          playerAction = RUNNING_R;
          lastDir = 1;
        }
      }
    }

    if (attacking) {
      if (lastDir == 0)
        playerAction = ATTACK_L;
      if (lastDir == 1)
        playerAction = ATTACK_R;
    }

    if (startAni != playerAction)
      resetAniTick();
  }

  private void resetAniTick() {
    aniTick = 0;
    aniIndex = 0;
  }

  private void updateAnimationTick(int i) {
    aniTick++;
    if (aniTick >= aniSpeed) {
      aniTick = 0;
      aniIndex++;
      if (aniIndex >= GetSpriteAmount(playerAction)) {
        aniIndex = 0;
        attacking = false;
      }
    }
  }

  private void loadAnimations() {
    BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
    animations = new BufferedImage[10][12];
    for (int i = 0; i < animations.length; i++)
      for (int j = 0; j < animations[i].length; j++)
        animations[i][j] = img.getSubimage(j * 100, i * 100, 100, 100);
  }

  /**
   * Metoda care încarcă datele nivelului pentru jucător.
   *
   * @param lvlData Datele nivelului.
   */
  public void loadLvlData(int[][] lvlData) {
    this.lvlData = lvlData;
  }

  /**
   * Metoda care verifică dacă jucătorul se deplasează spre stânga.
   *
   * @return {@code true} dacă jucătorul se deplasează spre stânga, {@code false}
   *         altfel.
   */
  public boolean isLeft() {
    return left;
  }

  /**
   * Metoda care setează direcția deplasării jucătorului la stânga.
   *
   * @param left {@code true} pentru a seta direcția deplasării la stânga,
   *             {@code false} altfel.
   */
  public void setLeft(boolean left) {
    this.left = left;
    this.right = false;
  }

  /**
   * Metoda care verifică dacă jucătorul se deplasează în sus.
   *
   * @return {@code true} dacă jucătorul se deplasează în sus, {@code false}
   *         altfel.
   */
  public boolean isUp() {
    return up;
  }

  /**
   * Metoda care setează direcția deplasării jucătorului în sus.
   *
   * @param up {@code true} pentru a seta direcția deplasării în sus,
   *           {@code false} altfel.
   */
  public void setUp(boolean up) {
    this.up = up;
  }

  /**
   * Metoda care verifică dacă jucătorul se deplasează în jos.
   *
   * @return {@code true} dacă jucătorul se deplasează în jos, {@code false}
   *         altfel.
   */
  public boolean isDown() {
    return down;
  }

  /**
   * Metoda care setează direcția deplasării jucătorului în jos.
   *
   * @param down {@code true} pentru a seta direcția deplasării în jos,
   *             {@code false} altfel.
   */
  public void setDown(boolean down) {
    this.down = down;
  }

  /**
   * Metoda care verifică dacă jucătorul se deplasează spre dreapta.
   *
   * @return {@code true} dacă jucătorul se deplasează spre dreapta, {@code false}
   *         altfel.
   */
  public boolean isRight() {
    return right;
  }

  /**
   * Metoda care setează direcția deplasării jucătorului la dreapta.
   *
   * @param right {@code true} pentru a seta direcția deplasării la dreapta,
   *              {@code false} altfel.
   */
  public void setRight(boolean right) {
    this.right = right;
    this.left = false;
  }

  /**
   * Metoda care verifică dacă jucătorul sare.
   *
   * @return {@code true} dacă jucătorul sare, {@code false} altfel.
   */
  public boolean isJump() {
    return jump;
  }

  /**
   * Metoda care setează starea de săritură a jucătorului.
   *
   * @param jump {@code true} pentru a seta starea de săritură a jucătorului,
   *             {@code false} altfel.
   */
  public void setJump(boolean jump) {
    this.jump = jump;
  }

  /**
   * Metoda care verifică dacă jucătorul atacă.
   *
   * @return {@code true} dacă jucătorul atacă, {@code false} altfel.
   */
  public boolean isAttacking() {
    return attacking;
  }

  /**
   * Metoda care setează starea de atac a jucătorului.
   *
   * @param attacking {@code true} pentru a seta starea de atac a jucătorului,
   *                  {@code false} altfel.
   */

  public void setAttacking(boolean attacking) {
    this.attacking = attacking;
    super.shootProjectile(ColliderTag.PlayerProjectile, (int) (collider.getHitbox().x + lastDir * 37 * Game.SCALE),
        (int) (collider.getHitbox().y - 9 * Game.SCALE), (int) (PHLEGM_DEFAULT_WIDTH * Game.SCALE),
        (int) (PHLEGM_DEFAULT_HEIGHT * Game.SCALE), LoadSave.PHLEGM);
  }

  public void getDamage(float amount) {
    System.out.println("Player is getting damage : " + amount);
  }

  public void resetDirBooleans() {
    left = false;
    up = false;
    right = false;
    down = false;
    jump = false;
  }

  @Override
  public void OnCollisionEnter(Collider col) {
    // TODO Auto-generated method stub
    if (col.getTag() != ColliderTag.PlayerProjectile) {
      isDead = true;
    }

  }

}
