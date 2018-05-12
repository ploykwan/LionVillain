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
/**
 * Create server and the server will send data to clients.
 * @author Kwankaew
 *
 */
public class GameServer extends Observable {

	private Server server;
	private List<GameRoom> gameRoom;
	private List<Connection> connections;
	

	public GameServer() throws IOException {
		server = new Server();
		connections = new ArrayList<Connection>();
		gameRoom = new ArrayList<GameRoom>();
		server.bind(54333);
		server.getKryo().register(SendData.class);
		server.addListener(new ServerListener());

		server.start();
//		System.out.println("Server started.");
	}

	class ServerListener extends Listener {
		@Override
		public void connected(Connection c) {
			super.connected(c);
			GameRoom game = findAvailableRoom();
			String player = game.addConnection(c);
			SendData data = new SendData();
			data.status = "SetName";
			data.playerName = player;
			c.sendTCP(data);
			if(game.isFull()) {
//				System.out.println("Room Full");
				data.status = "Play";
				game.getP1().sendTCP(data);
				game.getP2().sendTCP(data);
			}
		}

		@Override
		public void disconnected(Connection c) {
			super.disconnected(c);
			connections.remove(c);
//			System.out.println("Client disconnected.");
			setChanged();
			notifyObservers("Client disconnected.");
		}

		@Override
		public void received(Connection c, Object o) {
			super.received(c, o);
			if (o instanceof SendData) {
				SendData receive = (SendData) o;
				SendData data = new SendData();
				GameRoom room = findGameByConnection(c);
//				System.out.println(receive.status);
				if(receive.status.equals("Correct")) {
//					System.out.println(receive.playerName);
					if(room!=null) {
						room.getP1().sendTCP(receive);
						room.getP2().sendTCP(receive);
					}
					else System.out.println("Logic Error!!!!!!");
				}
				else if(receive.status.equals("End")) {
					if(room!=null) {
						if(receive.playerName.equals("p1")) {
							data.status = "lose";
							data.playerName = "p1";
							room.getP1().sendTCP(data);
							data.status = "win";
							data.playerName = "p2";
							room.getP2().sendTCP(data);
						}
						else if(receive.playerName.equals("p2")) {
							data.status = "win";
							data.playerName = "p1";
							room.getP1().sendTCP(data);
							data.status = "lose";
							data.playerName = "p2";
							room.getP2().sendTCP(data);	
						}
					}
//					else System.out.println("Logic Error!!!!!!");
				}
				if(receive.status.equals("p1Win")) {
//					System.out.println("status: p1Win");
					data.status = "win";
					data.playerName = "p1";
					room.getP1().sendTCP(data);
					data.status = "lose";
					data.playerName = "p2";
					room.getP2().sendTCP(data);
				}
				else if(receive.status.equals("p2Win")) {
//					System.out.println("status: p2Win");
					data.status = "lose";
					data.playerName = "p1";
					room.getP1().sendTCP(data);
					data.status = "win";
					data.playerName = "p2";
					room.getP2().sendTCP(data);	
				}
				else if(receive.status.equals("draw")) {
//					System.out.println("status: draw");
					data.status = "draw";
					data.playerName = "p1";
					room.getP1().sendTCP(data);
					data.status = "draw";
					data.playerName = "p2";
					room.getP2().sendTCP(data);	
				}
			}
		}
	}

	public GameRoom findAvailableRoom() {
		if (gameRoom.size() == 0) {
//			System.out.println("No Room Available! Create New One");
			setChanged();
			notifyObservers("No Room Available! Create New One");
			GameRoom room = new GameRoom();
			gameRoom.add(room);
			return room;
		} else {
			for (GameRoom room : gameRoom) {
				if (!room.isFull()) {
					return room;
				}
			}
			GameRoom room = new GameRoom();
			gameRoom.add(room);
			return room;
		}
	}

	public GameRoom findGameByConnection(Connection connection) {
		for(GameRoom room : gameRoom) {
			if(room.getP1().equals(connection)||room.getP2().equals(connection)) {
				return room;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			GameServer server = new GameServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
