package entities;

import Main.GamePanel;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Obstacles class represents various obstacles in the game.
 * It handles their position, movement, collision detection, and rendering.
 */
public class Obstacles {
	private String type = "";
	protected GamePanel gp;
	public int x;
	public int y;
	public int width;
	public int height;
	public ImageIcon image;
	public boolean triggered = false;
 
	/**
	* the constructor for obstacles (parent class)
	* @param type  type of obstacle (bat, mushroom, or WindBoost)
	* @param gp    graphics to draw obstacles
	* @param x     x position of the obstacle
	* @param y     y position of the obstacle
	*/
	public Obstacles(String type, GamePanel gp, int x, int y) {
		this.type = type;
		this.gp = gp;
		this.x = x ;
		this.y = y;
		width = gp.tileSize;
		height = gp.tileSize;
	}
	
	/**
	* apply effect of the obstacle to the player
	*/
	public void effect() {triggered = true;}
	
	/**
	* moves obstacles position and resets position depending on their type
	*/
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
	
	/**
	* draws image of obstacle at specified position
	* @param g2     graphics to draw obstacle
	*/
	public void draw(java.awt.Graphics2D g2) {
		g2.drawImage(getOImage().getImage(), x, y, width, height, null);
	}
	
	/**
	* @return type of obstacle
	*/
	public String getType() {
		return type;
	}
	
	/**
	* @return x position of obstacle
	*/
	public int getX() {
		return x;
	}
	
	/**
	* @return y position of obstacle
	*/
	public int getY() {
		return y;
	}
	
	/**
	* @return rectangle bounds of the obstacle
	*/
	public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
	
	/**
	* @param playerX         x position of player
	* @param playerY         y position of player
	* @param playerWidth     width of the player
	* @param playerHeight    height of the player
	* @return boolean value of whether or not there is a collision
	*/
    public boolean collidesWith(int playerX, int playerY, int playerWidth, int playerHeight) {
        Rectangle playerBounds = new Rectangle(gp.playerX, gp.playerY, gp.tileSize, gp.tileSize);
        return playerBounds.intersects(getBounds());
    }
	
    /**
	* @return image for each type of obstacle
	*/
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








