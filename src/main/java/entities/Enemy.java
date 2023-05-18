package entities;

import static utilz.Constants.PlayerConstants.ATTACK_L;
import static utilz.Constants.PlayerConstants.ATTACK_R;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE_L;
import static utilz.Constants.PlayerConstants.IDLE_R;
import static utilz.Constants.PlayerConstants.JUMP_L;
import static utilz.Constants.PlayerConstants.JUMP_R;
import static utilz.Constants.PlayerConstants.RUNNING_L;
import static utilz.Constants.PlayerConstants.RUNNING_R;
import static utilz.HelpMethods.GetEntityXPosNextToWall;
import static utilz.HelpMethods.GetEntityYNextToWall;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.HelpMethods.canMoveHere;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import colliders.Collider;
import colliders.ColliderTag;
import main.Game;
import utilz.LoadSave;

public class Enemy extends Entity {

	private float xOffset = 30 * Game.SCALE;
	private float yOffset = 35 * Game.SCALE;
	private boolean isVisible = true;

	public Enemy(float x, float y, int width, int height) {
		super(x, y, width, height,ColliderTag.Enemy);
	    loadAnimations();
	}

	public void update() {

		updatePos();
		collider.updateHitbox(x, y);
		updateAnimationTick(playerAction);
		setAnimation();
	}

	public void render(Graphics g) {
	    if(isVisible)
		g.drawImage(animations[playerAction][aniIndex], (int) (x - xOffset), (int) (y - yOffset), playerDir * width,
				height, null);
	}

	private void updatePos() {
		moving = false;

		if (jump)
			jump();

		if (!left && !right && !inAir)
			return;

		float xSpeed = 0;

		if (left)
			xSpeed -= playerSpeed;

		if (right)
			xSpeed += playerSpeed;

		if (!inAir && !IsEntityOnFloor(collider.getHitbox(), lvlData)) {
			inAir = true;
			// System.out.println("dadadadada");
		}

		if (inAir) {

			if (canMoveHere(collider.getHitbox().x, collider.getHitbox().y + airSpeed, collider.getHitbox().width, collider.getHitbox().height, lvlData)) {
				this.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				collider.updateHitbox(collider.getHitbox().x,GetEntityYNextToWall(collider.getHitbox(), airSpeed));
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		moving = true;

	}

	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = -jumpSpeed;
		System.out.println(airSpeed);

	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;

	}

	private void updateXPos(float xSpeed) {
		if (canMoveHere(collider.getHitbox().x + xSpeed, collider.getHitbox().y, collider.getHitbox().width, collider.getHitbox().height, lvlData)) {
			// System.out.println("dada");
			this.x += xSpeed;
		} else {
			this.x = GetEntityXPosNextToWall(collider.getHitbox(), xSpeed);
		}
	}

	private void setAnimation() {

		int startAni = playerAction;

		if (!moving) {
			if (lastDir == 0) {
				playerAction = IDLE_L;
			} else {
				playerAction = IDLE_R;
			}
		} else {
			if (left) {
				playerAction = RUNNING_L;
				lastDir = 0;
			}
			if (right) {
				playerAction = RUNNING_R;
				lastDir = 1;
			}
		}

		if (inAir)
			if (airSpeed < 0 && right)
				playerAction = JUMP_R;
		// else
		// playerAction = FALLING_R;
		if (airSpeed < 0 && left)
			playerAction = JUMP_L;

		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}
	

	private void updateAnimationTick(int i) {

		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction))// animations[i].length)
			{
				aniIndex = 0;
			}
		}
	}

	private void loadAnimations() {

		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

		animations = new BufferedImage[10][12];
		for (int i = 0; i < animations.length; i++)
			for (int j = 0; j < animations[i].length; j++)
				animations[i][j] = img.getSubimage(j * 100, i * 100, 100, 100);
	}
	
	public void loadLvlData(int[][] lvlData) {
		    this.lvlData = lvlData;
		  }

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
		this.right = false;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
		this.left = false;
	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	// public boolean isAttacking() {
//		    return attacking;
	// }
	
	


	public void resetDirBooleans() {
		left = false;
		up = false;
		right = false;
		down = false;
		jump = false;

	}

	public boolean isVisible() {
    return isVisible;
  }

  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  @Override
	public void OnCollisionEnter(Collider col) {
		// TODO Auto-generated method stub
		
	}


}
