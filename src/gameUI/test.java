package gameUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Connection.DatabaseConnect;
import Connection.PlayerTable;
import game.Calculator;
import game.ObjectPool;
import game.Villager;

public class test extends JPanel implements Observer, Runnable {

	private JLabel question, timeLabel, time, distance, distanceLabel, witch, lion, endLabel;
	private JTextField textField;
	private JButton restartButton, homeButton;
	private ImageIcon w, lion_in_cage;
	private Renderer renderer;

	int num1 = 0;
	int num2 = 0;
	char op;
	int result, score = 0;
	int timeup = 0;
	private String message;
	private String name;

	private Calculator game;
	private Thread thread = new Thread(this);
	private ObjectPool objectPool;
	private PlayerTable p = new PlayerTable();

	public void initialize(PlayerTable player) {
		p.setName(player.getName());
		p.setScore(0);
		System.out.println("gameEnd(): " + p.getName() + ", " + p.getScore());
	}

	public test() {
		System.out.println("Run test...");
		game = new Calculator();
		objectPool = new ObjectPool();
		objectPool.addObserver(this);

		renderer = new Renderer();
		add(renderer);

		timeLabel = new JLabel();
		timeLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		timeLabel.setText("Time: ");
		timeLabel.setBounds(44, 35, 80, 25);
		add(timeLabel);

		time = new JLabel();
		time.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		time.setText("00 sec"); // ใส่เวลา
		time.setBounds(110, 35, 200, 25);
		add(time);

		distanceLabel = new JLabel();
		distanceLabel.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		distanceLabel.setText("Distance: ");
		distanceLabel.setBounds(44, 70, 300, 25);
		add(distanceLabel);

		distance = new JLabel();
		distance.setFont(new Font("Andale Mono", Font.PLAIN, 20));
		distance.setBounds(160, 70, 500, 25);
		add(distance);

		question = new JLabel();
		question.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 40));
		question.setText("question");
		question.setBounds(497, 178, 213, 57);
		add(question);

		textField = new JTextField();
		textField.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 43));
		textField.setBounds(710, 168, 105, 75);
		add(textField);

		w = new ImageIcon(getClass().getResource("/res/witch_r.gif"));
		witch = new JLabel(w);
		witch.setBounds(980, 250, 299, 212);
		witch.setVisible(false);
		add(witch);

		lion_in_cage = new ImageIcon(getClass().getResource("/res/push_lion_left.png"));
		lion = new JLabel(lion_in_cage);
		lion.setBounds(750, 375, 424, 253);
		add(lion);

		ImageIcon img = new ImageIcon(getClass().getResource("/res/save.png"));
		endLabel = new JLabel(img);
		endLabel.setBounds(400, 70, 517, 373);
		add(endLabel);
		endLabel.setVisible(false);

		ImageIcon b1 = new ImageIcon(getClass().getResource("/res/restart.png"));
		restartButton = new JButton(b1);
		restartButton.addActionListener((e) -> {
			test goTo = new test();
			MainFrame.setPanel(goTo);
		});
		restartButton.setBounds(440, 470, 204, 87);
		add(restartButton);
		restartButton.setVisible(false);

		ImageIcon b2 = new ImageIcon(getClass().getResource("/res/home.png"));
		homeButton = new JButton(b2);
		homeButton.addActionListener((e) -> {
			IndexUI goTo = new IndexUI();
			MainFrame.setPanel(goTo.getPanel());
		});
		homeButton.setBounds(680, 470, 204, 87);
		add(homeButton);
		homeButton.setVisible(false);

		play();

		setLayout(new BorderLayout());
		add(renderer);

	}

	public void play() {
		thread.start();
		game.setX(760); // set first lion's position ; panel center:493
		lion.setBounds(game.getX(), 375, 424, 253);
		renderer.setVisible(true);
		distance.setText(String.format("%d meter", game.getX() + 20));
		question();
		question.setText(getMessage());
		textField.addKeyListener(new Enter());
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	class Enter implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				int answer = 9999;
				try {
					String ans = textField.getText().trim();
					answer = Integer.parseInt(ans);
				} catch (NumberFormatException e1) {
					textField.setText("");
				}
				if (!game.check(answer, num1, num2, op)) {
					textField.setText("");
					game.setDx(-10);
					game.back();
					lion.setLocation(game.getX(), 375);
					distance.setText(String.format("%d meter", game.getX() + 20));
				} else { // correct answer
					objectPool.setStop(game.getX() - game.getDx());
					objectPool.burstVillagers(e.getKeyCode());
					if (score % 5 == 0 && score > 0) {
						witch.setVisible(true);
					}
					witch.setVisible(false);
					score++;
					textField.setText("");
					game.setDx(10); // เพิ่มขึ้นที่ละ x หน่วย
					game.push();
					lion.setLocation(game.getX(), 375);
					distance.setText(String.format("%d meter", game.getX() + 20));
				}
				if (game.isGameEnd()) {
					thread.stop();
					distance.setText("0 meter");
					gameEnd();
				} else {
					question();
					question.setText(getMessage());
				}
			}
		}

		private void gameEnd() {
			double time = timeup * 0.01; // เวลาทีทำได้
			System.out.printf("%.2f sec\n", time);
			textField.removeKeyListener(textField.getKeyListeners()[0]);
			textField.setVisible(false);
			question.setVisible(false);
			endLabel.setVisible(true);
			restartButton.setVisible(true);
			homeButton.setVisible(true);

			if (p.getName() != null) {
				System.out.println("guset");
				p.setScore(time);
				System.out.println("gameEnd(): " + p.getName() + ", " + p.getScore());
				DatabaseConnect.getInstance().update(p);
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

	}

	class Renderer extends JPanel {
		public Renderer() {
			setDoubleBuffered(true);
			setPreferredSize(new Dimension(objectPool.getWidth(), objectPool.getHeight()));
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			BufferedImage img1 = null;
			try {
				img1 = ImageIO.read(this.getClass().getResource("/res/single_mode.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(img1, 0, 0, 1280, 720, null);

			BufferedImage img = null;
			try {
				img = ImageIO.read(this.getClass().getResource("/res/push.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Draw space
			for (Villager villager : objectPool.getVillager()) {
				g.drawImage(img, 1200 + villager.getX(), 510 + villager.getY(), 111, 120, null);
			}
		}
	}

	public void question() {

		char operator[] = { '+', '-', '*', '/' };
		// TODO ค่อยแก้เลข
		num1 = (int) (1 + (Math.random() * 1));
		num2 = (int) (1 + (Math.random() * 10));
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public JPanel getPanel() {
		return this;
	}
}
