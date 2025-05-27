package entities;

import javax.swing.ImageIcon;

import Main.GamePanel;

public class Bat extends Obstacles {
// Bat obstacle that gives a effect to the player's horizontal velocity
	public Bat(GamePanel gp, int x, int y) {
		super("bat", gp, x, y);

	}
	public void effect() {
		super.effect();
		gp.playerXvelo/=2;
	}
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

