package utilz;

public class Constants {

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
