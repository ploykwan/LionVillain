package game;

import java.util.Scanner;

public class Calculator {
	private int num1, num2, result;
	private Villager v = new Villager();
	private int x, dx;

	public Calculator() {
		v.setDx(10);
//		this.dx = 10;
	}

	public int getNum1() {
		return num1;
	}

	public int getNum2() {
		return num2;
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
		// x -= getDx();
		v.setX(v.getX() - v.getDx());
		return v.getX();
	}

	public boolean isGameEnd() {
		if (v.getX() <= -20)
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
//		this.x = x;
	}

	public void setDx(int dx) {
		v.setDx(dx);
//		this.dx += dx;
	}

	// public static void main(String[] args) {
	// Calculator calculator = new Calculator();
	// calculator.question();
	// }
}
