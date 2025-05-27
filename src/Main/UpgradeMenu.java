package Main;

import entities.Player;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.sound.sampled.*;


public class UpgradeMenu extends JPanel implements Runnable {
	JLabel playerCurrency = new JLabel("Player Currency: $10", SwingConstants.CENTER);
    JLabel strengthUpgradeCost = new JLabel("Flap Strength Upgrade: $40", SwingConstants.CENTER);
    JLabel limitUpgradeCost = new JLabel("Flap Limit Upgrade: $30", SwingConstants.CENTER);
    JLabel launchUpgradeCost = new JLabel("Launch Upgrade: $50", SwingConstants.CENTER);
	
    JLabel label = new JLabel("Choose Upgrades:", SwingConstants.CENTER);
    Player player;
    Thread upgrade;
    
	public void startUpgradeThread() {
		upgrade = new Thread(this);
		upgrade.start();
	}

	public UpgradeMenu(Player player) {
        this.player = player;
		setSize(400, 300);
        setLayout(new GridLayout(2, 4, 10, 10));
        
        
	

		ActionListener actionListener1 = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	strengthUpgradeCost.setText("Flap Strength Upgrade: $"+player.upgradeFlapStrength());
		    	playerCurrency.setText("Player Currency: $" + player.getCurrency());
		    }
		};
		
		ActionListener actionListener2 = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	limitUpgradeCost.setText("Flap Limit Upgrade: $"+player.upgradeFlapLimit());
		    	playerCurrency.setText("Player Currency: $" + player.getCurrency());
		    }
		};
		
		ActionListener actionListener3 = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	launchUpgradeCost.setText("Launch Upgrade: $"+player.upgradeLaunch());
		    	playerCurrency.setText("Player Currency: $" + player.getCurrency());
		    }
		};


		JButton increaseFlapStrength = new JButton("Increase Flap Strength");
		increaseFlapStrength.addActionListener(actionListener1);
		
        JButton increaseFlapLimit = new JButton("Increase Flap Limit");
        increaseFlapLimit.addActionListener(actionListener2);
        
        JButton increaseLaunch = new JButton("Increase Launch Strength");
        increaseLaunch.addActionListener(actionListener3);

        setBounds(25, 25, 300, 200);
        
        // Add label to display player info
        add(playerCurrency);
        add(strengthUpgradeCost);
        add(limitUpgradeCost);
        add(launchUpgradeCost);
        
        // Add more buttons for other upgrades...
        add(label);
        add(increaseFlapStrength);
        add(increaseFlapLimit);
        add(increaseLaunch);
        // Add other buttons...

        setVisible(true);

    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			playerCurrency.setText("Player Currency: $" + player.getCurrency());
			
			
			
		}
	}
}