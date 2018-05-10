package gameUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Connection.GameClient;

public class WaitingUI {

	private GameClient gameClient;
	
	private JPanel panel;

	public WaitingUI() {
		initialize();
	}

	private void initialize() {
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
		
		try {
			gameClient = new GameClient();
			gameClient.setStatus("Connecting");
			gameClient.sendMessage();
//			OnlineGame ui = new OnlineGame();
//			gameClient.addObserver(ui);
//			MainFrame.setPanel(ui.getDualPlayModePanel());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public JPanel getPanel() {
		return panel;
	}

}
