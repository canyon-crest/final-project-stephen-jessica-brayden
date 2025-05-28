package entities;

import javax.swing.ImageIcon;

import Main.GamePanel;

public class WindBoost extends Obstacles {
	/**
	* the constructor for windBoost
	* @param gp    graphics to draw windBoost
	* @param x     x position of the windBoost
	* @param y     y position of the windBoost
	*/
	public WindBoost(GamePanel gp, int x, int y) {
		super("windBoost", gp, x, y);

	}
	
	/**
	* apply effect to the player's horizontal and vertical velocity when hitting windBoost
	*/
	public void effect() {
		super.effect();
		gp.playerYvelo+=100;
		gp.playerXvelo+=100;
	}
}
