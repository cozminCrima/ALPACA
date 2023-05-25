package levels;

import java.awt.image.BufferedImage;

import main.Game;

/**
 * 
 * Clasa pentru reprezentarea unui nivel în joc.
 */
public class Level {

  BufferedImage lvlSprites = null;
  int[][] lvlData = null;

  /**
   * 
   * Constructor pentru obiectul Level.
   * 
   * @param img Imaginea reprezentând nivelul.
   */
  public Level(BufferedImage img) {
    this.lvlSprites = img;
    int n = (int) ((lvlSprites.getHeight() / 38));
    int m = (int) ((lvlSprites.getWidth() / 38));
    System.out.println("n + m: " + n + " " + m);
    this.lvlData = new int[n][m];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < m; j++)
        lvlData[i][j] = 0;
  }

  /**
   * 
   * Returnează harta nivelului sub formă de matrice de imagini.
   * 
   * @return Matricea de imagini reprezentând harta nivelului.
   */
  public BufferedImage[][] getLevelMap() {
    BufferedImage lvlspr = this.lvlSprites;
    BufferedImage[][] lvlmap = new BufferedImage[lvlspr.getHeight() / 38][lvlspr.getWidth() / 38];
    for (int i = 0; i < lvlmap.length; i++) {
      for (int j = 0; j < lvlmap[i].length; j++) {
        lvlmap[i][j] = lvlspr.getSubimage(j * 38, i * 38, 38, 38);
        if (lvlmap[i][j].getRGB(19, 19) != 0)
          this.lvlData[i][j] = 1;
        else
          this.lvlData[i][j] = 0;
      }
    }
    return lvlmap;
  }

  /**
   * 
   * Returnează sprite-urile nivelului.
   * 
   * @return Imaginea cu sprite-urile nivelului.
   */
  public BufferedImage getLevelSprites() {
    return this.lvlSprites;
  }

  /**
   * 
   * Returnează datele nivelului sub formă de matrice de întregi.
   * 
   * @return Matricea de întregi reprezentând datele nivelului.
   */
  public int[][] getLvlData() {
    return this.lvlData;
  }

}