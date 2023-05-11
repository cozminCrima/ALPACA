package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow {
  private JFrame jframe;

  public GameWindow(final GamePanel gamePanel) {
    jframe = new JFrame();
    jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
    jframe.add(gamePanel);
    // jframe.setLocationRelativeTo(null);
    jframe.pack();
    jframe.setResizable(false);
    jframe.setVisible(true);
    jframe.addWindowFocusListener(new WindowFocusListener() {

      @Override
      public void windowGainedFocus(WindowEvent e) {

      }

      @Override
      public void windowLostFocus(WindowEvent e) {
        gamePanel.getGame().windowFocusLost();

      }

    });

  }

}
