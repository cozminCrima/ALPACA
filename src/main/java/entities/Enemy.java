package entities;

import colliders.*;
import static utilz.Constants.PlayerConstants.ATTACK_L;
import static utilz.Constants.PlayerConstants.ATTACK_R;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE_L;
import static utilz.Constants.PlayerConstants.IDLE_R;
import static utilz.Constants.PlayerConstants.JUMP_L;
import static utilz.Constants.PlayerConstants.JUMP_R;
import static utilz.Constants.PlayerConstants.RUNNING_L;
import static utilz.Constants.PlayerConstants.RUNNING_R;
import static utilz.Constants.Projectiles.*;
import static utilz.HelpMethods.GetEntityXPosNextToWall;
import static utilz.HelpMethods.GetEntityYNextToWall;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.HelpMethods.canMoveHere;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import colliders.Collider;
import colliders.ColliderTag;
import main.Game;
import utilz.LoadSave;

/**
 * Clasa Enemy reprezintă o entitate inamică din joc.
 */
public class Enemy extends Entity {

  protected BufferedImage[][] animations;
  private float xOffset = 30 * Game.SCALE;
  private float yOffset = 35 * Game.SCALE;
  private boolean isVisible = true;
  private EnemyType type;
  private int enemyDir;
  private Player player;
  private boolean isShooting;

  /**
   * Constructor pentru clasa Enemy.
   * 
   * @param x      poziția pe axa x a entității
   * @param y      poziția pe axa y a entității
   * @param width  lățimea entității
   * @param height înălțimea entității
   * @param cm     managerul de coliziuni
   * @param type   tipul inamicului
   * @param player jucătorul
   */
  public Enemy(float x, float y, int width, int height, CollisionManager cm, EnemyType type, Player player) {
    super(x, y, width, height, ColliderTag.Enemy, cm);
    this.type = type;
    this.player = player;
    this.aniSpeed = 25;
    loadAnimations();
  }

  /**
   * Actualizează orientarea inamicului în funcție de poziția jucătorului.
   */
  public void updateOrientation() {
    int lastDir = enemyDir;

    if (this.x > player.getCollider().getHitbox().x) {
      enemyDir = 0;
    } else {
      enemyDir = 1;
    }
    if (lastDir != enemyDir)
      resetAniTick();

  }

  /**
   * Actualizează starea inamicului.
   */
  public void update() {
    if (type == EnemyType.Cannon) {
      this.lastDir = enemyDir;
      int dir;
      if (aniIndex == 3 && !isShooting) {
        isShooting = true;

        if (lastDir == 0) {
          dir = -1;
        } else {
          dir = 1;
        }
        super.shootProjectile(ColliderTag.Projectile, (int) (collider.getHitbox().x + dir * 25 * Game.SCALE),
            (int) (collider.getHitbox().y + 10 * Game.SCALE), (int) (CANNON_BALL_DEFAULT_WIDTH * Game.SCALE),
            (int) (CANNON_BALL_DEFAULT_HEIGHT * Game.SCALE), LoadSave.BALL);
        Timer animationTimer = new Timer();
        animationTimer.schedule(new TimerTask() {
          @Override
          public void run() {
            isShooting = false;
          }
        }, 3000);
      }

      updateOrientation();
      super.updateProjectiles(lvlData);
      super.disableNotVisibleProjectiles();

    }
    updatePos();
    collider.updateHitbox(x, y);
    updateAnimationTick(playerAction);

  }

  /**
   * Desenează inamicul pe ecran.
   * 
   * @param g         contextul grafic
   * @param lvlOffset compensarea nivelului
   */
  public void render(Graphics g, int lvlOffset) {
    this.lvlOffset = lvlOffset;
    super.drawProjectiles(g, lvlOffset);
    if (isVisible && !super.isDead) {
      if (type == EnemyType.Cactus) {
        g.drawImage(animations[0][aniIndex], (int) (x - xOffset) - lvlOffset, (int) (y - yOffset), width, height, null);

      } else {
        if (isShooting) {
          g.drawImage(animations[enemyDir][3], (int) (x - 0.5 * xOffset) - lvlOffset, (int) (y), width, height, null);
        } else {
          g.drawImage(animations[enemyDir][aniIndex], (int) (x - 0.5 * xOffset) - lvlOffset, (int) (y), width, height,
              null);
        }

      }

      collider.drawCollider(g);
    }
  }

  /**
   * Actualizează poziția inamicului.
   */
  private void updatePos() {
    moving = false;

    if (!inAir && !IsEntityOnFloor(collider.getHitbox(), lvlData)) {
      inAir = true;
    }

    if (inAir) {
      if (canMoveHere(collider.getHitbox().x, collider.getHitbox().y + airSpeed, collider.getHitbox().width,
          collider.getHitbox().height, lvlData)) {
        this.y += airSpeed;
        airSpeed += gravity;
      } else {
        collider.updateHitbox(collider.getHitbox().x, GetEntityYNextToWall(collider.getHitbox(), airSpeed));
        if (airSpeed > 0)
          resetInAir();
        else
          airSpeed = fallSpeedAfterCollision;
      }
    }

    moving = true;
  }

  /**
   * Resetează starea de "în aer" a inamicului.
   */
  private void resetInAir() {
    inAir = false;
    airSpeed = 0;
  }

  /**
   * Resetează numărul de tick-uri al animației.
   */
  private void resetAniTick() {
    aniTick = 0;
    aniIndex = 0;
  }

  /**
   * Actualizează tick-ul animației.
   *
   * @param i index-ul animației
   */
  private void updateAnimationTick(int i) {
    int aniLength = 0;
    if (type == EnemyType.Cactus)
      aniLength = 12;
    else
      aniLength = 7;
    aniTick++;
    if (aniTick >= aniSpeed) {
      aniTick = 0;
      aniIndex++;
      if (aniIndex >= aniLength) {
        aniIndex = 0;
      }
    }
  }

  /**
   * Încarcă animațiile inamicului.
   */
  protected void loadAnimations() {
    if (type == EnemyType.Cactus) {
      BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.CACTUS);

      animations = new BufferedImage[1][12];
      for (int j = 0; j < animations[0].length; j++)
        animations[0][j] = img.getSubimage(j * 100, 0, 100, 100);
    } else {
      BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.CANNON);

      animations = new BufferedImage[2][7];
      for (int i = 0; i < animations.length; i++)
        for (int j = 0; j < animations[i].length; j++)
          animations[i][j] = img.getSubimage(j * 40, i * 26, 40, 26);
    }
  }

  /**
   * Încarcă datele nivelului.
   *
   * @param lvlData datele nivelului
   */
  public void loadLvlData(int[][] lvlData) {
    this.lvlData = lvlData;
  }

  /**
   * Verifică dacă inamicul este vizibil.
   *
   * @return true dacă inamicul este vizibil, false în caz contrar
   */
  public boolean isVisible() {
    return isVisible;
  }

  /**
   * Setează vizibilitatea inamicului.
   *
   * @param isVisible vizibilitatea inamicului
   */
  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  /**
   * Implementare a metodei OnCollisionEnter pentru gestionarea coliziunii
   * inamicului cu alte obiecte.
   *
   * @param col collider-ul cu care a avut loc coliziunea
   */
  @Override
  public void OnCollisionEnter(Collider col) {
    if (col.getTag() == ColliderTag.PlayerProjectile) {
      super.getDamage(utilz.Constants.Projectiles.CANNON_BALL_DAMAGE);
    }
  }

}
