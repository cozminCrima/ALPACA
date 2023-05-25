package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import utilz.LoadSave;

/**
 * 
 * Clasa pentru gestionarea nivelelor din joc.
 */
public class LevelManager {

  private Game game;
  private BufferedImage lvlSprite;
  private Level level_0;

  /**
   * 
   * Constructor pentru obiectul LevelManager.
   * 
   * @param game Referința către obiectul Game.
   */
  public LevelManager(Game game) {
    this.game = game;
    level_0 = new Level(LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS));
  }

  /**
   * 
   * Desenează nivelul curent pe ecran.
   * 
   * @param g         Contextul grafic pe care se desenează nivelul.
   * 
   * @param lvlOffset Offset-ul de nivel pentru desenare.
   */
  public void draw(Graphics g, int lvlOffset) {
    BufferedImage[][] lvlmap = level_0.getLevelMap();
    for (int i = 0; i < lvlmap.length; i++)
      for (int j = 0; j < lvlmap[i].length; j++) {
        BufferedImage tile = null;
        tile = lvlmap[i][j];
        g.drawImage(tile, j * Game.TILES_SIZE - lvlOffset, i * Game.TILES_SIZE, Game.TILES_SIZE, Game.TILES_SIZE, null);

      }
  }

  /**
   * 
   * Actualizează nivelul curent.
   */
  public void update() {

  }

  /**
   * 
   * Returnează nivelul curent.
   * 
   * @return Obiectul Level reprezentând nivelul curent.
   */
  public Level getCurrentLevel() {
    return level_0;
  }
}