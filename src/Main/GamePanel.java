package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entities.Obstacles;
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
	private Runnable gameOverListener;

	
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
	double playerXvelo = 0;
	double playerYvelo = 0;

	public int colPlayer = 0;
	int boostLimit = player.getFlapLimit();
	double angle;
	public boolean showLaunchLine = true;
	JLabel vOut;

	
	private List<Obstacles> obstacles = new ArrayList<>();

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		
		this.setLayout(new BorderLayout());
		vOut = new JLabel("X", SwingConstants.CENTER);
		vOut.setForeground(Color.WHITE);
		this.add(vOut, BorderLayout.NORTH);
		
		UpgradeMenu upgrade = new UpgradeMenu(player);
		this.add(upgrade, BorderLayout.SOUTH);
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
	
		
	public void setGameOverListener(Runnable listener) {
		this.gameOverListener = listener;
	}

		
	// Call this method when the game ends
	private void triggerGameOver() {
		if (gameOverListener != null) {
			gameOverListener.run();
		}
	}
	
	public Player getPlayer() {
		return this.player; 
	}

	private void checkCollisions() {
        // Check if the player hits the ground
        if (playerY > 89 * tileSize) {
            triggerGameOver();
        }

        // Check if the player collides with any obstacle
        for (Obstacles obstacle : obstacles) {
            if (obstacle.collidesWith(playerX, playerY, tileSize, tileSize)) {
                triggerGameOver();
                break;
            }
        }
    }
	
	//Update's game info
	public void update() {
		updatePlayerPos();
		checkCollisions(); 

		
		vOut.setText("Y: " + String.format("%9d" , -(playerY - 89*tileSize)) + "   X:" + String.format("%9d" , playerX) + "   Velocity:" + String.format("%12.2f" , playerXvelo) + "   Angle:" + String.format("%12.2f" , angle));
		
	}

	
	//Redraw's components on screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		for (Obstacles obstacle : obstacles) {
            g2.fillRect(obstacle.getBounds().x, obstacle.getBounds().y, obstacle.getBounds().width, obstacle.getBounds().height);
        }

		tiles.draw(g2);
		g2.drawImage(player.image.getImage(), screenWidth/2,screenHeight/2,tileSize,tileSize, null);

		if (showLaunchLine) {
			int startX = screenWidth / 2 + tileSize / 2; // Center of the player
			int startY = screenHeight / 2 + tileSize / 2;
			int endX = mouse.x; // Use the mouse's x position
			int endY = mouse.y; // Use the mouse's y position

			g2.setColor(Color.BLACK); // Set the line color
			g2.drawLine(startX, startY, endX, endY); 
		}
	}
	
	public void updatePlayerPos() {
		if (showLaunchLine && mouse.click) {
			playerXvelo = 100; 
			playerYvelo = 50;
			showLaunchLine = false;
		}

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
