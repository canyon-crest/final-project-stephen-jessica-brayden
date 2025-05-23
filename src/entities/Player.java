package entities;
import java.awt.Image;

import javax.swing.ImageIcon;

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

	public int upgradeFlapStrength() {
		if(gameCurrency >= 20*flapStrengthLevel) {
			gameCurrency -= 20*flapStrengthLevel;
			flapStrengthLevel++;
		}
		return flapStrengthLevel;
	}
	
	public int upgradeFlapLimit() {
		if(gameCurrency >= 30*flapLimitLevel) {
			gameCurrency -= 30*flapLimitLevel;
			flapLimitLevel++;
		}
		return flapLimitLevel;
	}
	
	public void upgradeLift() {
		liftLevel++;
	}
	
	public void upgradeDrag() {
		dragLevel++;
	}
	
	public int upgradeLaunch() {
		if(gameCurrency >= 100*launchLevel) {
			gameCurrency -= 100*launchLevel;
			launchLevel++;
			
		}
		return launchLevel;
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
