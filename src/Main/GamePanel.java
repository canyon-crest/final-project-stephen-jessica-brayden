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
	public int playerX = 0;
	public int playerY = 89*tileSize;
	public double playerXvelo = 100;
	public double playerYvelo = 50;
	public int colPlayer = 0;
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
		
		
		vOut.setText("Y: "+-(playerY - 89*tileSize)+" X:"+playerX+" Velocity:"+playerXvelo+" Angle:"+angle);
		
	}

	
	//Redraw's components on screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tiles.draw(g2);
		g2.drawImage(player.image.getImage(), screenWidth/2,screenHeight/2,tileSize,tileSize, null);
	}
	
	public void updatePlayerPos() {
		angle = (mouse.y-this.getLocationOnScreen().getY()-100)/25/Math.sqrt(screenWidth^2+screenHeight^2);
		playerYvelo = ((playerYvelo-0.5*playerXvelo*player.getLift()*(angle)-5)*scalar);
		playerXvelo = (playerXvelo-playerXvelo*player.getDrag()*(Math.abs(angle)+0.1*playerYvelo*Math.sqrt(1-angle*angle))*scalar/30);
		if (mouse.click) {
			playerYvelo += player.getFlapStrength()*scalar*(-angle);
			playerXvelo += player.getFlapStrength()*scalar*Math.sqrt(1-angle*angle);
			boostLimit--;
		}
		player.getImage(mouse.click);
		playerY -= (int)playerYvelo;
		playerX += (int)playerXvelo;
		if(playerY>89*tileSize){
			playerY  = 89*tileSize;
			playerYvelo = 0;
			playerXvelo-=playerXvelo*player.getDrag()/30;
		}
		colPlayer = playerX/tileSize;
	}
	
}
