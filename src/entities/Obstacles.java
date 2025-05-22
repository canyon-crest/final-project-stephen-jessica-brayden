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
	public int width;
	public int height;
	public ImageIcon image;
	public boolean triggered = false;
 
	
	public Obstacles(String type, GamePanel gp, int x, int y) {
		this.type = type;
		this.gp = gp;
		this.x = x ;
		this.y = y;
		width = gp.tileSize;
		height = gp.tileSize;
	}
	
	public void effect() {triggered = true;}
	
	public void move() {
		x -= 2; // Move the obstacle to the left
	
		// Reset the obstacle's position if it moves off-screen
		if (x < -width) {
			x = gp.screenWidth + new Random().nextInt(100); // Reset x position
			if (type.equals("bat")) {
				y = 89 * gp.tileSize - 5 * gp.tileSize; // Mid-air level for bats
			} else if (type.equals("windBoost")) {
				y = 89 * gp.tileSize - 2 * gp.tileSize; // Slightly above ground for wind boosts
			} else if (type.equals("mushroom")) {
				y = 89 * gp.tileSize - gp.tileSize; // Near the ground for mushrooms
			}
		}
	}

	public void draw(java.awt.Graphics2D g2) {
		g2.drawImage(getOImage().getImage(), x, y, width, height, null);
	}

	public String getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean collidesWith(int playerX, int playerY, int playerWidth, int playerHeight) {
        Rectangle playerBounds = new Rectangle(gp.playerX, gp.playerY, gp.tileSize, gp.tileSize);
        return playerBounds.intersects(getBounds());
    }
	
    public ImageIcon getOImage() {
    	if (type.equals("bat"))
    		image = new ImageIcon("images/bat.png");
    	else if (type.equals("windBoost"))
    		image = new ImageIcon("images/windboost.png");
    	else if (type.equals("mushroom"))
    		image = new ImageIcon("images/mushroom.png");
    	return image;
    }
}








