package gamestates;

import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

/**
 * 
 * Clasa de bază pentru stările jocului.
 */
public class State {

  protected Game game;

  /**
   * 
   * Constructor pentru clasa State.
   * 
   * @param game Referința către obiectul Game.
   */
  public State(Game game) {
    this.game = game;
  }

  /**
   * 
   * Verifică dacă coordonatele unui eveniment de mouse se află în interiorul unei
   * butoane de meniu.
   * 
   * @param e  Evenimentul de mouse.
   * @param mb Butonul de meniu.
   * @return true dacă coordonatele se află în interiorul butonului, false în caz
   *         contrar.
   */
  public boolean isIn(MouseEvent e, MenuButton mb) {
    return mb.getBounds().contains(e.getX(), e.getY());
  }

  /**
   * 
   * Returnează obiectul Game asociat stării curente.
   * 
   * @return Obiectul Game asociat stării curente.
   */
  public Game getGame() {
    return game;
  }
}