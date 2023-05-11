package utilz;

import main.Game;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;

public class HelpMethods {

  public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {

    if (!isSolid(x, y, lvlData))
    {
      //System.out.print("colt L up ");
      if (!isSolid(x + width, y + height, lvlData))
      {
        //System.out.print("colt R down ");
        if (!isSolid(x + width, y, lvlData))
        {
          //System.out.print("colt R up ");
          if (!isSolid(x, y + height, lvlData))
          {
            if (!isSolid(x + width/2, y + height, lvlData))
            {
              
              if (!isSolid(x + width, y + height/2, lvlData))
              {
                //System.out.print("colt L down ");
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  public static boolean isSolid(float x, float y, int[][] lvlData) {
    if (x < 0 || x >= Game.GAME_WIDTH)
      return true;
    if (y < 0 || y >= Game.GAME_HEIGHT)
      return true;

    float xIndex = x / Game.TILES_SIZE;
    float yIndex = y / Game.TILES_SIZE;

    int value = lvlData[(int) yIndex][(int) xIndex];//.getRGB(19,1);
    //System.out.println("x,y: " +x+ " "+y + "value: "+value);
    //System.out.println("index x,y: " +(int) xIndex+ " "+(int) yIndex + "value: "+value);
    //System.out.println("true value: "+lvlData[(int)x][(int)y].getRGB(19, 19));
    if (value != 0)
      return true;
    return false;
  }
  
  public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
    
    int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
    if(xSpeed > 0) {
      int tileXPos = currentTile * Game.TILES_SIZE;
      int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
      return tileXPos + xOffset - 1;
    }else {
      return currentTile * Game.TILES_SIZE;
    }
      
    
  }
  
  
  public static float GetEntityYNextToWall(Rectangle2D.Float hitbox, float airSpeed) {
    int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
    if(airSpeed > 0) {
      int tileYPos = currentTile * Game.TILES_SIZE;
      int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
      return tileYPos + yOffset -1;
    }else {
      return currentTile * Game.TILES_SIZE;
    }
    
  }
  
  public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
    if (!isSolid(hitbox.x, hitbox.y + hitbox.height+10, lvlData))
      if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height+10, lvlData))
        return false;
    return true;
  }

}
