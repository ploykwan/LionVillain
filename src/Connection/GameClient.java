package Connection;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import game.Calculator;
import game.Villager;
import gameUI.DualPlayUI;

public class GameClient {

	private DualPlayUI game;

	private Client client;

	public GameClient() throws IOException {
		client = new Client();

		client.getKryo().register(Calculator.class);
		client.getKryo().register(Villager.class);
		client.getKryo().register(GameAnswer.class);

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
			if( o instanceof GameAnswer) {
				GameAnswer v = (GameAnswer) o;
				game.setAnswer(v.answer);
				game.getDualPlayModePanel();
			}
		}
	}
	
	public void sendAnswer(int ans) {
		GameAnswer a = new GameAnswer();
		a.answer = ans;
		client.sendTCP(a);
	}

	public static void main(String[] args) {
		try {
			new GameClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
