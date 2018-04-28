package controller;

import java.util.Random;

import game.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class test2Controller implements Runnable {
	@FXML
	Label label;
	@FXML
	TextField textfield;
	@FXML
	ImageView lion;
	@FXML
	ImageView winner;
	@FXML
	Label textTime;

	Calculator game;

	Font font = new Font("LayijiMahaniyomV105.ttf", 40);

	Random rand = new Random();
	Thread tr = new Thread();
	int score = 0;
	int power = 5;
	int num1 = 0;
	int num2 = 0;
	int timeup = 0;
	char op;

	int result;
	String Message = "";

	@FXML
	public void initialize() {
		tr.start();
		// winner.setDisable(false);
		label.setFont(font);
		textfield.setFont(font);
		game = new Calculator();
		question();
		label.setText(getMessage());
		// game.question(ans);
		textfield.setOnAction(this::answerHandle);
	}

	public void answerHandle(ActionEvent event) {
		label.setText(getMessage());
		String ans = textfield.getText().trim();
		int answer = Integer.parseInt(ans);

		if (!game.check(answer, num1, num2, op)) {
			System.out.println("ตอบผิด");
			textfield.clear();
			// System.out.println("---");
			// ans = textfield.getText().trim();
			// answer = Integer.parseInt(ans);
			// System.out.println("***");
			// game.check(answer,num1,num2,op);
			// answerHandle(event);
		}
		textfield.clear();
		question();
		label.setText(getMessage());
		// if (score == 2) {
		// System.out.println("WIN");
		// // winner();
		// // imagePopupWindowShow();
		// // winner.setDisable(true);
		// }

	}

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
		this.Message = message;
	}

	public String getMessage() {
		return Message;
	}

	@Override
	public void run() {
		while (true) {
			try {
				tr.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeup++;
			textTime.setText(timeup + "");

		}
	}

}
