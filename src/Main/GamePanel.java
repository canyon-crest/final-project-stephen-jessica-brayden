package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entities.Player;
import tile.TileManager;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GamePanel extends JPanel implements Runnable{
	
	//tile sizing
	public final int originalTileSize = 16;
	public final int scale = 3;
	public final int tileSize = originalTileSize*scale;
	public final int screenCol = 16;
	public final int screenRow = 12;	
	public final int screenWidth = tileSize*screenCol;
	public final int screenHeight = tileSize*screenRow;
	
	//Game speed
	int FPS = 30;
	double scalar = 0.5;
	
	TileManager tiles = new TileManager(this);
	MouseHandler mouse = new MouseHandler();
	Thread gameThread;
	
	
	//setup player
	Player player = new Player("player");
	public int playerX = 100;
	public int playerY = 100;
	double playerXvelo = 100;
	double playerYvelo = 0;
	int boostLimit = player.getFlapLimit();
	double angle;
	
	JLabel vOut;
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		vOut = new JLabel("X", SwingConstants.CENTER);
		vOut.setForeground(Color.WHITE);
		this.add(vOut);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		double drawInterval = 1000000000/FPS;
		double nextTime = System.nanoTime()+drawInterval;
		try {
			Thread.sleep((long) 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (gameThread != null) {
			update();
			
			repaint();
			
			try {
				double remainingTime = nextTime- System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime <0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextTime += drawInterval;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	//Update's game info
	public void update() {
		updatePlayerPos();
		
		System.out.print(playerX+" "+playerY+" "+playerXvelo+"\r");
		vOut.setText(playerYvelo+" "+playerXvelo);
		
	}

	
	//Redraw's components on screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tiles.draw(g2);
		g2.setColor(Color.white);
		g2.fillRect(100,100,tileSize,tileSize);
		/*
		ImageIcon image1 = new ImageIcon("images/bird_00.png");
		g2.drawImage(image1.getImage(), 400, 200, 100, 100, null);
		ImageIcon image2 = new ImageIcon("images/bird_04.png");
		g2.drawImage(image2.getImage(), 500, 200, 100, 100, null);
		*/
		
		g2.dispose();
	}
	
	public void updatePlayerPos() {
		angle = (mouse.y-this.getLocationOnScreen().getY()-75)/50/Math.sqrt(screenWidth^2+screenHeight^2);
		playerYvelo = ((playerYvelo-0.5*playerXvelo*player.getLift()*(angle)-2)*scalar);
		playerXvelo = (playerXvelo-playerXvelo*player.getDrag()*(Math.abs(angle))*scalar/25);
		if (mouse.click == true) {
			playerYvelo += 100*player.getFlapStrength()*scalar*(-angle);
			playerXvelo += 10*player.getFlapStrength()*scalar*Math.sqrt(1-angle*angle);
			boostLimit--;
		}
		playerY -= (int)playerYvelo;
		playerX += (int)playerXvelo;
	}
	
}
