package levels;

import java.awt.image.BufferedImage;

import main.Game;

public class Level {

  BufferedImage lvlSprites = null;
  int[][] lvlData = null;

  public Level(BufferedImage img) {
    this.lvlSprites = img;
    int n = (int) ((lvlSprites.getHeight()/38));
    int m = (int) ((lvlSprites.getWidth()/38));
    System.out.println("n + m: "+n+" " +m);
    this.lvlData = new int[n][m];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < m; j++)
        lvlData[i][j] = 0;
  }

  public BufferedImage[][] getLevelMap() {
    BufferedImage lvlspr = this.lvlSprites;
    
    //System.out.println("map");
    BufferedImage[][] lvlmap = new BufferedImage[lvlspr.getHeight() / 38][lvlspr.getWidth() / 38];
    for (int i = 0; i < lvlmap.length; i++) {
      for (int j = 0; j < lvlmap[i].length; j++) {
        int ok = 0;
        lvlmap[i][j] = lvlspr.getSubimage(j * 38, i * 38, 38, 38);
        if(lvlmap[i][j].getRGB(19, 19) != 0)
          this.lvlData[i][j] = 1;
        else
          this.lvlData[i][j] = 0;
          //ok = 1;
       //System.out.print(this.lvlData[i][j] + " " );
      }
         //System.out.println();
        //this.lvlData[i][j] = lvlmap[i][j].getRGB(19, 19);
        // System.out.println(lvlmap.length + );
    }
    return lvlmap;
  }

  public BufferedImage getLevelSprites() {
    return this.lvlSprites;
  }

  public int[][] getLvlData() {
    return this.lvlData;
  }

}
