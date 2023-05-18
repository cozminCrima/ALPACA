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
import entities.Player;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;

public class Playing extends State implements Statemethods {
	public Playing(Game game) {
		super(game);
		initClasses();
	}

	private Player player;
	private Enemy enemy;
	private LevelManager levelManager;
	private CollisionManager collisionManager;
	private BufferedImage bg = null;

	private void initClasses() {
		levelManager = new LevelManager(game);
		collisionManager = new CollisionManager();
		player = new Player(200, 200, (int) (100 * Game.SCALE), (int) (100 * Game.SCALE)); // ,(int) (100 * SCALE),(int)
																							// (100 * SCALE));

		enemy = new Enemy(500, 200, (int) (100 * Game.SCALE), (int) (100 * Game.SCALE));

		collisionManager.addCollider(player.getCollider());
		collisionManager.addCollider(enemy.getCollider());
		
		player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
		enemy.loadLvlData(levelManager.getCurrentLevel().getLvlData());
		if (bg == null) {
			bg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND);
		}
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
		levelManager.update();
		player.update();
		enemy.update();
		collisionManager.updateColliders();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		levelManager.draw(g);
		player.render(g);
		enemy.render(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

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
