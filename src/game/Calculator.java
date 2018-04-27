package game;

import java.util.Scanner;

public class Calculator {
	private int num1, num2, result;

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

	// public static void main(String[] args) {
	// Calculator calculator = new Calculator();
	// calculator.question();
	// }
}
