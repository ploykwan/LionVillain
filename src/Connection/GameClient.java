package Connection;

import java.io.IOException;
import java.util.Observable;

import javax.swing.JPanel;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import game.Calculator;
import gameUI.MainFrame;
import gameUI.OnlineGame;
import gameUI.WaitingUI;

public class GameClient extends Observable {

	// private DualPlayUI game;
	private Client client;
	private Calculator control;
	private String status;
	private String answer;
	private String playerName;
	private WaitingUI waitingUI;

	public GameClient(String ip, int binding,WaitingUI ui) throws IOException {
		client = new Client();

		client.getKryo().register(SendData.class);

		client.addListener(new ClientListener());
		client.start();
		waitingUI = ui;
		client.connect(5000, ip, binding);
		this.addObserver(waitingUI);
	}

	class ClientListener extends Listener {
		@Override
		public void connected(Connection c) {
			super.connected(c);
			System.out.println("Connected to Server.");
		}

		@Override
		public void received(Connection c, Object o) {
			if (o instanceof SendData) {
				SendData receive = (SendData) o;	
				System.out.println(receive.status);
				if( receive.status.equals("Play")) {
					System.out.println("Open OnlineGame Class");
					startGame();
				}
				if( receive.status.equals("SetName")) {
					System.out.println("SetName");
					playerName = receive.playerName;
				}
				if( receive.status.equals("Correct") ) {
					setChanged();
					notifyObservers(receive);
				}
				if( receive.status.equals("win") || receive.status.equals("lose") ) {
					System.out.println("โง่สัสๆ");
					setChanged();
					notifyObservers(receive);
				}
				if( receive.status.equals("draw")) {
					setChanged();
					notifyObservers(receive);
				}
			}
		}
	}

	public void startGame() {
		OnlineGame ui = new OnlineGame(this);
		setChanged();
		notifyObservers(ui);
		addObserver(ui);
	}

	public void sendMessage() {
		SendData data = new SendData();
		data.ans = this.answer;
		data.status = this.status;
		data.playerName = this.playerName;
		client.sendTCP(data);
	}

	public String getStatus() {
		return status;
	}

	public String getAnswer() {
		return answer;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}
