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
import levels.Level;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utilz.*;

public class Playing extends State implements Statemethods {
  public Playing(Game game) {
    super(game);
    initClasses();
  }

  private Player player;
  private Enemy enemy;
  private CollisionManager collisionManager;
  private LevelManager levelManager;
  private BufferedImage bg = null;
  private PauseOverlay pauseOverlay;
  private boolean paused = false;

  private int xLvlOffset;
  private int leftBorder = (int) (0.3 * Game.GAME_WIDTH);
  private int rightBorder = (int) (0.7 * Game.GAME_WIDTH);
  private int lvlTilesWide = 100;// Level.getLvlData()[0].length;
  private int maxTilesOffset = lvlTilesWide - Game.TILES_WIDTH;
  private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;

  private void initClasses() {
    levelManager = new LevelManager(game);

    collisionManager = new CollisionManager();
    player = new Player(200, 200, (int) (100 * Game.SCALE), (int) (100 * Game.SCALE),collisionManager); // ,(int) (100 * SCALE),(int)
                                                                                        // (100 * SCALE));

    enemy = new Enemy(500, 200, (int) (100 * Game.SCALE), (int) (100 * Game.SCALE),collisionManager);

    //collisionManager.addCollider(player.getCollider());
    collisionManager.addCollider(enemy.getCollider());
    
    player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
    enemy.loadLvlData(levelManager.getCurrentLevel().getLvlData());
    if (bg == null) {
      bg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND);
    }

    pauseOverlay = new PauseOverlay(this);
  }

  public void windowFocusLost() {
    player.resetDirBooleans();
    enemy.resetDirBooleans();
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public void update() {

    if (!paused) {
      checkCloseToBorder();
      levelManager.update();
      player.update();
      enemy.update();
      collisionManager.updateColliders();
      

      enemy.setVisible(HelpMethods.IsInFOV(enemy.getCollider().getHitbox(),xLvlOffset));
      
      //System.out.println(HelpMethods.IsInFOV(enemy.getCollider().getHitbox(),xLvlOffset));
      
    } else {
      pauseOverlay.update();
    }

  }

  public void unpauseGame() {
    paused = false;
  }

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
    g.drawImage(bg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
    levelManager.draw(g, xLvlOffset);
    player.render(g, xLvlOffset);
    enemy.render(g);
    if (paused)
      pauseOverlay.draw(g);

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
