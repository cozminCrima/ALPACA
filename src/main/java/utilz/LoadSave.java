package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class LoadSave {

  public static final String PLAYER_ATLAS = "Spritesheet_idle_walk_attack_jump_fall.png";
  public static final String LEVEL_ATLAS = "map0.png";
  public static final String BACKGROUND = "clearskybg.png";

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
