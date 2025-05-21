package entities;

import Main.GamePanel;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Obstacles {
	private String type = "";
	protected GamePanel gp;
	public int x;
	public int y;
	public ImageIcon image;

	
    public Rectangle getBounds() {
        return new Rectangle(x, y, gp.tileSize, gp.tileSize);
    }

    public boolean collidesWith(int playerX, int playerY, int playerWidth, int playerHeight) {
        Rectangle playerBounds = new Rectangle(playerX, playerY, playerWidth, playerHeight);
        return playerBounds.intersects(getBounds());
    }
	
	public Obstacles(String type, GamePanel gp, int x, int y) {
		this.type = type;
		this.gp = gp;
		this.x = x;
		this.y = y;
	}
	
	public void effect() {}
	
	public void move() {}
}

class Bat extends Obstacles {
	public Bat(GamePanel gp, int x, int y) {
		super("bat", gp, x, y);
		this.image = new ImageIcon("images/bat.png");
	}
	public void effect() {
		gp.playerXvelo/=2;
	}
	public void move() {
		if (gp.playerY<y) {
			y-=gp.tileSize;
		}
		else {
			y+=gp.tileSize;
		}
		x+=100;
	}
}


class WindBoost extends Obstacles {
	public WindBoost(GamePanel gp, int x, int y) {
		super("windBoost", gp, x, y);
		this.image = new ImageIcon("images/windboost.png");
	}
	public void effect() {
		gp.playerYvelo+=100;
		gp.playerXvelo+=100;
	}
}


class Mushroom extends Obstacles {
	public Mushroom(GamePanel gp, int x) {
		super("mushroom", gp, x, 89*gp.tileSize);
		this.image = new ImageIcon("images/mushroom.png");
	}
	public void effect() {
		gp.playerYvelo*=-1;
	}
}

