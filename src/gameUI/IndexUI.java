package gameUI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Connection.GameClient;
import Connection.SendData;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.SetChangeListener;

public class IndexUI{

	private JPanel panel;
	private JButton single, dual;
	private JTextField insertIP;

	private String ip;

	public IndexUI() {
		initialize();
	}

	private void initialize() {
		System.out.println("Run index...");
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
		single.setBounds(120, 270, 444, 111);
		single.addActionListener((e) -> {
			InsertNameUI goTo = new InsertNameUI();
			MainFrame.setPanel(goTo.getPanel());
		});
		panel.add(single);
		
		insertIP = new JTextField();
		insertIP.setBounds(820, 390, 343, 48);
		panel.add(insertIP);
		
		ImageIcon img2 = new ImageIcon(getClass().getResource("/res/player2_v4.png"));
		dual = new JButton(img2);
		dual.setOpaque(false);
		dual.setContentAreaFilled(false);
		dual.setBorderPainted(false);
		dual.setBounds(690, 270, 444, 111);
		panel.add(dual);
		dual.addActionListener((e) -> {
			WaitingUI goTo = new WaitingUI();
			ip = insertIP.getText().trim();
			try {
				if(!ip.isEmpty()) {
					System.out.println("IP: "+ip);
					GameClient client = new GameClient(ip, 54333, goTo);
					MainFrame.setPanel(goTo.getPanel());
				}else {
					System.out.println("insert IP");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		});
		
		

	}

	public JPanel getPanel() {
		return panel;
	}

}
