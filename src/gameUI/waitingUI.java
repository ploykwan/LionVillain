package gameUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Connection.GameClient;

public class WaitingUI implements Observer {

	private JPanel panel;

	public WaitingUI() {
		initialize();
	}

	private void initialize() {
		System.out.println("Run waiting...");
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResource("/res/waiting.png"));
					g.drawImage(img, 0, 0, 1280, 720, null);
				} catch (IOException e) {

				}
			}
		};
		panel.setBounds(0, 0, 1280, 720);
		panel.setLayout(null);
		
		ImageIcon img = new ImageIcon(getClass().getResource("/res/back_v2.png"));
		JButton home = new JButton(img);
		home.setBounds(810,350,136,58);
		home.addActionListener((e) ->{
			IndexUI goTo = new IndexUI();
			MainFrame.setPanel(goTo.getPanel());
		}); 
		panel.add(home);
		
	}

	public JPanel getPanel() {
		return panel;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof OnlineGame) {
			System.out.println("OnlineGameInit");
			OnlineGame ui = (OnlineGame) arg;
			MainFrame.setPanel(ui.getDualPlayModePanel());
		}
	}

}
