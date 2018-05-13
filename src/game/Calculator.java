package game;

import Connection.PlayerTable;

/**
 * Control the game action.
 * 
 * @author Pimwalun Witchawanitchanun
 *
 */
public class Calculator {
	private int result;
	private Villager v2, v1;
	private PlayerTable p1;

	/**
	 * Initialize new game to calculator.
	 */
	public Calculator() {
		this.v2 = new Villager();
		this.v1 = new Villager();
	}

	/**
	 * Create a new game with the given PlyerTable of database.
	 * 
	 * @param p
	 */
	public Calculator(PlayerTable p) {
		p1.setName(p.getName());
		p1.setScore(0);
	}

	/**
	 * Initialize new game for 2 players.
	 * 
	 * @param v1
	 *            is first player.
	 * @param v2
	 *            is second player.
	 */
	public Calculator(Villager a, Villager b) {
		this.v1 = b;
		this.v2 = a;
	}

	/**
	 * Check the answer that correct or not.
	 * 
	 * @param ans
	 * @param num1
	 * @param num2
	 * @param op
	 * @return true if answer is correct. false if answer is not correct.
	 */
	public boolean check(int ans, int num1, int num2, char op) {
		switch (op) {
		case '+':
			result = (int) (num1 + num2);
			break;
		case '-':
			result = (int) (num1 - num2);
			break;
		case 'x':
			result = (int) (num1 * num2);
			break;
		case '÷':
			result = (int) (num1 / num2);
			break;
		}

		if (ans != result) {
			return false;
		}
//		System.out.println("true");
		return true;
	}

	public int V1push() {
		V2setX((V2getX() - V2getDX()));
		V1setX((V1getX() + V2getDX()));
//		System.out.println(V1getX() + " " + V2getX() + " " + V2getDX());
		return V2getX();
	}

	public int V2push() {
		V2setX((V2getX() + V2getDX()));
		V1setX((V1getX() - V2getDX()));
//		System.out.println(V1getX() + " " + V2getX() + " " + V2getDX());
		return V2getX();
	}

	public int V1getX() {
		return v1.getX();
	}

	public void V1setX(int x) {
		v1.setX(x);
	}

	// public int V1getDx() {
	// return v1.getDx();
	// }
	//
	// public void V1setDx(int dx) {
	// v1.setDx(dx);
	// }

	public int V2getX() {
		return v2.getX();
	}

	private int V2getDX() {
		return v2.getDx();
	}

	public void V2setDx(int dx) {
		v2.setDx(dx);
	}

	public void V2setX(int x) {
		v2.setX(x);
	}

	public int push() {
		v2.setX(v2.getX() - v2.getDx());
		System.out.println("v.getX(): " + v2.getX());
		return v2.getX();
	}

	public int back() {
		v2.setX(v2.getX() + 20);
		return v2.getX();
	}

	/**
	 * Return x-coordinate of village.
	 * 
	 * @return
	 */
	public int getX() {
		return v2.getX();
	}

	/**
	 * Return distance x of village.
	 * 
	 * @return
	 */
	public int getDx() {
		return v2.getDx();
	}

	/**
	 * Set x-coordinate of village.
	 * 
	 * @param x
	 */
	public void setX(int x) {
		v2.setX(x);
	}

	/**
	 * Set distance x of village.
	 * 
	 * @param dx
	 */
	public void setDx(int dx) {
		v2.setDx(dx);
	}

}
