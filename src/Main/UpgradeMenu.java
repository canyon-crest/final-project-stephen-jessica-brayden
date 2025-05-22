package Main;

import entities.Player;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

public class UpgradeMenu extends JPanel {
    public UpgradeMenu(Player player) {
        setSize(400, 300);
        setLayout(new GridLayout(2, 3, 10, 10));
        
        JLabel playerCurrency = new JLabel("Player Currency: $" + player.getCurrency(), SwingConstants.CENTER);
        JLabel strengthUpgradeCost = new JLabel("Flap Strength Upgrade: $20", SwingConstants.CENTER);
        JLabel limitUpgradeCost = new JLabel("Flap Limit Upgrade: $30", SwingConstants.CENTER);
        
		JLabel label = new JLabel("Choose Upgrades:", SwingConstants.CENTER);

		ActionListener actionListener1 = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	player.upgradeFlapStrength();
		    	playerCurrency.setText("Player Currency: $" + player.getCurrency());
		    }
		};
		
		ActionListener actionListener2 = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	player.upgradeFlapLimit();
		    	playerCurrency.setText("Player Currency: $" + player.getCurrency());
		    }
		};

		JButton increaseFlapStrength = new JButton("Increase Flap Strength");
		increaseFlapStrength.addActionListener(actionListener1);
		
        JButton increaseFlapLimit = new JButton("Increase Flap Limit");
        increaseFlapLimit.addActionListener(actionListener2);

        setBounds(25, 25, 300, 200);
        
        // Add label to display player info
        add(playerCurrency);
        add(strengthUpgradeCost);
        add(limitUpgradeCost);
        
        // Add more buttons for other upgrades...
        add(label);
        add(increaseFlapStrength);
        add(increaseFlapLimit);
        // Add other buttons...

        setVisible(true);

    }
}