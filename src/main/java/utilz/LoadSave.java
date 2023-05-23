package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class LoadSave {

  public static final String PLAYER_ATLAS = "Spritesheet_idle_walk_attack_jump_fall.png";
  public static final String LEVEL_ATLAS = "lvlamap1.png";
  public static final String MENU_BUTTONS = "button_atlas.png";
  public static final String MENU_BG = "menu_background.png";
  public static final String PAUSE_BG = "pause_menu.png";
  public static final String SOUND_BUTTONS = "sound_button.png";
  public static final String URM_BUTTONS = "urm_buttons.png";
  public static final String VOL_BUTTONS = "volume_buttons.png";
  public static final String START_BG = "strartscreen1.png";
  public static final String BACKGROUND = "clearskybg.png";
  public static final String PHLEGM = "phlegm.png";

  public static BufferedImage GetSpriteAtlas(String fname) {
    BufferedImage img = null;
    InputStream is = LoadSave.class.getResourceAsStream("/" + fname);
    try {
      img = ImageIO.read(is);
      is.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return img;
  }
}
