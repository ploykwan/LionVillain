package gameUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Connection.DatabaseConnect;
import Connection.PlayerTable;

public class InsertNameUI {

	private DatabaseConnect database;
	
	private JPanel panel;
	private JButton back, start, skip;
	private JTextField name;
	
	private String player;

	public InsertNameUI() {
		initialize();
	}

	private void initialize() {
		database = DatabaseConnect.getInstance();
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResource("/res/regis.png"));
					g.drawImage(img, 0, 0, 1280, 720, null);
				} catch (IOException e) {

				}
			}
		};
		panel.setBounds(0, 0, 1280, 720);
		panel.setLayout(null);

		name = new JTextField();
		name.setBounds(488, 237, 343, 48);
		panel.add(name);

		ImageIcon img = new ImageIcon(getClass().getResource("/res/back_v2.png"));
		back = new JButton(img);
		back.setOpaque(false);
		back.setContentAreaFilled(false);
		back.setBorderPainted(false);
		back.setBounds(675, 307, 150, 58);
		back.addActionListener((e) -> {
			IndexUI ui = new IndexUI();
			MainFrame.setPanel(ui.getPanel());
		});
		panel.add(back);

		ImageIcon img2 = new ImageIcon(getClass().getResource("/res/start_v2.png"));
		start = new JButton(img2);
		start.setOpaque(false);
		start.setContentAreaFilled(false);
		start.setBorderPainted(false);
		start.setBounds(495, 307, 138, 58);
		start.addActionListener((e) -> {
			player = name.getText().trim();
			PlayerTable p = new PlayerTable();
			p.setName(player);
			database.createUser(p);
			SinglePlayUI ui = new SinglePlayUI();
			MainFrame.setPanel(ui.getSinglePlayModePanel());
		});
		panel.add(start);

		skip = new JButton("Skip >>");
		Font buttonFont = new Font(skip.getFont().getName(), Font.ITALIC + Font.BOLD, skip.getFont().getSize());
		skip.setFont(buttonFont);
		skip.setBounds(763, 382, 100, 13);
		skip.setOpaque(false);
		skip.setContentAreaFilled(false);
		skip.setBorderPainted(false);
		skip.addActionListener((e) -> {
			player = "Guest";
			DualPlayUI ui = new DualPlayUI();
			MainFrame.setPanel(ui.getDualPlayModePanel());
		}); 
		panel.add(skip);

	}

	public JPanel getPanel() {
		return panel;
	}
}
