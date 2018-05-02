package gameUI;

import java.awt.Font;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.Calculator;

public class DualPlayUI implements Runnable{

	private JPanel panel;
	private JLabel lion, questionLabel, timeLabel, myDistanceLabel, opponentDistanceLabel, endLabel;
	private JTextField answerField;
	private JButton restartButton,homeButton;

	int num1 = 0, num2 = 0, result, answer = 999;
	char op;
	private String message;
	private Calculator game;
	private Thread thread = new Thread(this);
	double timedown = 5*100;

	public DualPlayUI() {
		initialize();
	}

	private void initialize() {
		game = new Calculator();
		panel = new JPanel() {
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
		panel.setBounds(0, 0, 1280, 720);
		panel.setLayout(null);

		timeLabel = new JLabel();
		timeLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		timeLabel.setText("Time: 00 sec");
		timeLabel.setBounds(44, 35, 300, 25);
		panel.add(timeLabel);

		opponentDistanceLabel = new JLabel();
		opponentDistanceLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		opponentDistanceLabel.setText("Opponent's Distance: ");
		opponentDistanceLabel.setBounds(44, 70, 300, 25);
		panel.add(opponentDistanceLabel);

		myDistanceLabel = new JLabel();
		myDistanceLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		myDistanceLabel.setText("My Distance: ");
		myDistanceLabel.setBounds(970, 70, 300, 25);
		panel.add(myDistanceLabel);

		questionLabel = new JLabel();
		questionLabel.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 43));
		questionLabel.setText("q");
		questionLabel.setBounds(497, 178, 213, 57);
		panel.add(questionLabel);

		answerField = new JTextField();
		answerField.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 43));
		answerField.setBounds(711, 168, 106, 75);
		panel.add(answerField);

		ImageIcon lion_in_cage = new ImageIcon(getClass().getResource("/res/push_lion.png"));
		lion = new JLabel(lion_in_cage);
		panel.add(lion);

		play();
	}

	public JPanel getDualPlayModePanel() {
		return panel;
	}

	public void question() {
		char operator[] = { '+', '-', '*', '/' };
		// TODO ค่อยแก้เลข
		num1 = (int) (1 + (Math.random() * 1));
		num2 = (int) (1 + (Math.random() * 1));
		int id = (int) (Math.random() * 4);
		op = operator[id];
		switch (op) {
		case '-':
			if (num2 > num1) {
				int temp = num1;
				num1 = num2;
				num2 = temp;
			}
			result = (int) (num1 - num2);
			break;
		case '/':
			if (num2 > num1) {
				int temp = num1;
				num1 = num2;
				num2 = temp;
			}
			if (num1 % num2 != 0) {
				num1 -= (num1 % num2);
			}
			result = (int) (num1 / num2);
			break;
		case '*':
			if (num2 > 10) {
				num2 = num2 % 10;
			}
			result = num1 * num2;
			break;
		}
		setMessage(num1 + " " + op + " " + num2 + " =");
		System.out.println(num1 + " " + op + " " + num2);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void play() {
		thread.start();
		game.setX(340); // set first lion's position ; panel center:493
		lion.setBounds(game.getX(), 375, 521, 253);
		myDistanceLabel.setText(String.format("My Distance: %d meter", game.getX() + 20));
		question();
		questionLabel.setText(getMessage());
		answerField.addKeyListener(new enter());
	}

	@Override
	public void run() {
		while (timedown >= 0.1) {
			try {
				thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timedown--;
			timeLabel.setText(String.format("Time: %.2f sec", timedown * 0.01));
		}
		gameEnd();
	}

	private void gameEnd() {
		double time = timedown * 0.01; // เวลาทีทำได้
		System.out.printf("%.2f sec\n", time);
		answerField.removeKeyListener(answerField.getKeyListeners()[0]);
		answerField.setVisible(false);
		questionLabel.setVisible(false);
		
		JPanel end = new JPanel();
		end.setOpaque(false);
		end.setBounds(320, 70, 695, 515);
		
		ImageIcon img = new ImageIcon(getClass().getResource("/res/save.png"));
		endLabel = new JLabel(img);
		end.add(endLabel);
		
		ImageIcon restart = new ImageIcon(getClass().getResource("/res/restart.png"));
		restartButton = new JButton(restart);
		restartButton.setOpaque(false);
		restartButton.setContentAreaFilled(false);
		restartButton.setBorderPainted(false);
		end.add(restartButton);
		
		ImageIcon home = new ImageIcon(getClass().getResource("/res/home.png"));
		homeButton = new JButton(home);
		homeButton.setOpaque(false);
		homeButton.setContentAreaFilled(false);
		homeButton.setBorderPainted(false);
		homeButton.addActionListener((e) -> {
			IndexUI ui = new IndexUI();
			MainFrame.setPanel(ui.getPanel());
		});
		end.add(homeButton);
		panel.add(end);
	}

	class enter implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				questionLabel.setText(getMessage());
				try {
					String ans = answerField.getText().trim();
					answer = Integer.parseInt(ans);
				} catch (NumberFormatException e1) {
					answerField.setText("");
				}
				if (!game.check(answer, num1, num2, op)) {
					System.out.println(answer + " ผิด");
					answerField.setText("");
				} else { // correct answer
					System.out.println(answer + " ถูก");
					answerField.setText("");
					game.setDx(10); // เพิ่มขึ้นที่ละ x หน่วย
					game.push();
					lion.setLocation(game.getX(), 375);
					myDistanceLabel.setText(String.format("My Distance: %d meter", game.getX() + 20));
				}
				if (game.isGameEnd()) {
					thread.stop();
					gameEnd();
				} else {
					question();
					questionLabel.setText(getMessage());
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

}
