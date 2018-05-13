package gameUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Connection.GameServer;

public class ServerUI extends JFrame {
	private static JPanel panel;
	private static JLabel text;

	public ServerUI() {
		panel = new JPanel();
		setSize(new Dimension(200, 100));
		panel.setBounds(0, 0, 200, 100);
		text = new JLabel(BorderLayout.CENTER);
		text.setBounds(0, 0, 200, 100);
		try {
			GameServer server = new GameServer(55555);
			text.setText("Server Start");
		} catch (IOException e) {
			text.setText("Server Strat error");
			e.printStackTrace();
		}
		text.setVisible(true);
		panel.setVisible(true);
		panel.add(text);
		add(panel);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		ServerUI server = new ServerUI();
	}

}
