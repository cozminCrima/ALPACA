package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * 
 * Interfață pentru metodele specifice stărilor jocului.
 */
public interface Statemethods {

  /**
   * 
   * Actualizează starea jocului.
   */
  public void update();

  /**
   * 
   * Desenează starea curentă pe ecran.
   * 
   * @param g Obiectul Graphics utilizat pentru desenare.
   */
  public void draw(Graphics g);

  /**
   * 
   * Metodă apelată la evenimentul de clic al mouse-ului.
   * 
   * @param e Evenimentul de clic al mouse-ului.
   */
  public void mouseClicked(MouseEvent e);

  /**
   * 
   * Metodă apelată la apăsarea butonului mouse-ului.
   * 
   * @param e Evenimentul de apăsare a mouse-ului.
   */
  public void mousePressed(MouseEvent e);

  /**
   * 
   * Metodă apelată la eliberarea butonului mouse-ului.
   * 
   * @param e Evenimentul de eliberare a mouse-ului.
   */
  public void mouseReleased(MouseEvent e);

  /**
   * 
   * Metodă apelată la mișcarea mouse-ului.
   * 
   * @param e Evenimentul de mișcare a mouse-ului.
   */
  public void mouseMoved(MouseEvent e);

  /**
   * 
   * Metodă apelată la apăsarea unei taste.
   * 
   * @param e Evenimentul de apăsare a tastelor.
   */
  public void keyPressed(KeyEvent e);

  /**
   * 
   * Metodă apelată la eliberarea unei taste.
   * 
   * @param e Evenimentul de eliberare a tastelor.
   */
  public void keyReleased(KeyEvent e);

}