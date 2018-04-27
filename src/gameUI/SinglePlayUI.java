package gameUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Interface for single play mode.
 * 
 * @author Kwankaew Uttama
 *
 */

import game.Calculator;
import javafx.event.ActionEvent;

public class SinglePlayUI implements Runnable {

	// private StopWatch stopWatch;

	private JPanel panel;
	private JLabel lion, distance, time, distanceLabel, timeLabel;
	private JLabel question;
	private JTextField textfield;

	int num1 = 0;
	int num2 = 0;
	char op;
	int result;
	Calculator game;
	Thread thread = new Thread(this);
	int timeup = 0;

	public SinglePlayUI() {
		initialize();
	}

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
		distance.setText("00 meter"); // Remaining distance
		distance.setBounds(160, 70, 500, 25);
		panel.add(distance);

		question = new JLabel();
		question.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 43));
		question.setText("question");
		question.setBounds(497, 178, 213, 57);
		panel.add(question);

		textfield = new JTextField();
		textfield.setFont(new Font("Arial Rounded Bold", Font.PLAIN, 43));
		textfield.setBounds(711, 168, 120, 75);
		textfield.addKeyListener(new SubmitAnswer());
		panel.add(textfield);

		ImageIcon lion_in_cage = new ImageIcon(getClass().getResource("/res/lion_in_cage.png"));
		lion = new JLabel(lion_in_cage);
		lion.setBounds(493, 375, 333, 264);
		panel.add(lion);
	}

	public JPanel getSinglePlayModePanel() {
		return panel;
	}

	private String message;

	public void question() {

		char operator[] = { '+', '-', '*', '/' };
		num1 = (int) (1 + (Math.random() * 99));
		num2 = (int) (1 + (Math.random() * 99));
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
		/**
		 * !!!init first game
		 */
		// stopWatch = new StopWatch();

	}

	class SubmitAnswer implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			thread.start();
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				question.setText(getMessage());
				int answer = 1;
				try {
					String ans = textfield.getText().trim();
					answer = Integer.parseInt(ans);
				} catch (NumberFormatException e1) {
					textfield.setText("");
				}
				if (!game.check(answer, num1, num2, op)) {
					System.out.println(answer + " ผิด");
					textfield.setText("");
				}
				System.out.println(answer + " ถูก");
				textfield.setText("");
				question();
				question.setText(getMessage());
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void run() {
		while (true) {
			try {
				thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeup++;
			time.setText(timeup + "");
		}
	}

	// public void enter(ActionEvent event) {
	// String text = textfield.getText().trim();
	// int value = Integer.parseInt(text);
	// if (calculator.covert(value) == true) {
	// try {
	// label.setText(calculator.getMessage());
	// textfield.setText(null);
	// } catch (NumberFormatException e) {
	//
	// }
	// } else {
	//
	// }
	// }

	// /**
	// * shows game-over pop-up.
	// * @param controller
	// */
	// private void showGameOverPopup(MainFXMLDocumentController controller) {
	// @SuppressWarnings("deprecation")
	// BorderPane content =BorderPaneBuilder.create()
	// .minWidth(230).minHeight(130)
	// .bottom(getBottomBox(controller))
	// .center(getCenterBox())
	// .style( "-fx-background-color:linear-gradient(darkslategrey, wheat, white);"
	// + "-fx-background-radius:7;"
	// + "-fx-border-radius:7")
	// .build();
	// pp = new Popup();
	// pp.setAutoHide(true);
	// pp.getContent().add(content);
	// pp.show(controller.DOWN.getScene().getWindow());
	// }

}
