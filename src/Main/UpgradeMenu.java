package Main;

import entities.Player;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Dimension;

public class UpgradeMenu extends JPanel {
    public UpgradeMenu(Player player) {
        setSize(400, 300);

		JLabel label = new JLabel("Choose Upgrades:", SwingConstants.LEFT);
		add(label);

        JButton increaseFlapStrength = new JButton("Increase Flap Strength");
        increaseFlapStrength.addActionListener(e -> player.upgradeFlapStrength());

        JButton increaseFlapLimit = new JButton("Increase Flap Limit");
        increaseFlapLimit.addActionListener(e -> player.upgradeFlapLimit());

        // Add more buttons for other upgrades...

        add(increaseFlapStrength);
        add(increaseFlapLimit);
        // Add other buttons...

        setVisible(true);

    }
}