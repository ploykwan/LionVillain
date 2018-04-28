package game;

import java.util.Scanner;

public class Calculator {

	private int num1, num2, result;
	private int x, dx;

	public Calculator() {
		this.dx = 10;
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
		x -= getDx();
		return x;
	}

	public boolean isGameEnd() {
		if (x <= -20)
			return true;
		return false;
	}

	public int getX() {
		return x;
	}

	public int getDx() {
		return dx;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setDx(int dx) {
		this.dx += dx;
	}

	// public static void main(String[] args) {
	// Calculator calculator = new Calculator();
	// calculator.question();
	// }
}
