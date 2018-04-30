package gameUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class IndexUI {

	private JPanel panel;
	private JButton single,dual;
	
	public IndexUI() {
		initialize();
	}
	
	private void initialize() {
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResource("/res/index.png"));
					g.drawImage(img, 0, 0, 1280, 720, null);
				} catch (IOException e) {

				}
			}
		};
		panel.setBounds(0, 0, 1280, 720);
		panel.setLayout(null);
		
		ImageIcon img = new ImageIcon(getClass().getResource("/res/player1_v4.png"));
		single = new JButton(img);
		single.setOpaque(false);
		single.setContentAreaFilled(false);
		single.setBorderPainted(false);
		single.setBounds(120,270,444,111);
		panel.add(single);
		
		ImageIcon img2 = new ImageIcon(getClass().getResource("/res/player2_v4.png"));
		dual = new JButton(img2);
		dual.setOpaque(false);
		dual.setContentAreaFilled(false);
		dual.setBorderPainted(false);
		dual.setBounds(690,270,444,111);
		panel.add(dual);
		
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
