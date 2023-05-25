package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

public class LevelCompletedOverlay {

  private Playing playing;

  private BufferedImage img;
  private int bgX, bgY, bgW, bgH;

  public LevelCompletedOverlay(Playing playing) {
    this.playing = playing;
    initImg();
    initButtons();
  }

  private void initButtons() {
    int menuX = (int) (330 * Game.SCALE);
    int nextX = (int) (445 * Game.SCALE);
    int y = (int) (195 * Game.SCALE);

  }

  private void initImg() {
    img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
    bgW = (int) (img.getWidth() * Game.SCALE);
    bgH = (int) (img.getHeight() * Game.SCALE);
    bgX = Game.GAME_WIDTH / 2 - bgW / 2;
    bgY = (int) (75 * Game.SCALE);
  }

  public void draw(Graphics g) {
    g.setColor(new Color(0, 0, 0, 200));
    g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

    g.drawImage(img, bgX, bgY, bgW, bgH, null);

  }

  private boolean isIn(UrmButton b, MouseEvent e) {
    return b.getBounds().contains(e.getX(), e.getY());
  }

}