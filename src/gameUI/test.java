package gameUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import game.Calculator;
import game.ObjectPool;
import game.Villager;

public class test extends JFrame implements Observer {

	int num1 = 0;
	int num2 = 0;
	char op;
	int result, score = 0;
	private String message;
	private JLabel question;
	private Calculator game;

	private ObjectPool objectPool;
	private Renderer renderer;

	public test() {
		game = new Calculator();
		objectPool = new ObjectPool();
		objectPool.addObserver(this);

		renderer = new Renderer();
		add(renderer);

		question = new JLabel();
		question.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 40));
		question.setText("question");
		question.setBounds(497, 178, 213, 57);
		add(question);

		JTextField textfield = new JTextField();
		textfield.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 43));
		textfield.setBounds(710, 168, 105, 75);
		add(textfield);
		question();
		question.setText(getMessage());
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
						objectPool.burstVillagers(e.getKeyCode());
						score++;
						System.out.println(answer + " ถูก");
						textfield.setText("");
						game.setDx(-10); // เพิ่มขึ้นที่ละ x หน่วย
						game.push();
						System.out.println(game.getX());
					}
					System.out.println("------------");
					if (game.isGameEnd()) {
						System.out.println("***************");
//						thread.stop();
//						thread2.stop();
//						gameEnd();
					}else {
						System.out.println("new question");
						question();
						question.setText(getMessage());
					}
				}
			}
		});

		setLayout(new BorderLayout());
		add(renderer);

		setResizable(true);
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
			
			BufferedImage img1 = null;
			try {
				img1 = ImageIO.read(this.getClass().getResource("/res/single_mode.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(img1, 0, 0,1280,720, null);

			BufferedImage img = null;
			try {
				img = ImageIO.read(this.getClass().getResource("/res/push.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Draw space
			for (Villager villager : objectPool.getVillager()) {
				g.drawImage(img, 1200 + villager.getX(), 375 + villager.getY(), 111, 120, null);
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
}
