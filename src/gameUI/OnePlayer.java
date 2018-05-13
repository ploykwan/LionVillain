package gameUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import Connection.DatabaseConnect;
import Connection.PlayerTable;
import game.Calculator;
import game.ObjectPool;
import game.Villager;

/**
 * OnePlyer UI for 1 player mode.
 * 
 * @author Pimwalun Witchawanitchanun
 *
 */
public class OnePlayer extends JPanel implements Observer, Runnable {

	private DatabaseConnect database;
	private JLabel question, timeLabel, time, distance, distanceLabel, witch, lion, endLabel, showScore, lose;
	private JTextField textField;
	private JButton restartButton, homeButton;
	private JScrollPane scroll;
	private ImageIcon w, lion_in_cage;
	private Renderer renderer;
	private JTable table;

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
	private boolean powerup = false;

	private Calculator game;
	private Thread thread;
	private final AtomicBoolean running = new AtomicBoolean(true);
	private ObjectPool objectPool;
	private PlayerTable player = new PlayerTable();
	private Villager v = new Villager();

	/**
	 * Create the application.
	 */
	public OnePlayer() {
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
		endLabel.setBounds(400, 70, 517, 373);
		add(endLabel);
		endLabel.setVisible(false);

		ImageIcon youLose = new ImageIcon(getClass().getResource("/res/lose.png"));
		lose = new JLabel(youLose);
		lose.setBounds(400, 70, 517, 373);
		add(lose);
		lose.setVisible(false);

		ImageIcon b1 = new ImageIcon(getClass().getResource("/res/restart.png"));
		restartButton = new JButton(b1);
		restartButton.setBounds(440, 470, 204, 87);
		restartButton.addActionListener((e) -> {
			OnePlayer goTo = new OnePlayer();
			if(guest == false) {
				goTo.initializePlayer(player);
			}
			MainFrame.setPanel(goTo);
		});
		add(restartButton);
		restartButton.setVisible(false);

		ImageIcon b2 = new ImageIcon(getClass().getResource("/res/home.png"));
		homeButton = new JButton(b2);
		homeButton.setBounds(680, 470, 204, 87);
		homeButton.addActionListener((e) -> {
			IndexUI goTo = new IndexUI();
			MainFrame.setPanel(goTo.getPanel());
		});
		add(homeButton);
		homeButton.setVisible(false);

		table = new JTable();
		table.setRowHeight(25);
		table.setFont(new Font("Arial Rounded Bold", Font.BOLD, 16));
		table.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 12));
		table.setForeground(Color.white);
		table.setOpaque(true);
		table.setBorder(BorderFactory.createLineBorder(new Color(148, 91, 39)));
		table.setSelectionForeground(new Color(148, 91, 39));
		// table.setSelectionBackground(new Color(247, 219, 0));
		scroll = new JScrollPane(table);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getViewport().setOpaque(false);
		scroll.setOpaque(false);
		scroll.setBounds(500, 190, 320, 200);
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

	/**
	 * Initialize from PlayerTable to show the score board.
	 * 
	 * @param player
	 *            is info from database.
	 */
	public void initializePlayer(PlayerTable newPlayer) {
		guest = false;
		player.setName(newPlayer.getName());
		player.setScore(0);
		System.out.println("gameEnd(): " + player.getName() + ", " + player.getScore());
	}

	/**
	 * Playing the game.
	 */
	public void play() {
		game.setX(780); // set first lion's position ; panel center:493
		lion.setBounds(game.getX(), 375, 424, 253);
		renderer.setVisible(true);
		distance.setText(String.format("%d meter", game.getX()));
		question();
		question.setText(getMessage());
		textField.addKeyListener(new Enter());
	}

	/**
	 * Count down before the game starts.
	 */
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
										start();
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

	/**
	 * Input the answer and check that correct or not.
	 * 
	 * @author pimwalun
	 *
	 */
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
					if (score % 3 == 0 && score > 0) {
						witch.setVisible(true);
						objectPool.burstVillagers(1);
						game.setDx(5);
						Timer timer = new Timer();
						powerup = true;
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								witch.setVisible(false);
							}
						}, TIME_DELAY);
					}
					game.setDx(10);
					score++;
					textField.setText("");
					game.push();
					lion.setLocation(game.getX(), 375);
					distance.setText(String.format("%d meter", game.getX()));
				}
				if (game.getX() >= 890) {
					stop();
					lose.setVisible(true);
					gameEnd();
				}
				if (isGameEnd()) {
					stop();
					distance.setText("0 meter");
					endLabel.setVisible(true);
					gameEnd();
				} else {
					question();
					question.setText(getMessage());
				}
			}
		}

		public boolean isGameEnd() {
			if (game.getX() <= -10 || game.getX() >= 900)
				return true;
			return false;
		}

		/**
		 * Waiting before show score board.
		 */
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

		/**
		 * If player select start button the panel it show scoreboard. If player select
		 * skip button the panel it doesn't show scoreboard.
		 */
		private void gameEnd() {
			double time = timeup * 0.01; // เวลาทีทำได้
			System.out.printf("%.2f sec\n", time);
			textField.removeKeyListener(textField.getKeyListeners()[0]);
			textField.setVisible(false);
			question.setVisible(false);

			if (guest == false) {
				showScoreBoard();
				System.out.println("guset");
				player.setScore(time);
				System.out.println("gameEnd(): " + player.getName() + ", " + player.getScore());
				DatabaseConnect.getInstance().update(player);
				showScoreBoard();

				List<PlayerTable> playerList = new ArrayList<PlayerTable>(
						DatabaseConnect.getInstance().pullAllPlayerdata());
				Collections.sort(playerList);
				String columnNames[] = { "No", "Name", "Time" };
				String[][] data = new String[playerList.size()][3];

				for (int i = 0; i < playerList.size(); i++) {
					data[i][0] = (i + 1) + "";
					data[i][1] = playerList.get(i).getName();
					data[i][2] = playerList.get(i).getScore() + "";
					if (playerList.get(i).getName().equalsIgnoreCase(player.getName())) {
					}
				}

				TableModel model = new DefaultTableModel(data, columnNames) {
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};

				JTableHeader header = table.getTableHeader();
				header.setBackground(new Color(148, 91, 39));
				header.setForeground(Color.white);
				header.setReorderingAllowed(false);
				header.setPreferredSize(new Dimension(100, 30));
				table.setModel(model);
				TableColumnModel columnModel = table.getColumnModel();
				columnModel.getColumn(0).setPreferredWidth(50);
				columnModel.getColumn(1).setPreferredWidth(200);
				columnModel.getColumn(2).setPreferredWidth(100);
				getNewRenderedTable(table);
			} else if (guest == true) {
				restartButton.setVisible(true);
				homeButton.setVisible(true);
			}
		}

		/**
		 * Set color of the current user playing.
		 * 
		 * @param table
		 *            is scoreboard.
		 * @return every value of scoreboard.
		 */
		private JTable getNewRenderedTable(JTable table) {
			table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int col) {
					super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
					String status = (String) table.getModel().getValueAt(row, 1);
					if ((player.getName()).equals(status)) {
						setBackground(Color.white);
						setForeground(Color.black);
					} else {
						setBackground(new Color(148, 91, 39));
						setForeground(Color.white);
					}
					return this;
				}
			});
			return table;
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

	}

	/**
	 * Paint background and villager in the panel.
	 * 
	 * @author pimwalun
	 *
	 */
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

	/**
	 * Random question to the player.
	 */
	public void question() {
		char operator[] = { '+', '-', 'x', '÷' };
		// TODO ค่อยแก้เลข
		num1 = (int) (1 + (Math.random() * 10));
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
		// System.out.println(num1 + " " + op + " " + num2);
	}

	/**
	 * Set a message about the game.
	 * 
	 * @param message
	 *            a string about the question in the game.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Return a message about the question in the game.
	 * 
	 * @return string message related to the recent question.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Start timer.
	 */
	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stop timer.
	 */
	public void stop() {
		running.set(false);
	}

	/**
	 * Time of user to play in 1 game.
	 */
	@Override
	public void run() {
		while (running.get()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			timeup++;
			time.setText(String.format("%.2f sec", timeup * 0.01));
		}

	}

	/**
	 * Return panel of OnePlayer.
	 * 
	 * @return panel of OnePlayer.
	 */
	public JPanel getPanel() {
		return this;
	}
}
