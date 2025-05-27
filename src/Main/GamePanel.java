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
import javax.sound.sampled.*;

//game panel that contains the game logic and rendering
public class GamePanel extends JPanel implements Runnable{
	
	//tile sizing
	public final int originalTileSize = 16;
	public final int scale = 3;
	public final int tileSize = originalTileSize*scale;
	public final int screenCol = 16;
	public final int screenRow = 12;	
	public final int screenWidth = tileSize*screenCol;
	public final int screenHeight = tileSize*screenRow;
	private boolean gameOver = false;

	
	//Game speed
	int FPS = 30;
	double scalar = 0.5;
	
	TileManager tiles = new TileManager(this);
	MouseHandler mouse = new MouseHandler();
	Thread gameThread;
	
	//setup player
	public Player player = new Player("player");
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

	// Screen messages
	public boolean showLaunchLine = true;
	private boolean showNoBoostMessage = false;

	//scores
	private int score = 0;
	private int highScore = 0;
	
	//background music clip
	private Clip backgroundClip;

	//list of obstacles
	private List<Obstacles> obstacles = new ArrayList<>();

	// Constructor
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		playBackgroundMusic("background.wav");
		
		this.setLayout(new BorderLayout());

	}

	// Start the game thread
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// Plays the sound file with the given filename
	public void playSound(String filename) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/" + filename));
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (Exception e) {
			System.out.println("Error playing sound: " + filename);
			e.printStackTrace();
		}
	}


	// Plays the background music from the given filename
	public void playBackgroundMusic(String filename) {
		try {
			if (backgroundClip != null && backgroundClip.isRunning()) {
				backgroundClip.stop();
				backgroundClip.close();
			}
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/" + filename));
			backgroundClip = AudioSystem.getClip();
			backgroundClip.open(audioIn);
			backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop forever
		} catch (Exception e) {
			System.out.println("Error playing background music: " + filename);
			e.printStackTrace();
		}
	}

	public void stopBackgroundMusic() {
		if (backgroundClip != null && backgroundClip.isRunning()) {
			backgroundClip.stop();
			backgroundClip.close();
		}
	}

	@Override
	public void run() {
		//Game time
		
		try {
			Thread.sleep((long) 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
				e.printStackTrace();
			}
		}
	}
	
		

		
	// Call this method when the game ends
	private void triggerGameOver() {
		gameOver = true;
		showLaunchLine = true;
		player.addCurrency(score);
		if (score>highScore) {
			highScore = score;
		}
		score = 0;
		playerX = screenWidth/4;
		boostLimit = player.getFlapLimit();
	}
	
	public Player getPlayer() {
		return this.player; 
	}

	private void checkCollisions() {
        // Check if the player hits the ground
        if (playerY >= 89 * tileSize && !showLaunchLine && playerXvelo<1) {
            triggerGameOver();
			playSound("lose.wav");
			return;
        }

		//List traversal
        // Check if the player collides with any obstacle
        for (Obstacles obstacle : obstacles) {
            if (obstacle.collidesWith(playerX, playerY, tileSize, tileSize)) {
                obstacle.effect();
				playSound("hit.wav");
            }
        }
    }
	
	//Update's game info
	public void update() {
		addObstacles();
		List<Obstacles> toRemove = new ArrayList<>();
		//List traversal
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

	}

	
	//Redraw's components on screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tiles.draw(g2);
		for (Obstacles obstacle : obstacles) {
			g2.drawImage(obstacle.getOImage().getImage(), screenWidth/4 + obstacle.x - playerX, screenHeight/2 + obstacle.y - playerY, tileSize, tileSize, null);

        }
		if (showNoBoostMessage) {
			String noBoostText = "*No Boost Remaining!*";
			g2.setFont(g2.getFont().deriveFont(36f));
			g2.setColor(Color.RED);
			int textWidth = g2.getFontMetrics().stringWidth(noBoostText);
			int textX = (screenWidth - textWidth) / 2;
			int textY = screenHeight / 2 - 100;
			g2.drawString(noBoostText, textX, textY);
		}
		g2.drawImage(player.image.getImage(), screenWidth/4,screenHeight/2,60,60, null);
		g2.setFont(g2.getFont().deriveFont(15f)); // Set font size to 15
		g2.setColor(Color.BLACK); // Set text color
		String boardText = "Y: " + String.format("%9d", -(playerY - 89 * tileSize)) +
		        "   X:" + String.format("%9d", playerX - screenWidth / 4) +
		        "   Velocity:" + String.format("%12.2f", playerXvelo) +
		        "   Boost Remaining: "+boostLimit +
		        "  Score: " + score +
		        "  High Score: " + highScore;
		int text1Width = g2.getFontMetrics().stringWidth(boardText);
		int text1X = (screenWidth - text1Width) / 2; // Center the text horizontally
		int text1Y = 20; // Position the text at the top
		g2.drawString(boardText, text1X, text1Y);

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
			boostLimit = player.getFlapLimit();
		}

		if (gameOver) {
			g2.setFont(g2.getFont().deriveFont(48f));
			g2.setColor(Color.RED);
			String gameOverText = "Landed! Press to launch again";
			int textWidth = g2.getFontMetrics().stringWidth(gameOverText);
			int textX = (screenWidth - textWidth) / 2;
			int textY = screenHeight / 2;
			g2.drawString(gameOverText, textX, textY);
		}
		g2.dispose();
	}
	
	public void updatePlayerPos() {
		// Launch the player if the launch line is shown and the mouse is clicked
		if (showLaunchLine && mouse.click) {
			playSound("launch.wav");
			double launchAngle = Math.atan2(mouse.y - (screenHeight / 2), mouse.x - (screenWidth / 4));
			
			playerXvelo = launchAcceleration * Math.cos(launchAngle); 
			playerYvelo = -launchAcceleration * Math.sin(launchAngle); 
			
			showLaunchLine = false;
			isLaunching = true; 
			gameOver = false;
		}
	
		// Launch the player
		if (isLaunching) {
			double speed = Math.sqrt(playerXvelo * playerXvelo + playerYvelo * playerYvelo);
			if (speed < maxLaunchSpeed*player.getLaunch()) {
				playerXvelo += launchAcceleration * (playerXvelo / speed); 
				playerYvelo += launchAcceleration * (playerYvelo / speed); 
			} else {
				isLaunching = false; 
			}
			
			player.getImage(true); 
		} else {
			// Update player position based on velocity
			angle = (mouse.y - this.getLocationOnScreen().getY() - 100) / 25 / Math.sqrt(screenWidth ^ 2 + screenHeight ^ 2);
			playerYvelo = ((playerYvelo - 0.5 * playerXvelo * player.getLift() * (angle) - 5) * scalar);
			playerXvelo = playerXvelo - (playerXvelo * player.getDrag() * Math.abs(angle) + playerYvelo * Math.sqrt(1 - angle * angle)) * scalar/20;
			if (mouse.click&&boostLimit>0) {
				playerYvelo += player.getFlapStrength() * scalar * (-angle);
				playerXvelo += player.getFlapStrength() * scalar * Math.sqrt(1 - angle * angle);
				boostLimit--;
				player.getImage(mouse.click);
				showNoBoostMessage = false;
			}
			else if (mouse.click && boostLimit<=0) {
				showNoBoostMessage = true;
			}
			else{
				showNoBoostMessage = false;
			}
		}


		 if (Double.isNaN(playerXvelo) || Double.isNaN(playerYvelo)) {
				System.out.println("DEBUG: Velocity is NaN! playerXvelo=" + playerXvelo + ", playerYvelo=" + playerYvelo);
				playerXvelo = 0;
				playerYvelo = 0;
		}


		playerY -= (int)playerYvelo;
		playerX += (int)playerXvelo;

		score = Math.max(score, playerX/500);
		if (playerY<0){
			playerY = 0;
			playerYvelo = 0;
		}

		if(playerY>89*tileSize){
			playerY  = 89*tileSize;
			playerYvelo = 0;
			playerXvelo-=playerXvelo*player.getDrag()/10;
		}
		colPlayer = playerX/tileSize;
	}
	
	// Adds obstacles to the game at random intervals
	public void addObstacles() {
		if (!showLaunchLine) {
			double rng = Math.random();
		
		
			if (rng<0.01) {
				obstacles.add(new Mushroom(this, playerX + 2*screenWidth));
			}
			else if (rng<0.015) {
				obstacles.add(new Bat(this, playerX + 2*screenWidth, playerY));
			}
			else if (rng<0.025) {
				obstacles.add(new WindBoost(this, playerX+2*screenWidth,playerY-6+(int)(12*(Math.random()*tileSize))));
			}
		}
	}

}
