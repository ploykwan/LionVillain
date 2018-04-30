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

		client.addListener(new ClientListener());
		client.start();
		client.connect(5000, "127.0.0.1", 54333);
	}

	class ClientListener extends Listener {
		@Override
		public void connected(Connection c) {
			super.connected(c);
		}

		@Override
		public void disconnected(Connection c) {
			super.disconnected(c);
		}

		@Override
		public void received(Connection c, Object o) {
			super.received(c, o);
			if( o instanceof Villager) {
				Villager v = (Villager) o;
				
			}
		}
	}

	public static void main(String[] args) {
		try {
			new GameClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
