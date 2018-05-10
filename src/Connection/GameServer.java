package Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import game.*;
import gameUI.DualPlayUI;

public class GameServer extends Observable{
	
	private Server server;
	private List<GameRoom> gameRoom;
	private List<Connection> connections;
	private List<SendData> data;
	
	public GameServer() throws IOException {
		server = new Server();
		connections = new ArrayList<Connection>();
		gameRoom = new ArrayList<GameRoom>();
		
		server.getKryo().register(SendData.class);
		server.addListener(new ServerListener());
		
		server.start();
		server.bind(54333);
		System.out.println("Server started.");
	}
	
	class ServerListener extends Listener{
		@Override
		public void connected(Connection c) {
			super.connected(c);
			GameRoom game = findAvailableRoom();
			game.addConnection(c);
			SendData data = new SendData();
			c.sendTCP(data);
		}
		
		@Override
		public void disconnected(Connection c) {
			super.disconnected(c);
			connections.remove(c);
			System.out.println("Client dis connected.");
			setChanged();
			notifyObservers("Client dis connected.");
		}
		
		@Override
		public void received(Connection c, Object o) {
			super.received(c, o);
			if(o instanceof SendData) {
				SendData recieve = (SendData) o;
				if(recieve.status.equals("Connecting")) {
					System.out.println("Connected to the room");
					GameRoom game = findAvailableRoom();
					if(game.isFull()) {
						for(Connection player : game.getPlayer()) {
							SendData data = new SendData();
							data.status = "Ready";
							player.sendTCP(data);
						}
					}
				}
			}
		}
	}
	
	public GameRoom findAvailableRoom() {
		if(gameRoom.size() == 0) {
			System.out.println("No Room Available! Create New One");
			setChanged();
			notifyObservers("No Room Available! Create New One");
			GameRoom room = new GameRoom();
			gameRoom.add(room);
			return room;
		}else {
			for(GameRoom room : gameRoom) {
				if( !room.isFull() ) {
					return room;
				}
			}
			GameRoom room = new GameRoom();
			gameRoom.add(room);
			return room;
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
