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
  protected	int lastDir = 1;
  protected int[][] lvlData;
  protected int health=100;
  protected boolean isDead = false;
  protected int lvlOffset;
  
  protected Collider collider;
  protected ColliderTag tag;
  private ArrayList<Projectile> projectiles = new ArrayList<>();
  protected CollisionManager colManager;

  public Entity(float x, float y, int width, int height,ColliderTag tag,CollisionManager cm) {
    this.x = x;
    this.y = y;
    this.height = height;
    this.width = width;
    this.tag = tag;
    colManager = cm;
    collider = new Collider(x,y,37*Game.SCALE ,37*Game.SCALE,tag,this);
  }

  protected void shootProjectile(ColliderTag projectileTag,int x,int y,int width,int height,String pathName)
  {
      int dir;
      if(lastDir == 0)
      {
        dir = -1;
      }
      else
      {
        dir = 1;
      }
	  Projectile p = new Projectile(x, y,width,height,dir,projectileTag,colManager,pathName);
	  projectiles.add(p);
	  colManager.addCollider(p.getCollider());
  }
  
  protected void updateProjectiles(int[][] lvlData) {
		for (Projectile p : projectiles)
			if (p.isActive()) {
				p.updatePos();
			}
  }
  
  protected void drawProjectiles(Graphics g, int lvlOffset)
  {
	  for (Projectile p : projectiles)
			if (p.isActive())
				p.draw(g, lvlOffset);
  }
  
  protected void disableNotVisibleProjectiles()
  {
    for (Projectile p : projectiles)
      p.setActive(HelpMethods.IsInFOV(p.getCollider().getHitbox(),lvlOffset));
  }
   
  protected void getDamage(int amount)
  {
	  if(health-amount < 0)
	  {
		  isDead = true;
		  colManager.removeCollider(collider);
	  }
	  else
	  {
		  health -= amount;
	  }
  }
  
  public Collider getCollider()
  {
	  return collider;
  }
  
  
  public boolean isDead() {
    return isDead;
  }

  public void setDead(boolean isDead) {
    this.isDead = isDead;
  }

}