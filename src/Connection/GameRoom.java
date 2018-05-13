package Connection;

import com.esotericsoftware.kryonet.Connection;
/**
 * Create room for players.
 * @author Kwankaew
 *
 */
public class GameRoom {

	private Connection p1, p2;

	public GameRoom() {

	}

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

}
