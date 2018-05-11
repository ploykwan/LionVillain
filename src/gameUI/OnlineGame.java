package gameUI;

import java.awt.Font;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Connection.GameClient;
import Connection.SendData;
import game.Calculator;
import game.Villager;

public class OnlineGame implements Runnable, Observer, KeyListener {

	private GameClient gameClient;

	private JPanel panel;
	private JLabel lion, questionLabel, timeLabel, v1DistanceLabel, v2DistanceLabel, endLabel;

	private JPanel end = new JPanel();;
	private JTextField answerField;
	private JButton homeButton;
	private ImageIcon winImg, loseImg, drawImg;

	private long TIME_DELAY = 1000;
	private int num1 = 0, num2 = 0, result, answer = 999;
	private char op;
	private String message;
	private Calculator game;
	private Thread thread = new Thread(this);
	double timedown = 30 * 100;

	private Villager v1 = new Villager();
	private Villager v2 = new Villager();

	private GameClient player;

	public OnlineGame(GameClient p) {
		gameClient = p;
		initialize();
		play();
	}

	public void initialize() {
		game = new Calculator(v1, v2);
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

		v2DistanceLabel = new JLabel();
		v2DistanceLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		v2DistanceLabel.setText("Opponent's Distance: ");
		v2DistanceLabel.setBounds(44, 70, 300, 25);
		panel.add(v2DistanceLabel);

		v1DistanceLabel = new JLabel();
		v1DistanceLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		v1DistanceLabel.setText("My Distance: ");
		v1DistanceLabel.setBounds(970, 70, 300, 25);
		panel.add(v1DistanceLabel);

		questionLabel = new JLabel();
		questionLabel.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 43));
		questionLabel.setBounds(497, 178, 213, 57);
		panel.add(questionLabel);

		answerField = new JTextField();
		answerField.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 43));
		answerField.setBounds(711, 168, 106, 75);
		answerField.addKeyListener(this);
		panel.add(answerField);

		ImageIcon lion_in_cage = new ImageIcon(getClass().getResource("/res/push_lion.png"));
		lion = new JLabel(lion_in_cage);
		panel.add(lion);

		winImg = new ImageIcon(getClass().getResource("/res/win.png"));
		loseImg = new ImageIcon(getClass().getResource("/res/lose.png"));
		drawImg = new ImageIcon(getClass().getResource("/res/draw.png"));
		endLabel = new JLabel(winImg);

		// play();
	}

	public JPanel getDualPlayModePanel() {
		return panel;
	}

	public void question() {
		char operator[] = { '+', '-', 'x', '÷' };
		num1 = (int) (1 + (Math.random() * 1));
		num2 = (int) (1 + (Math.random() * 9));
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
		case '÷':
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
		case 'x':
			if (num2 > 10) {
				num2 = num2 % 10;
			}
			result = num1 * num2;
			break;
		}
		setMessage(num1 + " " + op + " " + num2 + " =");
		// System.out.println(num1 + " " + op + " " + num2);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	private void gameStart() {
		questionLabel.setVisible(false);
		answerField.setVisible(false);
		JLabel count = new JLabel();
		count.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 500));
		count.setBounds(500, 100, 500, 500);
		panel.add(count);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				count.setText("3");
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						count.setText("2");
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								count.setText("1");
								timer.schedule(new TimerTask() {
									@Override
									public void run() {
										count.setVisible(false);
										questionLabel.setVisible(true);
										answerField.setVisible(true);
										panel.remove(count);
										thread.start();
									}
								}, TIME_DELAY);
							}
						}, TIME_DELAY);
					}
				}, TIME_DELAY);
			}
		}, TIME_DELAY);
	}

	public void play() {
		gameStart();
		thread.start();
		game.V1setDx(30);
		game.V2setDx(30);
		game.V1setX(340);
		game.V2setX(340); // set first lion's position ; panel center:493
		lion.setBounds(game.V2getX(), 375, 521, 253);
		v1DistanceLabel.setText(String.format("V1 Distance: %d meter", game.V2getX() + 20));
		v2DistanceLabel.setText(String.format("V2 Distance: %d meter", game.V1getX() + 20));
		question();
		questionLabel.setText(getMessage());
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
		gameEnd("timeup");
	}

	private void gameEnd(String why) {
		double time = timedown * 0.01; // เวลาทีทำได้
		System.out.printf("%.2f sec\n", time);
		answerField.removeKeyListener(this);
		answerField.setVisible(false);
		questionLabel.setVisible(false);
		lion.setVisible(false);
		panel.remove(questionLabel);
		panel.remove(answerField);
		panel.remove(lion);

		end.setOpaque(false);
		end.setBounds(320, 90, 695, 485);

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
		
		if (why.equals("timeup")) {
			System.out.println("TIME UP enter");
			if (game.V2getX() - game.V1getX() > 0) {
				System.out.println("p1win");
				gameClient.setStatus("p1Win");
			} else if (game.V2getX() == game.V1getX()) {
				System.out.println("draw");
				gameClient.setStatus("draw");
			} else {
				System.out.println("p2win");
				gameClient.setStatus("p2Win");
			}
			gameClient.sendMessage();
		}
	}

	private boolean isGameEnd() {
		if (game.V2getX() <= -20 || game.V1getX() <= -20) {
			return true;
		}
		return false;
	}

	private void v2push() {
		answerField.setText("");
		System.out.print("V2 push ");
		game.V1reverse();
		lion.setLocation(game.V1getX(), 375);
	}

	private void v1push() {
		answerField.setText("");
		System.out.print("V1 push ");
		game.V1push();
		lion.setLocation(game.V1getX(), 375);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			questionLabel.setText(getMessage());
			try {
				String ans = answerField.getText().trim();
				answer = Integer.parseInt(ans);
			} catch (NumberFormatException e1) {
				answerField.setText("");
			}
			if (game.check(answer, num1, num2, op)) {
				gameClient.setStatus("Correct");
				gameClient.sendMessage();
			} else {
				answerField.setText("");
			}
			question();
			questionLabel.setText(getMessage());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		v1DistanceLabel.setText(String.format("V1 Distance: %d meter", game.V2getX() + 20));
		v2DistanceLabel.setText(String.format("V2 Distance: %d meter", game.V1getX() + 20));
		if (arg instanceof SendData) {
			SendData data = (SendData) arg;
			if (data.status.equals("win")) {
				System.out.println("win >" + data.playerName);
				endLabel = new JLabel(winImg);
				end.add(endLabel);
			} else if (data.status.equals("lose")) {
				System.out.println("lose >" + data.playerName);
				endLabel = new JLabel(loseImg);
				end.add(endLabel);
			} else if (data.status.equals("draw")) {
				System.out.println("draw >" + data.playerName);
				endLabel = new JLabel(drawImg);
				end.add(endLabel);
			} else if (data.playerName.equals("p1")) {
				v1push();
				answer = 9999;
			} else if (data.playerName.equals("p2")) {
				v2push();
				answer = 9999;
			}
			if (isGameEnd() && !data.status.equals("win") && !data.status.equals("lose")) {
				gameClient.setStatus("End");
				gameClient.sendMessage();
				thread.stop();
				gameEnd("win");
				System.out.println("-------------------------");
			}

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
