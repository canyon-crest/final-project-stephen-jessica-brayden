package entities;

import javax.swing.ImageIcon;

import Main.GamePanel;

// WindBoost obstacle that gives a effect to the player's horizontal and vertical velocity
public class WindBoost extends Obstacles {
	public WindBoost(GamePanel gp, int x, int y) {
		super("windBoost", gp, x, y);

	}
	public void effect() {
		super.effect();
		gp.playerYvelo+=100;
		gp.playerXvelo+=100;
	}
}
