package gameUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DualPlayUI {

	private JPanel dualPlayPanel;
	private JLabel lion, questionLabel, timeLabel, myDistanceLabel,enemyDistanceLabel;
	private JTextField answer;

	public DualPlayUI() {
		initialize();
	}

	private void initialize() {
		dualPlayPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResource("/res/dual_mode.png"));
					g.drawImage(img, 0, 0, 1280, 720, null);
				} catch (IOException e) {

				}
			}
		};
		dualPlayPanel.setBounds(0, 0, 1280, 720);
		dualPlayPanel.setLayout(null);
		
		timeLabel = new JLabel();
		timeLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		timeLabel.setText("Time: ");
		timeLabel.setBounds(44, 35, 80, 25);
		dualPlayPanel.add(timeLabel);

		enemyDistanceLabel = new JLabel();
		enemyDistanceLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		enemyDistanceLabel.setText("Enemy's Distance: ");
		enemyDistanceLabel.setBounds(44, 70, 300, 25);
		dualPlayPanel.add(enemyDistanceLabel);
		
		myDistanceLabel = new JLabel();
		myDistanceLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		myDistanceLabel.setText("My Distance: ");
		myDistanceLabel.setBounds(1050, 70, 300, 25);
		dualPlayPanel.add(myDistanceLabel);

		questionLabel = new JLabel();
		questionLabel.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 45));
		questionLabel.setText("q");
		questionLabel.setBounds(497, 178, 213, 57);
		dualPlayPanel.add(questionLabel);

		answer = new JTextField();
		answer.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 45));
		answer.setBounds(711, 168, 106, 75);
		dualPlayPanel.add(answer);

		ImageIcon lion_in_cage = new ImageIcon(getClass().getResource("/res/lion_in_cage.png"));
		lion = new JLabel(lion_in_cage);
		lion.setBounds(493, 375, 333, 264);
		dualPlayPanel.add(lion);
		
	}
	
	public JPanel getDualPlayModePanel() {
		return dualPlayPanel;
	}

}
