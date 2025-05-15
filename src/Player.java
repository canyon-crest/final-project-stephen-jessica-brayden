
public class Player {
	private int flapStrengthLevel = 1;
	private int flapLimitLevel = 1;
	private int liftLevel = 1;
	private int dragLevel = 1;
	private int launchLevel = 1;
	private String name = "";
	private int xVelo = 0;
	private int yVelo = 0;
	
	public Player(String name) {
		this.name = name;
	}
	
	public void upgradeFlapStrength() {
		flapStrengthLevel++;
	}
	
	public void upgradeFlapLimit() {
		flapLimitLevel++;
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
	
	public double getFlapStrength() {
		return Math.pow(1.3, flapStrengthLevel);
	}
	
	public int getFlapLimit() {
		return flapLimitLevel;
	}
	public double getLift() {
		return Math.pow(1.3, liftLevel);
	}
	public double getDrag() {
		return Math.pow(0.95, dragLevel);
	}
	public double getLaunch() {
		return Math.pow(1.3, launchLevel);
	}

	public int getxVelo() {
		return xVelo;
	}

	public void setxVelo(int xVelo) {
		this.xVelo = xVelo;
	}

	public int getyVelo() {
		return yVelo;
	}

	public void setyVelo(int yVelo) {
		this.yVelo = yVelo;
	}
	
	
}
