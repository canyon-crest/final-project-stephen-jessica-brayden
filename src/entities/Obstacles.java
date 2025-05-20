package entities;

import Main.GamePanel;

public class Obstacles {
	private String type = "";
	protected GamePanel gp;
	public int x;
	public int y;
	
	public Obstacles(String type, GamePanel gp, int x, int y) {
		this.type = type;
		this.gp = gp;
	}
	
	public void effect() {}
	
	public void move() {}
}

class Bat extends Obstacles {
	public Bat(GamePanel gp, int x, int y) {
		super("bat", gp, x, y);
	}
	public void effect() {
		gp.playerXvelo/=2;
	}
	public void move() {
		if (gp.playerY<y) {
			y-=gp.tileSize;
		}
		else {
			y+=gp.tileSize;
		}
	}
}


class WindBoost extends Obstacles {
	public WindBoost(GamePanel gp, int x, int y) {
		super("windBoost", gp, x, y);
	}
	public void effect() {
		gp.playerYvelo+=100;
		gp.playerXvelo+=100;
	}
}


class Mushroom extends Obstacles {
	public Mushroom(GamePanel gp, int x) {
		super("mushroom", gp, x, 89*gp.tileSize);
	}
	public void effect() {
		gp.playerYvelo+=gp.playerYvelo;
	}
}

