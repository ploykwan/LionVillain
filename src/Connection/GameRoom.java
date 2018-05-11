package Connection;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;

public class GameRoom {

	// private List<Connection> player;
	private Connection p1, p2;
	private int count = 0;

	public GameRoom() {
		// player = new ArrayList<Connection>();

	}

	// public void addPlayer(List<Connection> p) {
	// this.player = p;
	// }
	//
	// public List<Connection> getPlayer() {
	// return this.player;
	// }

	public boolean isFull() {
		if (p1 != null && p2 != null) {
			return true;
		}
		return false;
	}

	public String addConnection(Connection c) {
		if (!isFull()) {
			if (p1 == null) {
				p1 = c;
				return "p1";
			}

			else {
				p2 = c;
				return "p2";
			}
		}
		return "";
	}
	
	public Connection getP1() {
		return p1;
	}
	
	public Connection getP2() {
		return p2;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	

}
