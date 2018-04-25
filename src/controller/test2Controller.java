package controller;

import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import game.Calculator;
import game.Minus;
import game.Plus;
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

public class test2Controller {
	@FXML
	Label label;
	@FXML
	TextField textfield;
	@FXML
	ImageView lion;
	@FXML
	ImageView winner;

	Font font = new Font("LayijiMahaniyomV105.ttf", 40);

	Random rand = new Random();
	int score = 0;
	int power = 5;
	int x = 0;
	int y = 0;
	int oparator = 0;
	int correctAns;

	@FXML
	public void initialize() {
		// winner.setDisable(false);
		label.setFont(font);
		textfield.setFont(font);
		x = rand.nextInt(10) + 1;
		y = rand.nextInt(10) + 1;
		label.setText(String.format("%d + %d = ", x, y));
		textfield.setOnAction(this::answerHandle);

	}

	public void answerHandle(ActionEvent event) {
		correctAns = x + y;
		String ans = textfield.getText().trim();
		int answer = Integer.parseInt(ans);

		if (answer == correctAns) {
			power += 5;
			lion.setX(lion.getX() + power);
			System.out.println("T: " + x + " + " + y + " = " + answer);
			score++;
		}
		textfield.clear();
		x = rand.nextInt(10) + 1;
		y = rand.nextInt(10) + 1;
	
		label.setText(String.format("%d %s %d = ", x, "+", y));
		if (score == 2) {
			System.out.println("WIN");
			// winner();
			// imagePopupWindowShow();
			// winner.setDisable(true);
		}

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

}
