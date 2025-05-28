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
	
	/**
	* constructor that sets up the player
	* @param name     name of player
	*/
	public Player(String name) {
		this.name = name;
	}

	/**
	* play the sound effects
	* @param fileName     file name of the audio to be played
	*/
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
	
	/**
	* Upgrade methods that increase the player's abilities and deduct currency
	* @return value of upgraded flapStrength
	*/
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
	
	/**
	* Upgrade method that increases the player's flap limit and deducts currency
	* @return value of upgraded flapLimit
	*/
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
	
	/**
	* Upgrade method that increases the player's lift
	*/
	public void upgradeLift() {
		liftLevel++;
	}
	
	/**
	* Upgrade method that increases the player's drag
	*/
	public void upgradeDrag() {
		dragLevel++;
	}
	
	/**
	* Upgrade method that increases the player's launch strength and deducts currency
	* @return value of upgraded launch power
	*/
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
	
	/**
	* @return value of gameCurrency
	*/
	public int getCurrency() {
		return gameCurrency;
	}
	
	/**
	* @return value of flapStrength
	*/
	public double getFlapStrength() {
		return Math.pow(1.1, flapStrengthLevel);
	}
	
	/**
	* @return value of flapLimit
	*/
	public int getFlapLimit() {
		return (int) (100*Math.pow(1.1, flapLimitLevel));
	}
	
	/**
	* @return value of lift
	*/
	public double getLift() {
		return Math.pow(0.95, liftLevel);
	}
	
	/**
	* @return value of drag
	*/
	public double getDrag() {
		return Math.pow(0.95, dragLevel);
	}
	
	/**
	* @return value of launch power
	*/
	public double getLaunch() {
		return Math.pow(1.1, launchLevel);
	}
	
	/**
	* @return value of player's name
	*/
	public String getName() {
		return name;
	}
	
	/**
	* adds currency and returns amount after adding
	* @param x     amount of currency to be added
	* @return value of gameCurrency
	*/
	public int addCurrency(int x) {
		gameCurrency+=x;
		return gameCurrency;
	}
	
	/**
	* Method to get the current image of the player based on flap state
	* @param flap     boolean value of if the bird is flapping
	*/
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
