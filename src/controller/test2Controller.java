package controller;

import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import game.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

	public void imagePopupWindowShow() {
		// All of our necessary variables
		File imageFile;
		// File audioFile;
		Image image;
		ImageView imageView;
		// Media audio;
		// MediaPlayer audioPlayer;
		BorderPane pane;
		Scene scene;
		Stage stage;

		// The path to your image can be a URL,
		// or it can be a directory on your computer.
		// If the picture is on your computer, type the path
		// likes so:
		// C:\\Path\\To\\Image.jpg
		// If you have a Mac, it's like this:
		// /Path/To/Image.jpg
		// Replace the path with the one on your computer
		imageFile = new File("/res/win.png");
		image = new Image(imageFile.toURI().toString());
		imageView = new ImageView(image);

		// // The same thing applies with audio files. Replace
		// // this with the path to your audio file
		// audioFile = new
		// File("/Users/bryce/NetBeansProjects/Graphics_PM/src/edu/govschool/dangerzone.mp3");
		// audio = new Media(audioFile.toURI().toString());
		// audioPlayer = new MediaPlayer(audio);
		// audioPlayer.setAutoPlay(true);

		// Our image will sit in the middle of our popup.
		pane = new BorderPane();
		pane.setCenter(imageView);
		scene = new Scene(pane);

		// Create the actual window and display it.
		stage = new Stage();
		stage.setScene(scene);
		// Without this, the audio won't stop!
		stage.setOnCloseRequest(e -> {
			e.consume();
			// audioPlayer.stop();
			stage.close();
		});
		stage.showAndWait();
	}

	public void winner() {
		JDialog dialog = new JDialog();
		dialog.setUndecorated(true);
		JLabel label = new JLabel(new ImageIcon("win.png"));
		dialog.add(label);
		dialog.pack();
		dialog.setVisible(true);
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
