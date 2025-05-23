package entities;

import javax.swing.ImageIcon;

import Main.GamePanel;

public class Mushroom extends Obstacles {
	public Mushroom(GamePanel gp, int x) {
		super("mushroom", gp, x, 89 * gp.tileSize - gp.tileSize);
	}
	public void effect() {
		super.effect();
		gp.playerYvelo*=-1;
		gp.playerYvelo+=100;
	}
}