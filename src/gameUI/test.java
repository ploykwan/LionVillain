package gameUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import Connection.DatabaseConnect;
import Connection.PlayerTable;
import game.Calculator;
import game.ObjectPool;
import game.Villager;

public class test extends JPanel implements Observer, Runnable {

	private JLabel question, timeLabel, time, distance, distanceLabel, witch, lion, endLabel, showScore, lose;
	private JTextField textField;
	private JButton restartButton, homeButton;
	private JTextArea textArea;
	private JScrollPane scroll;
	private ImageIcon w, lion_in_cage;
	private Renderer renderer;

	int num1 = 0;
	int num2 = 0;
	char op;
	int result, score = 0;
	int timeup = 0;
	int dist = 0;
	private long TIME_DELAY = 1000;
	private String message;
	private String name;
	private boolean guest = true;

	private Calculator game;
	private Thread thread = new Thread(this);
	private ObjectPool objectPool;
	private PlayerTable p = new PlayerTable();
	private Villager v = new Villager();

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
		time.setText("00.00 sec"); // ใส่เวลา
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
		endLabel.setBounds(400, 150, 517, 373);
		add(endLabel);
		endLabel.setVisible(false);

		ImageIcon youLose = new ImageIcon(getClass().getResource("/res/lose.png"));
		lose = new JLabel(youLose);
		lose.setBounds(400, 70, 517, 373);
		add(lose);
		lose.setVisible(false);

		ImageIcon b1 = new ImageIcon(getClass().getResource("/res/restart.png"));
		restartButton = new JButton(b1);
		restartButton.addActionListener((e) -> {
			test goTo = new test();
			MainFrame.setPanel(goTo);
		});
		add(restartButton);
		restartButton.setVisible(false);

		ImageIcon b2 = new ImageIcon(getClass().getResource("/res/home.png"));
		homeButton = new JButton(b2);
		homeButton.addActionListener((e) -> {
			IndexUI goTo = new IndexUI();
			MainFrame.setPanel(goTo.getPanel());
		});
		add(homeButton);
		homeButton.setVisible(false);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 15));
		textArea.setOpaque(false);
		scroll = new JScrollPane(textArea);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setOpaque(false);
		scroll.setBounds(515, 190, 300, 200);
		scroll.setVisible(false);
		add(scroll);

		ImageIcon score = new ImageIcon(getClass().getResource("/res/score_board.png"));
		showScore = new JLabel(score);
		showScore.setBounds(410, 0, 500, 700);
		showScore.setVisible(false);
		add(showScore);
		countdown();
		play();

		setLayout(new BorderLayout());
		add(renderer);
	}

	public void initializePlayer(PlayerTable player) {
		guest = false;
		p.setName(player.getName());
		p.setScore(0);
		System.out.println("gameEnd(): " + p.getName() + ", " + p.getScore());
	}

	public void play() {
		game.setX(780); // set first lion's position ; panel center:493
		lion.setBounds(game.getX(), 375, 424, 253);
		renderer.setVisible(true);
		distance.setText(String.format("%d meter", game.getX()));
		question();
		question.setText(getMessage());
		textField.addKeyListener(new Enter());
	}

	public void countdown() {
		question.setVisible(false);
		textField.setVisible(false);
		JLabel count = new JLabel();
		count.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 500));
		count.setBounds(500, 100, 500, 500);
		add(count);
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
										question.setVisible(true);
										textField.setVisible(true);
										remove(count);
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
					score--;
					textField.setText("");
					game.back();
					lion.setLocation(game.getX(), 375);
					objectPool.setStop(game.getX() + 20);
					distance.setText(String.format("%d meter", game.getX()));
				} else { // correct answer
					objectPool.setStop(game.getX() - game.getDx());
					objectPool.burstVillagers(e.getKeyCode());
					if (score % 5 == 0 && score > 0) {
						witch.setVisible(true);
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								witch.setVisible(false);
							}
						}, TIME_DELAY);
					}
					// score++;
					// textField.setText("");
					// game.setDx(10); // เพิ่มขึ้นที่ละ x หน่วย
					// System.out.println("dx: "+game.getDx());
					// System.out.println("push: "+game.getX());
					game.setDx(10); // เพิ่มขึ้นที่ละ x หน่วย
					score++;
					textField.setText("");
					game.push();
					// villagerPush();
					lion.setLocation(game.getX(), 375);
					distance.setText(String.format("%d meter", game.getX()));
				}
				if (game.getX() == 900) {
					thread.stop();
					lose.setVisible(true);
					gameEnd();
					showScoreBoard();
				}
				if (isGameEnd()) {
					thread.stop();
					distance.setText("0 meter");
					gameEnd();
					endLabel.setVisible(true);
					showScoreBoard();
				} else {
					question();
					question.setText(getMessage());
				}
			}
		}

		public boolean isGameEnd() {
<<<<<<< HEAD
		if (game.getX() <= -10 || game.getX() >= 900)
			return true;
		return false;
	}
=======
			if (game.getX() <= 0)
				return true;
			return false;
		}
>>>>>>> 6ebd817558b6823d509940c2a13ec67746ac28c4

		public void showScoreBoard() {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							timer.schedule(new TimerTask() {
								@Override
								public void run() {
									endLabel.setVisible(false);
									lose.setVisible(false);
									lion.setVisible(false);
									showScore.setVisible(true);
									scroll.setVisible(true);
									restartButton.setBounds(900, 380, 204, 87);
									homeButton.setBounds(900, 500, 204, 87);
									restartButton.setVisible(true);
									homeButton.setVisible(true);
								}
							}, TIME_DELAY);
						}
					}, TIME_DELAY);
				}
			}, TIME_DELAY);
		}

		private void gameEnd() {
			double time = timeup * 0.01; // เวลาทีทำได้
			System.out.printf("%.2f sec\n", time);
			textField.removeKeyListener(textField.getKeyListeners()[0]);
			textField.setVisible(false);
			question.setVisible(false);

			if (guest == false) {
				System.out.println("guset");
				p.setScore(time);
				System.out.println("gameEnd(): " + p.getName() + ", " + p.getScore());
				DatabaseConnect.getInstance().update(p);
				showScoreBoard();

				List<PlayerTable> playerList = new ArrayList<PlayerTable>(
						DatabaseConnect.getInstance().pullAllPlayerdata());
				Collections.sort(playerList);

				int i = 1;
				for (PlayerTable players : playerList) {
					if (players.getName().equalsIgnoreCase(p.getName())) {
						textArea.append(String.format("%d) %s \t\t%.02f\n", i, players.getName(), players.getScore()));
						System.out.print(">>>");
					}
					System.out.println("Name: " + players.getName() + " Score: " + players.getScore());
					textArea.setForeground(Color.blue);
					textArea.append(String.format("%d) %s \t\t%.02f\n", i, players.getName(), players.getScore()));
					i++;
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

		char operator[] = { '+', '-', 'x', '÷' };
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
//		System.out.println(num1 + " " + op + " " + num2);
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
