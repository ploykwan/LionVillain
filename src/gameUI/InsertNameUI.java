package gameUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import Connection.DatabaseConnect;
import Connection.PlayerTable;

public class InsertNameUI {

	private DatabaseConnect database;
	
	private JPanel panel;
	private JButton back, start, skip;
	private JTextField name;
	private JLabel lbLabel = new JLabel("Limit 10");
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
		name.setDocument(new JTextFieldLimit(10));
		name.setHorizontalAlignment( SwingConstants.CENTER );
		name.setText(lbLabel.getText());
		panel.add(name);
		name.add(lbLabel);
		name.addMouseListener(new MouseListener() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				name.setText("");
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

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
			PlayerTable p = new PlayerTable(player,0);
			database.createUser(p);
			test goTo = new test();
			goTo.initializePlayer(p);
			MainFrame.setPanel(goTo);
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
			test goTo = new test();
			MainFrame.setPanel(goTo.getPanel());
		}); 
		panel.add(skip);

	}

	public JPanel getPanel() {
		return panel;
	}
	
	class JTextFieldLimit extends PlainDocument {
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet set) throws BadLocationException {
			if (str == null)
				return;
			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, set);
			}
		}
	}
}
