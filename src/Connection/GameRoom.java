package Connection;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;

public class GameRoom {

	private List<Connection> player;

	public GameRoom() {
		player = new ArrayList<Connection>();
	}

	public void addPlayer(List<Connection> p) {
		this.player = p;
	}

	public List<Connection> getPlayer() {
		return this.player;
	}

	public boolean isFull() {
		return (player.size() == 2) ? true : false;
	}

	public void addConnection(Connection c) {
		if(!isFull()) {
			player.add(c);
		}
		else {
			System.out.println("This room is already full!");
		}
	}
}
