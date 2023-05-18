package entities;

import static utilz.Constants.PlayerConstants.IDLE_R;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import colliders.*;

import main.Game;

public abstract class Entity implements CollisionEvents {

  protected float x, y;
  protected int width, height;

  
  protected BufferedImage[][] animations;
  protected int aniTick = 0, aniIndex = 0, aniSpeed = 10;
  protected int playerAction = IDLE_R;
  protected int playerDir = 1;
  protected boolean moving = false;
  protected boolean left, up, down, right, jump;
  protected float playerSpeed = 1.1f;

  protected float airSpeed = 0f;
  protected float gravity = 0.1f;
  protected float jumpSpeed = 4.3f * Game.SCALE;
  protected float fallSpeedAfterCollision = 0.3f * Game.SCALE;
  protected boolean inAir = true;
  protected int image_direction = 0;
  protected	int lastDir = 1;
  protected int[][] lvlData;
  
  protected Collider collider;
  

  public Entity(float x, float y, int width, int height,ColliderTag tag) {
    this.x = x;
    this.y = y;
    this.height = height;
    this.width = width;
    collider = new Collider(x,y,37 * Game.SCALE,37 * Game.SCALE,tag,this);
  }

  protected void drawHitbox(Graphics g) {
    g.setColor(Color.PINK);
    collider.drawCollider(g);
  }
  
  public Collider getCollider()
  {
	  return collider;
  }

}