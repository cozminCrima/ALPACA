package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity {

  protected float x, y;
  protected int width, height;
  protected Rectangle2D.Float hitbox;

  public Entity(float x, float y, int width, int height) {
    this.x = x;
    this.y = y;
    this.height = height;
    this.width = width;
    ;
  }

  protected void drawHitbox(Graphics g, int lvlOffset) {
    g.setColor(Color.PINK);
    g.drawRect((int) hitbox.x - lvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
  }

  protected void initHitbox(float x, float y, int width, int height) {
    hitbox = new Rectangle2D.Float(x, y, height, width);
  }

  protected void updateHitbox() {
    hitbox.x = x;
    hitbox.y = y;
  }

  public Rectangle2D.Float getHitbox() {
    return hitbox;
  }

}