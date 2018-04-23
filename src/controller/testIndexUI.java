package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class testIndexUI {
	@FXML
	Button player1;
	@FXML 
	Button player2;
	
	@FXML
	public void initialize() {
		player1.setOnAction(this::player1ButtonHandle);
		player2.setOnAction(this::player2ButtonHandle);
	}
	
	public void player1ButtonHandle(ActionEvent event) {
		System.out.println("1 player");
	}
	
	public void player2ButtonHandle(ActionEvent event) {
		System.out.println("2 players");
	}
}
