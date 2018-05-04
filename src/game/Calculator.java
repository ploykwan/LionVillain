package game;

import Connection.PlayerTable;

public class Calculator {
	private int result;
	private Villager v = new Villager(), v1, v2;
	private PlayerTable p1,p2;

	public Calculator() {
	}
	
	public Calculator(PlayerTable p) {
		p1.setName(p.getName());
		p1.setScore(0);
	}

	// for 2 player.
	public Calculator(Villager v1, Villager v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	public boolean check(int ans, int num1, int num2, char op) {
		switch (op) {
		case '+':
			result = (int) (num1 + num2);
			break;
		case '-':
			result = (int) (num1 - num2);
			break;
		case '*':
			result = (int) (num1 * num2);
			break;
		case '/':
			result = (int) (num1 / num2);
			break;
		}

		if (ans != result) {
			return false;
		}
		return true;
	}

	public int push() {
		v.setX(v.getX() - v.getDx());
		return v.getX();
	}
	
	public int back() {
		v.setX(v.getX() + 10);
		return v.getX();
	}

	public boolean isGameEnd() {
		if (v.getX() <= -10)
			return true;
		return false;
	}

	public int getX() {
		return v.getX();
	}

	public int getDx() {
		return v.getDx();
	}

	public void setX(int x) {
		v.setX(x);
	}

	public void setDx(int dx) {
		v.setDx(dx);
	}

	public void V1Correct() {
		v2.setDistance(v2.getDistance() - v1.getPeople());
	}

	public void V2Correct() {
		v1.setDistance(v1.getDistance() - v2.getPeople());
	}

	public boolean V1Lose() {
		return v1.getDistance() < v2.getDistance();
	}

	public boolean V2Lose() {
		return v2.getDistance() < v1.getDistance();
	}

	public Villager getV1() {
		return v1;
	}

	public Villager getV2() {
		return v2;
	}

	public boolean end() {
		return V1Lose() || V2Lose();
	}
}
