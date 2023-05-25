package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.Gamestate;

import static utilz.Constants.Directions.*;

import main.GamePanel;

/**
 * 
 * Clasa pentru gestionarea evenimentelor de tastatură.
 */
public class KeyboardInputs implements KeyListener {
  private GamePanel gamePanel;

  /**
   * 
   * Constructor pentru obiectul KeyboardInputs.
   * 
   * @param gamePanel Panoul jocului asociat.
   */
  public KeyboardInputs(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (Gamestate.state) {
    case MENU:
      gamePanel.getGame().getMenu().keyPressed(e);
      break;
    case PLAYING:
      gamePanel.getGame().getPlaying().keyPressed(e);
      break;
    default:
      break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (Gamestate.state) {
    case MENU:
      gamePanel.getGame().getMenu().keyReleased(e);
      break;
    case PLAYING:
      gamePanel.getGame().getPlaying().keyReleased(e);
      break;
    default:
      break;
    }
  }
}