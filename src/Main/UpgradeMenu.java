package Main;

import entities.Player;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpgradeMenu extends JFrame {
    public UpgradeMenu(Player player) {
        setTitle("Upgrade Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

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