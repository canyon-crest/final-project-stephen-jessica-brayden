package Main;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.Player;

// Main class that initializes the game window and starts the game thread
public class Main {
	
	public enum GameState {
		PLAYING,
		GAME_OVER,
		UPGRADE_MENU
	}

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("BirdBrain");
		
		GamePanel gamePanel = new GamePanel();
		UpgradeMenu upgrade = new UpgradeMenu(gamePanel.player);

		window.add(gamePanel);
		window.add(upgrade, BorderLayout.SOUTH);
		window.pack();
		
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
		upgrade.startUpgradeThread();
		

	}

}
