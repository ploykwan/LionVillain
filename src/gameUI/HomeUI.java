package gameUI;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class HomeUI extends JPanel {
	private JPanel homePanel;

	private void initialize() {
		System.out.println("hey");
		homePanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Image img = new ImageIcon("src/res/Home.png").getImage();
				g.drawImage(img, 0, 0, 1280, 720, null);
			}
		};
		homePanel.setPreferredSize(new Dimension(1280, 720));
		homePanel.setLayout(null);

		JButton playerButton1 = new JButton();
		playerButton1.setIcon(new ImageIcon("src/res/1player.png"));
		playerButton1.setBounds(100, 100, 231, 97);
		homePanel.add(playerButton1);

		JButton playerButton2 = new JButton();
		playerButton2.setIcon(new ImageIcon("src/res/2player.png"));
		playerButton2.setBounds(400, 100, 231, 97);
		homePanel.add(playerButton2);

	}

	 public JPanel getHomeUI() {
	 return this.homePanel;
	 }

	public static void main(String[] args) {
		HomeUI home = new HomeUI();

		JFrame frame = new JFrame();

		frame.add(home);

		frame.setSize(1280, 720);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}

}
