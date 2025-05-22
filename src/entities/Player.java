package entities;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Player {
	private int gameCurrency = 100;
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

	public void upgradeFlapStrength() {
		if(gameCurrency >= 20) {
			gameCurrency -= 20;
			flapStrengthLevel++;
		}
	}
	
	public void upgradeFlapLimit() {
		if(gameCurrency >= 30) {
			gameCurrency -= 30;
			flapLimitLevel++;
		}
	}
	
	public void upgradeLift() {
		liftLevel++;
	}
	
	public void upgradeDrag() {
		dragLevel++;
	}
	
	public void upgradeLaunch() {
		launchLevel++;
	}
	
	public int getCurrency() {
		return gameCurrency;
	}
	
	public double getFlapStrength() {
		return Math.pow(1.3, flapStrengthLevel);
	}
	
	public int getFlapLimit() {
		return (int) (1000000*Math.pow(1.3, flapLimitLevel));
	}
	public double getLift() {
		return Math.pow(0.95, liftLevel);
	}
	public double getDrag() {
		return Math.pow(0.95, dragLevel);
	}
	public double getLaunch() {
		return Math.pow(1.3, launchLevel);
	}
	public String getName() {
		return name;
	}
	
	public void getImage(boolean flap) {
		if (flap) {
			// Increment the flap counter
			flapCounter++;
	
			// Toggle the image based on the flap interval
			if (flapCounter % (2 * FLAP_INTERVAL) < FLAP_INTERVAL) {
				image = new ImageIcon("images/bird_04.png"); // Flapping image
			} else {
				image = new ImageIcon("images/bird_00.png"); // Idle image
			}
		} else {
			ImageIcon idleIcon = new ImageIcon("images/bird_01.png");
			Image resizedImage = idleIcon.getImage().getScaledInstance(
				200, 
				200, 
				Image.SCALE_SMOOTH 
			);
			image = new ImageIcon(resizedImage);
			System.out.println("Resized image to: " + 200 + "x" + 200);
			flapCounter = 0;
		}
	}

	
}
