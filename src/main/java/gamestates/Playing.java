package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import colliders.Collider;
import colliders.CollisionManager;
import entities.Enemy;
import entities.EnemyType;
import levels.Level;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.DeathScreen;
import ui.PauseOverlay;
import ui.LevelCompletedOverlay;
import utilz.*;

/**
 * Clasa Playing reprezintă starea jocului în desfășurare. Extinde clasa State
 * și implementează interfețele Statemethods. Această clasă gestionează jocul în
 * timpul rulează și interacțiunea cu elementele acestuia.
 */
public class Playing extends State implements Statemethods {

  private Player player;
  private Enemy cactus1, cactus2, cactus3;
  private Enemy cannon1, cannon2, cannon3;
  private CollisionManager collisionManager;
  private LevelManager levelManager;
  private BufferedImage bg = null;
  private PauseOverlay pauseOverlay;
  private DeathScreen deathScreen;
  private LevelCompletedOverlay levelCompletedOverlay;
  private boolean paused = false;
  private boolean finished = false;

  private int xLvlOffset;
  private int leftBorder = (int) (0.3 * Game.GAME_WIDTH);
  private int rightBorder = (int) (0.7 * Game.GAME_WIDTH);
  private int lvlTilesWide = 100;
  private int maxTilesOffset = lvlTilesWide - Game.TILES_WIDTH;
  private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;

  /**
   * Constructorul clasei Playing. Inițializează starea jocului și încarcă
   * elementele necesare.
   *
   * @param game Referință către obiectul Game.
   */
  public Playing(Game game) {
    super(game);
    initClasses();
  }

  /**
   * Metoda privată care inițializează clasele necesare. Inițializează managerul
   * de nivel, managerul de coliziuni, jucătorul și inamicii. Adaugă coliziunile
   * la managerul de coliziuni. Inițializează elementele de interfață
   * (overlay-uri, ecranul de moarte, etc.).
   */
  private void initClasses() {
    levelManager = new LevelManager(game);
    collisionManager = new CollisionManager();
    player = new Player(200, 200, (int) (100 * Game.SCALE), (int) (100 * Game.SCALE), collisionManager);
    cactus1 = new Enemy(400, 100, (int) (100 * Game.SCALE), (int) (100 * Game.SCALE), collisionManager,
        EnemyType.Cactus, player);
    cactus2 = new Enemy(3935, 100, (int) (100 * Game.SCALE), (int) (100 * Game.SCALE), collisionManager,
        EnemyType.Cactus, player);
    cactus3 = new Enemy(4190, 100, (int) (100 * Game.SCALE), (int) (100 * Game.SCALE), collisionManager,
        EnemyType.Cactus, player);
    cannon1 = new Enemy(3495, 100, (int) (65 * Game.SCALE), (int) (42 * Game.SCALE), collisionManager, EnemyType.Cannon,
        player);
    cannon2 = new Enemy(5500, 100, (int) (65 * Game.SCALE), (int) (42 * Game.SCALE), collisionManager, EnemyType.Cannon,
        player);
    cannon3 = new Enemy(1400, 100, (int) (65 * Game.SCALE), (int) (42 * Game.SCALE), collisionManager, EnemyType.Cannon,
        player);

    collisionManager.addCollider(player.getCollider());
    collisionManager.addCollider(cactus1.getCollider());
    collisionManager.addCollider(cactus2.getCollider());
    collisionManager.addCollider(cactus3.getCollider());

    player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
    cactus1.loadLvlData(levelManager.getCurrentLevel().getLvlData());
    cactus2.loadLvlData(levelManager.getCurrentLevel().getLvlData());
    cactus3.loadLvlData(levelManager.getCurrentLevel().getLvlData());
    cannon1.loadLvlData(levelManager.getCurrentLevel().getLvlData());
    cannon2.loadLvlData(levelManager.getCurrentLevel().getLvlData());
    cannon3.loadLvlData(levelManager.getCurrentLevel().getLvlData());

    if (bg == null) {
      bg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND);
    }

