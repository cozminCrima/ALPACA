package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Player;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import levels.LevelManager;
import utilz.LoadSave;

public class Game implements Runnable {
  private GameWindow gameWindow;
  private GamePanel gamePanel;
  private Thread gameThread;
  private final int FPS_SET = 120;
  private final int UPS_SET = 200;

  private Playing playing;
  private Menu menu;

  public final static int TILES_DEFAULT_SIZE = 38;
  public final static float SCALE = 1.5f;
  public final static int TILES_WIDTH = 24;
  public final static int TILES_HEIGHT = 12;
  public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
  public final static int GAME_WIDTH = TILES_SIZE * TILES_WIDTH;
  public final static int GAME_HEIGHT = TILES_SIZE * TILES_HEIGHT;

  private BufferedImage bg = null;

  public Game() {
//    if (bg == null) {
//     bg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND);
//    }
    initClasses();
    gamePanel = new GamePanel(this);
    gameWindow = new GameWindow(gamePanel);
    gamePanel.requestFocus();
    startGameLoop();

  }

  private void initClasses() {
    menu = new Menu(this);
    playing = new Playing(this);
  }

  @Override
  public void run() {

    double timePerFrame = 1000000000.0 / FPS_SET;
    double timePerUpdate = 1000000000.0 / UPS_SET;
    long previousTime = System.nanoTime();

    int frames = 0;
    int updates = 0;
    long lastCheck = System.currentTimeMillis();

    double uFreq = 0;
    double fFreq = 0;

    while (true) {

      long currentTime = System.nanoTime();

      uFreq += (currentTime - previousTime) / timePerUpdate;
      fFreq += (currentTime - previousTime) / timePerFrame;

      previousTime = currentTime;

      if (uFreq >= 1) {
        update();
        updates++;
        uFreq--;
      }

      if (fFreq >= 1) {
        gamePanel.repaint();
        frames++;
        fFreq--;
      }

      if (System.currentTimeMillis() - lastCheck >= 1000) {
        lastCheck = System.currentTimeMillis();
        System.out.println("FPS: " + frames + " UPS: " + updates);
        frames = 0;
        updates = 0;
      }
    }
  }

  private void update() {

    switch (Gamestate.state) {
    case MENU:
      menu.update();
      break;
    case PLAYING:
      playing.update();
      break;
    case OPTIONS:
    case QUIT:
    default:
      System.exit(0);
      break;
    }

  }

  public void render(Graphics g) {

    switch (Gamestate.state) {
    case MENU:
      menu.draw(g);
      break;
    case PLAYING:
      playing.draw(g);

      break;
    default:
      break;
    }
  }

  private void startGameLoop() {
    gameThread = new Thread(this);
    gameThread.start();

  }

  public Menu getMenu() {
    return menu;
  }

  public Playing getPlaying() {
    return playing;
  }

  public void windowFocusLost() {
    if (Gamestate.state == Gamestate.PLAYING)
      playing.getPlayer().resetDirBooleans();
  }
}
