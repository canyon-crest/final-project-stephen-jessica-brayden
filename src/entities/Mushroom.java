package entities;

import javax.swing.ImageIcon;

import Main.GamePanel;

public class Mushroom extends Obstacles {
	// Mushroom obstacle that gives a effect to the player's vertical velocity
	public Mushroom(GamePanel gp, int x) {
		super("mushroom", gp, x, 89 * gp.tileSize);
	}
	public void effect() {
		super.effect();
		gp.playerYvelo*=-1;
		gp.playerYvelo+=100;
	}
}