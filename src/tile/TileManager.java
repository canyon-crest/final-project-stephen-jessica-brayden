package tile;


import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileManager {
	GamePanel gp;
	Tile[] tiles;
	int[] colLayout = new int[100];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tiles = new Tile[5];
		//setup tile layout
		for (int i = 0; i<colLayout.length;i++) {
			if (i<90) {
				colLayout[i] = 3;
			}
			else {
				colLayout[i] = 1;
			}
		}
		
		getTileImage();
	}
	
	public void getTileImage() {
			try {
				//tiles[0] = new Tile();
				//tiles[0].image = ImageIO.read(getClass().getResourceAsStream(""));
				tiles[1] = new Tile();
				tiles[1].image = ImageIO.read(getClass().getResourceAsStream("images/pixelDirt.png"));
				//tiles[2] = new Tile();
				//tiles[2].image = ImageIO.read(getClass().getResourceAsStream(""));
				tiles[3] = new Tile();
				tiles[3].image = ImageIO.read(getClass().getResourceAsStream("images/pixelSky.jpeg"));
			} catch(IOException e) {
				e.printStackTrace();
			}
	}
	
	public void draw(Graphics2D g2) {
		int col = 0;
		int row = 0;
		
		
		
		while (col<100 && row<gp.screenRow) {
			int worldX = col*gp.tileSize;
			int worldY = row*gp.tileSize;
			int screenX = worldX - gp.playerX + gp.screenWidth/2;
			int screenY = worldY- gp.playerY + gp.screenHeight/2;
			
			
			g2.drawImage(tiles[colLayout[row]].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			row++;
			
			
			if(row==100) {
				row = 0;
				col++;
			}
				
		}
	}
}
