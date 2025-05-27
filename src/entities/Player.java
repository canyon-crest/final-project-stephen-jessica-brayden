package entities;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.sound.sampled.*;

// Player class that holds the player's attributes and methods for upgrading and playing sounds
public class Player {
	private int gameCurrency = 10;
	private int flapStrengthLevel = 1;
	private int flapLimitLevel = 1;
	private int liftLevel = 1;
	private int dragLevel = 1;
	private int launchLevel = 1;
	private String name = "";
	public ImageIcon image = new ImageIcon("images/bird_04.png");
	private int flapCounter = 0;
	private final int FLAP_INTERVAL = 8; 
	
	public Player(String name) {
		this.name = name;
	}

	//play the sound effects
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
	


	public int upgradeFlapStrength() {
		int cost = (int)(40*getFlapStrength()*flapStrengthLevel/1.1);
		if(gameCurrency >= cost ) {
			playSound("upgrade.wav");
			gameCurrency -= cost;
			flapStrengthLevel++;
		}
		else{
			playSound("error.wav");
		}
		return (int)(40*getFlapStrength()*flapStrengthLevel/1.1);
	}
	
	public int upgradeFlapLimit() {
		int cost = (int)(30*getFlapLimit()*flapLimitLevel/110);
		if(gameCurrency >= cost) {
			playSound("upgrade.wav");
			gameCurrency -= cost;
			flapLimitLevel++;
		}
		else{
			playSound("error.wav");
		}
		return (int)(30*getFlapLimit()*flapLimitLevel/110);
	}
	
	public void upgradeLift() {
		liftLevel++;
	}
	
	public void upgradeDrag() {
		dragLevel++;
	}
	
	public int upgradeLaunch() {
		int cost = (int)(50*getLaunch()*launchLevel/1.1);
		if(gameCurrency >= cost) {
			playSound("upgrade.wav");
			gameCurrency -= cost;
			launchLevel++;
		}
		else{
			playSound("error.wav");
		}
		return  (int)(50*getLaunch()*launchLevel/1.1);
	}
	
	public int getCurrency() {
		return gameCurrency;
	}
	
	public double getFlapStrength() {
		return Math.pow(1.1, flapStrengthLevel);
	}
	
	public int getFlapLimit() {
		return (int) (100*Math.pow(1.1, flapLimitLevel));
	}
	public double getLift() {
		return Math.pow(0.95, liftLevel);
	}
	public double getDrag() {
		return Math.pow(0.95, dragLevel);
	}
	public double getLaunch() {
		return Math.pow(1.1, launchLevel);
	}
	public String getName() {
		return name;
	}
	public int addCurrency(int x) {
		gameCurrency+=x;
		return gameCurrency;
	}
	
	public void getImage(boolean flap) {
		if (flap) {
			flapCounter++;
	
			if (flapCounter % (2 * FLAP_INTERVAL) < FLAP_INTERVAL) {
				image = new ImageIcon("images/bird_04.png"); 
			} else {
				image = new ImageIcon("images/bird_00.png"); 
			}
		} else {
			image = new ImageIcon("images/bird_01.png");
			
			flapCounter = 0;
		}
	}

	
}
