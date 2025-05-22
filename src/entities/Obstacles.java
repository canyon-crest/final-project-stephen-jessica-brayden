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
		this.x = x;
		this.y = y;
		width = gp.tileSize;
		height = gp.tileSize;
	}
	
	public void effect() {triggered = true;}
	
	public void move() {}

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








