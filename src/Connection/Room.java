package Connection;

import com.lloseng.ocsf.server.ConnectionToClient;

public class Room {
	private ConnectionToClient p1 = null;
	private ConnectionToClient p2 = null;

	public void add(ConnectionToClient c) {
		if (!isFull()) {
			if (p1 == null)
				p1 = c;
			p2 = c;
		}

	}

	public ConnectionToClient getOpponent(ConnectionToClient c) {
		if (c == p1)
			return p2;
		else if (c == p2)
			return p1;
		else
			return null;
	}
	
	public void p1Disconnected() {
		p1 = p2;
		p2 = null;
	}
	
	public void p2Disconnected() {
		p2 = null;
	}

	private boolean isFull() {
		if (p1 != null && p2 != null) {
			return true;
		}
		return false;
	}

	public ConnectionToClient getP1() {
		return p1;
	}

	public ConnectionToClient getP2() {
		return p2;
	}
	
}