    pauseOverlay = new PauseOverlay(this);
    deathScreen = new DeathScreen(this);
    levelCompletedOverlay = new LevelCompletedOverlay(this);
  }

  /**
   * Metoda apelată atunci când fereastra jocului pierde focusul. Resetează
   * direcțiile jucătorului.
   */
  public void windowFocusLost() {
    player.resetDirBooleans();
  }

  /**
   * Returnează obiectul jucător.
   *
   * @return Obiectul Player asociat stării de joc.
   */
  public Player getPlayer() {
    return player;
  }

  @Override
  public void update() {
    if (!paused && !finished && !player.isDead()) {
      checkFinish();
      checkCloseToBorder();
      levelManager.update();
      player.update();
      cactus1.update();
      cactus2.update();
      cactus3.update();
      cannon1.update();
      cannon2.update();
      cannon3.update();
      collisionManager.updateColliders();

      cactus1.setVisible(HelpMethods.IsInFOV(cactus1.getCollider().getHitbox(), xLvlOffset));
      cactus2.setVisible(HelpMethods.IsInFOV(cactus2.getCollider().getHitbox(), xLvlOffset));
      cactus3.setVisible(HelpMethods.IsInFOV(cactus3.getCollider().getHitbox(), xLvlOffset));
      cannon1.setVisible(HelpMethods.IsInFOV(cannon1.getCollider().getHitbox(), xLvlOffset));
      cannon2.setVisible(HelpMethods.IsInFOV(cannon2.getCollider().getHitbox(), xLvlOffset));
      cannon3.setVisible(HelpMethods.IsInFOV(cannon3.getCollider().getHitbox(), xLvlOffset));
    } else if (paused) {
      pauseOverlay.update();
    } else if (finished) {

    }
  }

  /**
   * Verifică dacă jucătorul a ajuns la sfârșitul nivelului. Setează variabila
   * "finished" în cazul în care jucătorul a ajuns la sfârșit.
   */
  private void checkFinish() {
    if (player.getCollider().getHitbox().x > 5400) {
      finished = true;
    }
  }

  /**
   * Repornește jocul. Setează variabila "paused" la false.
   */
  public void unpauseGame() {
    paused = false;
  }

  /**
   * Verifică dacă jucătorul se află aproape de marginea vizibilă a nivelului și
   * ajustează offsetul de afișare în consecință.
   */
  private void checkCloseToBorder() {
    int playerX = (int) player.getCollider().getHitbox().x;
    int diff = playerX - xLvlOffset;

    if (diff > rightBorder)
      xLvlOffset += diff - rightBorder;
    else if (diff < leftBorder)
      xLvlOffset += diff - leftBorder;

    if (xLvlOffset > maxLvlOffsetX)
      xLvlOffset = maxLvlOffsetX;
    else if (xLvlOffset < 0)
      xLvlOffset = 0;
  }

  @Override
  public void draw(Graphics g) {
    if (player.isDead()) {
      deathScreen.draw(g);
    } else {

      g.drawImage(bg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
      levelManager.draw(g, xLvlOffset);
      player.render(g, xLvlOffset);
      cactus1.render(g, xLvlOffset);
      cactus2.render(g, xLvlOffset);
      cactus3.render(g, xLvlOffset);
      cannon1.render(g, xLvlOffset);
      cannon2.render(g, xLvlOffset);
      cannon3.render(g, xLvlOffset);
    }
    if (paused)
      pauseOverlay.draw(g);
    if (finished)
      levelCompletedOverlay.draw(g);

  }

  public void mouseDragged(MouseEvent e) {
    if (paused)
      pauseOverlay.mouseDragged(e);
  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (paused)
      pauseOverlay.mousePressed(e);

  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (paused)
      pauseOverlay.mouseReleased(e);

  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (paused)
      pauseOverlay.mouseMoved(e);

  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
    case KeyEvent.VK_UP:
      player.setJump(true);
      break;
    case KeyEvent.VK_LEFT:
      player.setLeft(true);
      break;
    case KeyEvent.VK_DOWN:
      player.setDown(true);
      break;
    case KeyEvent.VK_RIGHT:
      player.setRight(true);
      break;
    case KeyEvent.VK_X:
      player.setAttacking(true);
      break;
    case KeyEvent.VK_ESCAPE:
      paused = !paused;
      break;
    case KeyEvent.VK_BACK_SPACE:
      Gamestate.state = Gamestate.MENU;
    }

  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
    case KeyEvent.VK_UP:
      player.setJump(false);
      break;
    case KeyEvent.VK_LEFT:
      player.setLeft(false);
      break;
    case KeyEvent.VK_DOWN:
      player.setDown(false);
      break;
    case KeyEvent.VK_RIGHT:
      player.setRight(false);
      break;

    }

  }
}
