package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import utilz.LoadSave;

public class LevelManager {

  private Game game;
  private BufferedImage lvlSprite;
  private Level level_0;

  public LevelManager(Game game) {
    this.game = game;
    level_0 = new Level(LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS));
  }

  public void draw(Graphics g, int lvlOffset) {
    BufferedImage[][] lvlmap = level_0.getLevelMap();
    for (int i = 0; i < lvlmap.length; i++)
      for (int j = 0; j < lvlmap[i].length; j++) {
        BufferedImage tile = null;
        tile = lvlmap[i][j];
        g.drawImage(tile, j * Game.TILES_SIZE- lvlOffset, i * Game.TILES_SIZE , Game.TILES_SIZE, Game.TILES_SIZE, null); // Game.GAME_WIDTH,
                                                                                                             // Game.GAME_HEIGHT,
                                                                                                             // null);
      }
  }

//  public BufferedImage[][] getLevelMap()
//  {
//    BufferedImage lvlspr = this.level_0.lvlSprites;
//    BufferedImage[][] lvlmap = new BufferedImage[lvlspr.getHeight()/38][lvlspr.getWidth()/38];
//    for (int i = 0; i < lvlmap.length; i++)
//      for (int j = 0; j < lvlmap[i].length; j++)
//      {
//        lvlmap[i][j] = lvlspr.getSubimage(j * 38, i * 38, 38, 38);
//      }
//    return lvlmap;
//  }

  public void update() {

  }

  public Level getCurrentLevel() {
    return level_0;
  }
}
