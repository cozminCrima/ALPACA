package utilz;

import main.Game;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;

/**
 * 
 * Clasa HelpMethods conține metode de utilitate pentru joc.
 */
public class HelpMethods {
  /**
   * 
   * Verifică dacă se poate efectua o deplasare pe coordonatele specifice în harta
   * dată.
   * 
   * @param x       Coordonata X pentru verificare.
   * @param y       Coordonata Y pentru verificare.
   * @param width   Lățimea obiectului.
   * @param height  Înălțimea obiectului.
   * @param lvlData Matricea cu datele nivelului.
   * @return true dacă se poate deplasa în acele coordonate, false în caz contrar.
   */
  public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {

    if (!isSolid(x, y, lvlData))
      if (!isSolid(x + width, y + height, lvlData))
        if (!isSolid(x + width, y, lvlData))
          if (!isSolid(x, y + height, lvlData))
            if (!isSolid(x + width / 2, y + height, lvlData))
              if (!isSolid(x + width, y + height / 2, lvlData))
                if (!isSolid(x, y + height / 2, lvlData))
                  // if (!isSolid(x + width, y + height * 2 / 3, lvlData))
                  // if (!isSolid(x, y + height * 2 / 3, lvlData))
                  if (!isSolid(x + width / 2, y, lvlData))
                    return true;

    return false;
  }

  /**
   * 
   * Verifică dacă coordonatele specifice din harta dată reprezintă un obiect
   * solid.
   * 
   * @param x       Coordonata X pentru verificare.
   * @param y       Coordonata Y pentru verificare.
   * @param lvlData Matricea cu datele nivelului.
   * @return true dacă coordonatele reprezintă un obiect solid, false în caz
   *         contrar.
   */
  public static boolean isSolid(float x, float y, int[][] lvlData) {
    int maxWidth = lvlData[0].length * Game.TILES_SIZE;
    if (x < 0 || x >= maxWidth)
      return true;
    if (y < 0 || y >= Game.GAME_HEIGHT)
      return true;

    float xIndex = x / Game.TILES_SIZE;
    float yIndex = y / Game.TILES_SIZE;

    int value = lvlData[(int) yIndex][(int) xIndex];// .getRGB(19,1);
    if (value != 0)
      return true;
    return false;
  }

  /**
   * 
   * Obține poziția pe axa X a entității alături de un perete, în funcție de
   * dimensiunea hitbox-ului și viteza de deplasare.
   * 
   * @param hitbox Hitbox-ul entității.
   * @param xSpeed Viteza de deplasare pe axa X.
   * @return Poziția pe axa X alături de perete.
   */
  public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {

    int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
    if (xSpeed > 0) {
      int tileXPos = currentTile * Game.TILES_SIZE;
      int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
      return tileXPos + xOffset - 1;
    } else {
      return currentTile * Game.TILES_SIZE;
    }

  }

  /**
   * 
   * Obține poziția pe axa Y a entității alături de un perete, în funcție de
   * dimensiunea hitbox-ului și viteza de deplasare în aer.
   * 
   * @param hitbox   Hitbox-ul entității.
   * @param airSpeed Viteza de deplasare în aer pe axa Y.
   * @return Poziția pe axa Y alături de perete.
   */
  public static float GetEntityYNextToWall(Rectangle2D.Float hitbox, float airSpeed) {
    int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
    if (airSpeed > 0) {
      int tileYPos = currentTile * Game.TILES_SIZE;
      int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
      return tileYPos + yOffset - 1;
    } else {
      return currentTile * Game.TILES_SIZE;
    }

  }

  /**
   * 
   * Verifică dacă entitatea se află pe sol în funcție de hitbox-ul său și datele
   * nivelului.
   * 
   * @param hitbox  Hitbox-ul entității.
   * @param lvlData Matricea cu datele nivelului.
   * @return true dacă entitatea se află pe sol, false în caz contrar.
   */
  public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
    if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
      if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
        return false;
    return true;
  }

  /**
   * 
   * Verifică dacă hitbox-ul se află în câmpul vizual al jocului, în funcție de
   * deplasarea nivelului pe axa X.
   * 
   * @param hitbox       Hitbox-ul.
   * @param xLevelOffset Deplasarea nivelului pe axa X.
   * @return true dacă hitbox-ul se află în câmpul vizual, false în caz contrar.
   */
  public static boolean IsInFOV(Rectangle2D.Float hitbox, int xLevelOffset) {
    if (hitbox.x > xLevelOffset && hitbox.x < xLevelOffset + Game.GAME_WIDTH)
      return true;
    return false;

  }
}
