package game;

import Connection.PlayerTable;

public class Calculator{
	private int result;
	private Villager v1;
	private Villager v2;
	private PlayerTable p1, p2;

	public Calculator() {
		this.v1 = new Villager();
		this.v2 = new Villager();
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
	
//	public boolean isGameEnd() {
//		if (v1.getX() <= -10)
//			return true;
//		return false;
//	}

	public int V1push() {
		v1.setX(v1.getX() - v1.getDx());
		v2.setX(v2.getX() + v1.getDx());
		System.out.println("v2.getX(): "+v2.getX()+" v1.getX(): "+v1.getX());
		return v1.getX();
	}

	public int V1reverse() {
		v1.setX(v1.getX() + v1.getDx());
		v2.setX(v2.getX() - v1.getDx());
		System.out.println("v2.getX(): "+v2.getX()+" v1.getX(): "+v1.getX());
		return v1.getX();
	}

	public int V1getX() {
		return v1.getX();
	}

	public int V1getDx() {
		return v1.getDx();
	}

	public void V1setX(int x) {
		v1.setX(x);
	}

	public void V1setDx(int dx) {
		v1.setDx(dx);
	}
	
	public int V2push() {
		v2.setX(v2.getX() + v2.getDx());
		v1.setX(v1.getX() - v2.getDx());
		System.out.println("v2.getX(): "+v2.getX()+" v1.getX(): "+v1.getX());
		return v2.getX();
	}

	public int V2reverse() {
		v2.setX(v2.getX() - 10);
		return v2.getX();
	}

	public int V2getX() {
		return v2.getX();
	}

	public int V2getDx() {
		return v2.getDx();
	}

	public void V2setX(int x) {
		v2.setX(x);
	}

	public void V2setDx(int dx) {
		v2.setDx(dx);
	}
	
	public int push() {
		v1.setX(v1.getX() - v1.getDx());
		return v1.getX();
	}

	public int back() {
		v1.setX(v1.getX() + 10);
		return v1.getX();
	}
	
	public int getX() {
		return v1.getX();
	}

	public int getDx() {
		return v1.getDx();
	}

	public void setX(int x) {
		v1.setX(x);
	}

	public void setDx(int dx) {
		v1.setDx(dx);
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
