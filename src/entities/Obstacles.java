package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;

public class Obstacles {

    private int x, y, width, height;

    public Obstacles(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean collidesWith(int playerX, int playerY, int playerWidth, int playerHeight) {
        Rectangle playerBounds = new Rectangle(playerX, playerY, playerWidth, playerHeight);
        return playerBounds.intersects(getBounds());
    }
}
