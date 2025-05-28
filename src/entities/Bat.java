package entities;

import javax.swing.ImageIcon;

import Main.GamePanel;

public class Bat extends Obstacles {
	/**
	* the constructor for bat
	* @param gp    graphics to draw bat
	* @param x     x position of the bat
	* @param y     y position of the bat
	*/
	public Bat(GamePanel gp, int x, int y) {
		super("bat", gp, x, y);

	}
	
	/**
	* apply effect to the player's horizontal velocity when hitting bat
	*/
	public void effect() {
		super.effect();
		gp.playerXvelo/=2;
	}
	
	/**
	* moves the bat's position
	*/
	public void move() {
		if (gp.playerY<y) {
			y-=gp.tileSize/10;
		}
		else {
			y+=gp.tileSize/10;
		}
		x+=(int)(gp.playerXvelo/2);
	}
}

