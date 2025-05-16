import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class GamePanel extends JPanel implements Runnable{
	
	//tile sizing
	final int originalTileSize = 16;
	final int scale = 3;
	final int tileSize = originalTileSize*scale;
	final int screenWidth = tileSize*16;
	final int screenHeight = tileSize*12;
	
	MouseHandler mouse = new MouseHandler();
	Thread gameThread;
	
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		update();
		repaint();
		
	}
	
	//Update's game info
	public void update() {
		
	}
	//Redraw's components on screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.white);
		g2.fillRect(100,100,tileSize,tileSize);
		
		ImageIcon image1 = new ImageIcon("images/bird_00.png");
		g2.drawImage(image1.getImage(), 400, 200, 100, 100, null);
		ImageIcon image2 = new ImageIcon("images/bird_04.png");
		g2.drawImage(image2.getImage(), 500, 200, 100, 100, null);
		
		g2.dispose();
	}
}
