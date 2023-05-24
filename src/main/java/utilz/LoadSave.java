package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import utilz.Constants.EnemyConstants;
import utilz.Constants.EnemyConstants.*;
import entities.Crabby;
import main.Game;

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
  public static final String CRABBY_SPRITE = "crabby_sprite.png";


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
  
  public static ArrayList<Crabby> GetCrabs() {
    BufferedImage img = GetSpriteAtlas(LEVEL_ATLAS);
    ArrayList<Crabby> list = new ArrayList<>();
    for (int j = 0; j < img.getHeight(); j++)
        for (int i = 0; i < img.getWidth(); i++) {
            Color color = new Color(img.getRGB(i, j));
            int value = color.getGreen();
            if (value == EnemyConstants.CRABBY)
                list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
        }
    return list;

}
  public static int[][] GetLevelData() {
    BufferedImage img = GetSpriteAtlas(LEVEL_ATLAS);
    int[][] lvlData = new int[img.getHeight()][img.getWidth()];

    for (int j = 0; j < img.getHeight(); j++)
        for (int i = 0; i < img.getWidth(); i++) {
            Color color = new Color(img.getRGB(i, j));
            int value = color.getRed();
            if (value >= 48)
                value = 0;
            lvlData[j][i] = value;
        }
    return lvlData;

}
  
  
}
