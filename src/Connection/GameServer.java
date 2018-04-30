package Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import game.*;
import gameUI.DualPlayUI;

public class GameServer {
	
	private Server server;
	private List<Connection> connections;
	
	public GameServer() throws IOException {
		server = new Server();
		connections = new ArrayList<Connection>();
		
		server.getKryo().register(Calculator.class);
		server.getKryo().register(Villager.class);
		server.getKryo().register(DualPlayUI.class);
		
		server.start();
		server.bind(54333);
		System.out.println("Server started.");
	}
	
	class ServerListener extends Listener{
		@Override
		public void connected(Connection c) {
			super.connected(c);
			connections.add(c);
			System.out.println("Client connected the server.");
			Villager p1 = new Villager();
			
		}
		
		@Override
		public void disconnected(Connection c) {
			super.disconnected(c);
			connections.remove(c);
			System.out.println("Client dis connected.");
		}
		
		@Override
		public void received(Connection c, Object o) {
			super.received(c, o);
			if(o instanceof Villager) {
				Villager v = (Villager) o;
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			GameServer gameServer = new GameServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
