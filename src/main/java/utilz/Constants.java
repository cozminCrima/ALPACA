package utilz;

import main.Game;

public class Constants {
	public static class Projectiles {
		public static final int CANNON_BALL_DEFAULT_WIDTH = 15;
		public static final int CANNON_BALL_DEFAULT_HEIGHT = 15;
		public static final int PHLEGM_DEFAULT_WIDTH = 10;
		public static final int PHLEGM_DEFAULT_HEIGHT = 5;
		public static final int CANNON_BALL_DAMAGE = 125;
		
		public static final int CANNON_BALL_WIDTH = (int) (Game.SCALE * CANNON_BALL_DEFAULT_WIDTH);
		public static final int CANNON_BALL_HEIGHT = (int) (Game.SCALE * CANNON_BALL_DEFAULT_HEIGHT);
		public static final float SPEED = 0.75f * Game.SCALE;
	}

	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}

		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}

		public static class URMButtons {
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

		}

		public static class VolumeButtons {
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;

	}

	public static class PlayerConstants {
		public static final int IDLE_R = 0;
		public static final int IDLE_L = 1;
		public static final int RUNNING_R = 2;
		public static final int RUNNING_L = 3;
		public static final int ATTACK_R = 4;
		public static final int ATTACK_L = 5;
		public static final int JUMP_R = 6;
		public static final int JUMP_L = 7;
		public static final int FALLING_R = 8;
		public static final int FALLING_L = 9;

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case 0:
				return 12;
			case 1:
				return 12;
			case 2:
				return 12;
			case 3:
				return 12;
			case 4:
				return 9;
			case 5:
				return 9;
			case 6:
				return 12;
			case 7:
				return 12;
			case 8:
				return 1;
			case 9:
				return 1;
			default:
				return 0;
			}

		}
	}
}
