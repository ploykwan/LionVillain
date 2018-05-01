package gameUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Interface for single play mode.
 * 
 * @author Kwankaew Uttama, Pimwalan
 *
 */

import game.Calculator;

public class SinglePlayUI implements Runnable {

	private JPanel panel;
	private JLabel lion, distance, time, distanceLabel, timeLabel, endLabel;
	private JLabel question,witch;
	private JTextField textfield;

	int num1 = 0;
	int num2 = 0;
	char op;
	int result,score = 0;
	private String message;
	Calculator game;
	Thread thread = new Thread(this);
	double timeup = 0;

	public SinglePlayUI() {
		initialize();
	}

	@SuppressWarnings("serial")
	private void initialize() {
		game = new Calculator();
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("/res/single_mode.png"));
					g.drawImage(img, 0, 0, 1280, 720, null);
				} catch (IOException e) {

				}
			}
		};
		panel.setBounds(0, 0, 1280, 720);
		panel.setLayout(null);

		timeLabel = new JLabel();
		timeLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		timeLabel.setText("Time: ");
		timeLabel.setBounds(44, 35, 80, 25);
		panel.add(timeLabel);

		time = new JLabel();
		time.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		time.setText("00 sec"); // ใส่เวลา
		time.setBounds(110, 35, 200, 25);
		panel.add(time);

		distanceLabel = new JLabel();
		distanceLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		distanceLabel.setText("Distance: ");
		distanceLabel.setBounds(44, 70, 300, 25);
		panel.add(distanceLabel);

		distance = new JLabel();
		distance.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		distance.setBounds(160, 70, 500, 25);
		panel.add(distance);

		question = new JLabel();
		question.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 40));
		question.setText("question");
		question.setBounds(497, 178, 213, 57);
		panel.add(question);

		textfield = new JTextField();
		textfield.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 43));
		textfield.setBounds(710, 168, 105, 75);
		panel.add(textfield);
		
		ImageIcon w = new ImageIcon(getClass().getResource("/res/witch_r.gif"));
		witch = new JLabel(w);
		witch.setBounds(800, 360, 299, 212);
		witch.setVisible(false);
		panel.add(witch);

		ImageIcon lion_in_cage = new ImageIcon(getClass().getResource("/res/push_lion_left.png"));
		lion = new JLabel(lion_in_cage);
		panel.add(lion);

		play();
	}

	public JPanel getSinglePlayModePanel() {
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
		game.setX(750); // set first lion's position ; panel center:493
		lion.setBounds(game.getX(), 375, 424, 253);
		distance.setText(String.format("%d meter", game.getX() + 20));
		question();
		question.setText(getMessage());
		textfield.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					question.setText(getMessage());
					int answer = 9999;
					try {
						String ans = textfield.getText().trim();
						answer = Integer.parseInt(ans);
					} catch (NumberFormatException e1) {
						textfield.setText("");
					}
					if (!game.check(answer, num1, num2, op)) {
						System.out.println(answer + " ผิด");
						textfield.setText("");
					} else { // correct answer
						if( score % 5 == 0 && score > 0) {
							witch.setVisible(true);
						}
						score++;
						System.out.println(answer + " ถูก");
						textfield.setText("");
						game.setDx(10); // เพิ่มขึ้นที่ละ x หน่วย
						game.push();
						lion.setBounds(game.getX(), 375, 424, 253);
						distance.setText(String.format("%d meter", game.getX() + 20));
					}
					if (game.isGameEnd()) {
						thread.stop();
						gameEnd();
					} else {
						question();
						question.setText(getMessage());
					}
				}

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}

		});
	}

	@Override
	public void run() {
		while (true) {
			try {
				thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeup++;
			time.setText(String.format("%.2f sec", timeup * 0.01));
		}
	}

	private void gameEnd() {
		double time = timeup * 0.01; // เวลาทีทำได้
		System.out.printf("%.2f sec\n", time);
		textfield.removeKeyListener(textfield.getKeyListeners()[0]);
		textfield.setVisible(false);
		question.setVisible(false);
		JPanel end = new JPanel();
		end.setOpaque(false);
		end.setBounds(320, 70, 675, 495);
		ImageIcon img = new ImageIcon(getClass().getResource("/res/save.png"));
		endLabel = new JLabel(img);
		end.add(endLabel);
		panel.add(end);
	}

}
