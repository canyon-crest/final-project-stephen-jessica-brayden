package Main;
import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.Player;

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
		
		//JPanel display = new JPanel();
		window.add(gamePanel);
		window.pack();
		
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
		
		gamePanel.setGameOverListener(() -> {
            window.dispose(); 
        });
	}

}
