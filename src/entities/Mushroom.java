package entities;

import javax.swing.ImageIcon;

import Main.GamePanel;

public class Mushroom extends Obstacles {
	/**
	* the constructor for mushroom
	* @param gp    graphics to draw mushroom
	* @param x     x position of the mushroom
	*/
	public Mushroom(GamePanel gp, int x) {
		super("mushroom", gp, x, 89 * gp.tileSize);
	}
	
	/**
	* apply effect to the player's vertical velocity when hitting mushroom
	*/
	public void effect() {
		super.effect();
		gp.playerYvelo*=-1;
		gp.playerYvelo+=100;
	}
}