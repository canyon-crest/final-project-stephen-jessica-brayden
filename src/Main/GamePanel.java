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
import entities.Mushroom;
import entities.Bat;
import entities.WindBoost;
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
	private boolean gameOver = false;

	
	//Game speed
	int FPS = 30;
	double scalar = 0.5;
	
	TileManager tiles = new TileManager(this);
	MouseHandler mouse = new MouseHandler();
	Thread gameThread;
	
	
	//setup player
	Player player = new Player("player");
	public int playerX = screenWidth/4;
	public int playerY = 89*tileSize;
	public double playerXvelo = 0;
	public double playerYvelo = 0;
	private double launchAcceleration = 2;
	private double maxLaunchSpeed = 100;
	private boolean isLaunching = false;


	public int colPlayer = 0;
	int boostLimit = player.getFlapLimit();
	double angle;
	public boolean showLaunchLine = true;
	JLabel vOut;

	private int score = 0;
	private int launchDelayCounter = 0; 
	private final int LAUNCH_DELAY = 30; 
	private boolean launchDone = false;
	
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
		//Game time
		double drawInterval = 1000000000/FPS;
		double nextTime = System.nanoTime()+drawInterval;

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
		/*
		if (gameOverListener != null) {
			gameOverListener.run();
			gameOver = true;
		}
		*/
		gameOver = true;
	}
	
	public Player getPlayer() {
		return this.player; 
	}

	private void checkCollisions() {
        // Check if the player hits the ground
        if (playerY >= 89 * tileSize && launchDone) {
            triggerGameOver();
			return;
        }

        // Check if the player collides with any obstacle
        for (Obstacles obstacle : obstacles) {
            if (obstacle.collidesWith(playerX, playerY, tileSize, tileSize)) {
                obstacle.effect();
                break;
            }
        }
    }
	
	//Update's game info
	public void update() {
		addObstacles();
		List<Obstacles> toRemove = new ArrayList<>();
	    for (Obstacles obstacle : obstacles) {
	        if (obstacle.x < playerX - screenWidth || obstacle.triggered) {
	            toRemove.add(obstacle);
	        }
	        else {
	        	obstacle.move();
	        }
	    }
	    obstacles.removeAll(toRemove);

	    
	    updatePlayerPos();
	    checkCollisions();

	    vOut.setText("Y: " + String.format("%9d", -(playerY - 89 * tileSize)) +
	        "   X:" + String.format("%9d", playerX - screenWidth / 4) +
	        "   Velocity:" + String.format("%12.2f", playerXvelo) +
	        "   Angle:" + String.format("%12.2f", angle) +
	        "  Score: " + score);
	}

	
	//Redraw's components on screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		tiles.draw(g2);
		for (Obstacles obstacle : obstacles) {
			g2.drawImage(obstacle.getOImage().getImage(), screenWidth/4 + obstacle.x - playerX, screenHeight/2 - obstacle.y + playerY, tileSize, tileSize, null);
        }
		
		g2.drawImage(player.image.getImage(), screenWidth/4,screenHeight/2,tileSize,tileSize, null);


		if (showLaunchLine) {
			int startX = screenWidth / 4 + tileSize / 2; // Center of the player
			int startY = screenHeight / 2 + tileSize / 2;
			int endX = mouse.x; 
			int endY = mouse.y; 

			g2.setColor(Color.BLACK);
			g2.drawLine(startX, startY, endX, endY); 

			g2.setFont(g2.getFont().deriveFont(48f)); // Set font size to 48
			g2.setColor(Color.BLACK); // Set text color
			String launchText = "Launch the Bird!";
			int textWidth = g2.getFontMetrics().stringWidth(launchText);
			int textX = (screenWidth - textWidth) / 2; // Center the text horizontally
			int textY = screenHeight / 4; // Position the text near the top
			g2.drawString(launchText, textX, textY);
		}

		if (gameOver) {
			g2.setFont(g2.getFont().deriveFont(48f));
			g2.setColor(Color.RED);
			String gameOverText = "Game Over!";
			int textWidth = g2.getFontMetrics().stringWidth(gameOverText);
			int textX = (screenWidth - textWidth) / 2;
			int textY = screenHeight / 2;
			g2.drawString(gameOverText, textX, textY);
		}
	}
	
	public void updatePlayerPos() {
		if (showLaunchLine && mouse.click) {
			double launchAngle = Math.atan2(mouse.y - (screenHeight / 2), mouse.x - (screenWidth / 2));
			
			playerXvelo = launchAcceleration * Math.cos(launchAngle); 
			playerYvelo = -launchAcceleration * Math.sin(launchAngle); 
			
			showLaunchLine = false;
			isLaunching = true; 
		}
	
		if (isLaunching) {
			double speed = player.getLaunch()*Math.sqrt(playerXvelo * playerXvelo + playerYvelo * playerYvelo);
			if (speed < maxLaunchSpeed) {
				playerXvelo += launchAcceleration * (playerXvelo / speed); 
				playerYvelo += launchAcceleration * (playerYvelo / speed); 
			} else {
				isLaunching = false; 
			}
	
			player.getImage(true); 
		} else {

			angle = (mouse.y - this.getLocationOnScreen().getY() - 100) / 25 / Math.sqrt(screenWidth ^ 2 + screenHeight ^ 2);
			playerYvelo = ((playerYvelo - 0.5 * playerXvelo * player.getLift() * (angle) - 5) * scalar);
			playerXvelo = playerXvelo - (playerXvelo * player.getDrag() * Math.abs(angle) + playerYvelo * Math.sqrt(1 - angle * angle)) * scalar/20;
			if (mouse.click) {
				playerYvelo += player.getFlapStrength() * scalar * (-angle);
				playerXvelo += player.getFlapStrength() * scalar * Math.sqrt(1 - angle * angle);
				boostLimit--;
			}
			player.getImage(mouse.click);
		}


		playerY -= (int)playerYvelo;
		playerX += (int)playerXvelo;

		score = Math.max(score, playerX/500);
		if (playerX<0){
			playerX = 0;
			playerXvelo = 0;
			playerYvelo-=playerYvelo*player.getDrag()/30;
		}

		if(playerY>89*tileSize){
			playerY  = 89*tileSize;
			playerYvelo = 0;
			playerXvelo-=playerXvelo*player.getDrag()/10;
		}
		colPlayer = playerX/tileSize;
	}
	
	public void addObstacles() {
		double rng = Math.random();
		if (rng<0.01) {
			obstacles.add(new Mushroom(this, playerX + 2*screenWidth));
		}
		else if (rng<0.015) {
			obstacles.add(new Bat(this, playerX + 2*screenWidth, playerY));
		}
		else if (rng<0.025) {
			obstacles.add(new WindBoost(this, playerX + 2 * screenWidth, playerY - 3 + (int)(6*(Math.random()*tileSize))));
		}
	}
	
}
