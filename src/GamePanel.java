import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GamePanel extends JPanel implements Runnable{
	
	//tile sizing
	final int originalTileSize = 16;
	final int scale = 3;
	final int tileSize = originalTileSize*scale;
	final int screenWidth = tileSize*16;
	final int screenHeight = tileSize*12;
	
	//Game speed
	int FPS = 30;
	double scalar = 0.6;
	
	MouseHandler mouse = new MouseHandler();
	Thread gameThread;
	
	
	//setup player
	Player player = new Player("player");
	int playerX = 100;
	int playerY = 100;
	double playerXvelo = 1000;
	double playerYvelo = 0;
	int boostLimit = player.getFlapLimit();
	double angle;
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
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
		angle = (mouse.y-this.getLocationOnScreen().getY()-100)/75/Math.sqrt(screenWidth^2+screenHeight^2);
		playerYvelo = ((playerYvelo-0.5*playerXvelo*player.getLift()*(angle)-1)*scalar);
		playerXvelo = (playerXvelo-playerXvelo*player.getDrag()*(1-Math.abs(angle))*scalar*1/100);
		if (mouse.click == true) {
			playerYvelo += 100*player.getFlapStrength()*scalar*(-angle);
			playerXvelo += 10*player.getFlapStrength()*scalar*Math.sqrt(1-angle*angle);
			boostLimit--;
		}
		playerY -= (int)playerYvelo;
		//playerX += (int)playerXvelo;
		System.out.print(playerXvelo+" "+playerYvelo+"\r");

		
	}
	//Redraw's components on screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.white);
		g2.fillRect(100,playerY,tileSize,tileSize);
		/*
		ImageIcon image1 = new ImageIcon("images/bird_00.png");
		g2.drawImage(image1.getImage(), 400, 200, 100, 100, null);
		ImageIcon image2 = new ImageIcon("images/bird_04.png");
		g2.drawImage(image2.getImage(), 500, 200, 100, 100, null);
		*/
		
		g2.dispose();
	}
}
