package gameUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import game.Calculator;
import game.ObjectPool;
import game.Villager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class test extends JFrame implements Observer {
	
	int num1 = 0;
	int num2 = 0;
	char op;
	int result,score = 0;
	private String message;
	private JLabel question;
	private Calculator game;

	private ObjectPool objectPool;
	private Renderer renderer;
	private JPanel panel;

	public test() {
		game = new Calculator();
		objectPool = new ObjectPool();
		objectPool.addObserver(this);
		
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
		panel.setBounds(0,0,1280,720);

		renderer = new Renderer();
		
		question = new JLabel();
		question.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 40));
		question.setText("question");
		question.setBounds(497, 178, 213, 57);
		renderer.add(question);
		
		JTextField textfield = new JTextField();
		textfield.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 43));
		textfield.setBounds(710, 168, 105, 75);
		renderer.add(textfield);
		textfield.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

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
					System.out.println(e.getKeyCode());
					try {
						objectPool.burstVillagers(e.getKeyCode());

					} catch (Exception e2) {
						e2.getMessage();
					}
					score++;
					System.out.println(answer + " ถูก");
					textfield.setText("");
					game.setDx(10); // เพิ่มขึ้นที่ละ x หน่วย
					game.push();
				//	lion.setLocation(game.getX(), 375);
				 //distance.setText(String.format("%d meter", game.getX() + 20));
				}
				if (game.isGameEnd()) {
//					thread2.stop();
				//	gameEnd();
				} else {
					question();
					question.setText(getMessage());
				}
			}
				}
		});


		setLayout(new BorderLayout());
		add(renderer);

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
	}

	public void start() {
		setVisible(true);
	}

	public static void main(String[] args) {
		System.out.println("start");
		test test = new test();
		test.start();
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	class Renderer extends JPanel {
		public Renderer() {
			setDoubleBuffered(true);
			setPreferredSize(new Dimension(objectPool.getWidth(), objectPool.getHeight()));
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			BufferedImage img = null;
			try {
				img = ImageIO.read(this.getClass().getResource("/res/push.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Draw space
			for (Villager villager : objectPool.getVillager()) {
				// g.fillOval(bullet.getX(), bullet.getY(), 100, 100);
				// System.out.println(villager.getX() + " " + villager.getY());
				g.drawImage(img, 1200 + villager.getX(), 375 + villager.getY(), 111, 120, null);
			}
		}
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
	
	// @Override
	// public void start(Stage primaryStage) {
	// Parent root;
	// try {
	// root = FXMLLoader.load(getClass().getResource("/gameUI/indexUI.fxml"));
	// Scene scene = new Scene(root);
	// primaryStage.setScene(scene);
	// primaryStage.setTitle("test");
	// primaryStage.show();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public static void main(String[] args) {
	// launch(args);
	// }
}
