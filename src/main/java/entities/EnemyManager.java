package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import utilz.Constants.EnemyConstants;
import utilz.LoadSave;
import gamestates.Playing;

public class EnemyManager {

  private Playing playing;
  private BufferedImage[][] crabbyArr;
  private ArrayList <Crabby> crabbies = new ArrayList<>();
  
  public EnemyManager(Playing playing) {
    this.playing = playing;
    loadEnemyImgs();
    addEnemies();
    }
  private void addEnemies() {
    crabbies = LoadSave.GetCrabs();  
    System.out.println("size of crabs: "+ crabbies.size());
  }
  public void update() {
    for(Crabby c : crabbies)
      c.update();
    
  }
  public void draw(Graphics g, int xLvlOffset) {
    drawCrabs(g, xLvlOffset);
  }
  
  private void drawCrabs(Graphics g,int xLvlOffset) {
    for(Crabby c : crabbies)
      g.drawImage(crabbyArr[c.getEnemyState()][c.getAniIndex()], (int)c.getHitbox().x - xLvlOffset, (int)c.getHitbox().y, EnemyConstants.CRABBY_WIDTH, EnemyConstants.CRABBY_HEIGHT, null);
    
  }
  private void loadEnemyImgs() {
    crabbyArr = new BufferedImage[5][9];
    BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
    for(int j=0; j< crabbyArr.length; j++)
      for(int i=0; i<crabbyArr[j].length;i++)
        crabbyArr[j][i] = temp.getSubimage(i * EnemyConstants.CRABBY_WIDTH_DEFAULT, j * EnemyConstants.CRABBY_HEIGHT_DEFAULT, EnemyConstants.CRABBY_WIDTH_DEFAULT, EnemyConstants.CRABBY_HEIGHT_DEFAULT);
  }
}
