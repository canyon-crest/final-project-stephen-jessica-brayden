package Main;

import entities.Player;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Dimension;

public class UpgradeMenu extends JFrame {
    public UpgradeMenu(Player player) {
        JFrame frame = new JFrame();
    	frame.setTitle("Upgrade Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // make buttons and add them to frame
        JButton increaseFlapStrength = new JButton("Increase Flap Strength");
        increaseFlapStrength.addActionListener(e -> player.upgradeFlapStrength());
        increaseFlapStrength.setSize(400, 400);
        increaseFlapStrength.setVisible(true);

        JButton increaseFlapLimit = new JButton("Increase Flap Limit");
        increaseFlapLimit.addActionListener(e -> player.upgradeFlapLimit());
        increaseFlapLimit.setSize(400, 400);
        increaseFlapLimit.setVisible(true);
        
        frame.add(increaseFlapStrength);
        frame.add(increaseFlapLimit);
        // Add more buttons for other upgrades...

        // Add other buttons...

        frame.setVisible(true);
    }
}