package Connection;

import java.io.IOException;
import java.util.Observable;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import game.Calculator;
import gameUI.OnlineGame;

public class GameClient extends Observable{
	
	

//	private DualPlayUI game;
	private Client client;
	private Calculator control;
	private OnlineGame game;
	private String status;
	private int answer;
	private int x;

	public GameClient() throws IOException {
		client = new Client();

		client.getKryo().register(SendData.class);

		client.addListener(new ClientListener());
		client.start();
		client.connect(5000, "127.0.0.1", 54333);
	}

	class ClientListener extends Listener {
		@Override
		public void connected(Connection c) {
			super.connected(c);
			System.out.println("Connected to Server.");
		}
		
		@Override
		public void received(Connection c, Object o) {
			super.received(c, o);
			if( o instanceof SendData) {
				SendData receive = (SendData) o;
				if(receive.status.equals("Ready")) {
					
					setStatus("Play");
					startGame();
				}
				if(receive.status.equals("Answer")) {
					answer = receive.answer;
					setChanged();
					notifyObservers();
				}
			}
		}
	}
	private void startGame() {
		System.out.println("start online game");
		game = new OnlineGame(control,this);
		game.play();
		setChanged();
		notifyObservers();
		this.addObserver(game);
	}
	public void sendMessage() {
		SendData data = new SendData();
		data.answer = this.answer;
		data.x = this.x;
		data.status = this.status;
		
		client.sendTCP(data);
		System.out.println("Message sent(answer,distance,staus): "+data.answer+" "+data.x+" "+data.status);
	}

	public String getStatus() {
		return status;
	}

	public int getAnswer() {
		return answer;
	}

	public int getX() {
		return x;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public void setX(int x) {
		this.x = x;
	}

	public static void main(String[] args) {
		try {
			new GameClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
