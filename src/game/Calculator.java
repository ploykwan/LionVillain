package game;

import Connection.PlayerTable;

/**
 * Control the game action.
 * @author Pimwalun Witchawanitchanun
 *
 */
public class Calculator {
	private int result;
	private Villager v1,v2;
	private PlayerTable p1;

	/**
	 * Initialize new game to calculator. 
	 */
	public Calculator() {
		this.v1 = new Villager();
		this.v2 = new Villager();
	}

	/**
	 * Create a new game with the given PlyerTable of database.
	 * @param p
	 */
	public Calculator(PlayerTable p) {
		p1.setName(p.getName());
		p1.setScore(0);
	}

	/**
	 * Initialize new game for 2 players.
	 * @param v1 is first player.
	 * @param v2 is second player.
	 */
	public Calculator(Villager v1, Villager v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	/**
	 * Check the answer that correct or not.
	 * @param ans
	 * @param num1
	 * @param num2
	 * @param op
	 * @return true if answer is correct.
	 * 			false if answer is not correct.
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
		System.out.println("true");
		return true;
	}

	public int V1push() {
		V2setX((V2getX() - V2getDX()));
		V1setX((V1getX() + V2getDX()));
		System.out.println(V1getX() + " " + V2getX() + " " + V2getDX());
		System.out.println("v2.getX(): " + V1getX() + " v1.getX(): " + V2getX());
		return V2getX();
	}
	
	public int V2push() {
		V2setX((V2getX() + V2getDX()));
		V1setX((V1getX() - V2getDX()));
		System.out.println(V1getX() + " " + V2getX() + " " + V2getDX());
		System.out.println("v2.getX(): " + V1getX() + " v1.getX(): " + V2getX());
		return V2getX();
	}

	public int V1getX() {
		return v2.getX();
	}

	public void V1setX(int x) {
		v2.setX(x);
	}

//	public int V1getDx() {
//		return v1.getDx();
//	}
//
//	public void V1setDx(int dx) {
//		v1.setDx(dx);
//	}
	
	public int V2getX() {
		return v1.getX();
	}

	private int V2getDX() {
		return v1.getDx();
	}

	public void V2setDx(int dx) {
		v1.setDx(dx);
	}

	public void V2setX(int x) {
		v1.setX(x);
	}

	public int push() {
		v1.setX(v1.getX() - v1.getDx());
		System.out.println("v.getX(): " + v1.getX());
		return v1.getX();
	}

	public int back() {
		v1.setX(v1.getX() + 20);
		return v1.getX();
	}

	/**
	 * Return x-coordinate of V1.
	 * @return
	 */
	public int getX() {
		return v1.getX();
	}

	/**
	 * Return distance x of V1.
	 * @return
	 */
	public int getDx() {
		return v1.getDx();
	}

	/**
	 * Set x-coordinate of V1.
	 * @param x
	 */
	public void setX(int x) {
		v1.setX(x);
	}

	/**
	 * Set distance x of V1.
	 * @param dx
	 */
	public void setDx(int dx) {
		v1.setDx(dx);
	}

}
