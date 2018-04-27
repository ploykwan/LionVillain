package game;

import java.util.Scanner;

public class Calculator {
	private int num1, num2, ans;
	private boolean correct = true;
	private char operator[] = { '+', '-', '*', '/' };
	private char op;
	private int id, result;
	private String message = "";

	// public String question(int ans) {
	// while (true) {
	// num1 = (int) (1 + (Math.random() * 12));
	// num2 = (int) (1 + (Math.random() * 12));
	// id = (int) (Math.random() * 4);
	// op = operator[id];
	// setMessage(num1 + " " + op + " " + num2);
	// System.out.println(num1 + " " + op + " " + num2);
	// switch (op) {
	// case '+':
	// result = (int) (num1 + num2);
	// break;
	// case '-':
	// result = (int) (num1 - num2);
	// break;
	// case '*':
	// result = (int) (num1 * num2);
	// break;
	// case '/':
	// result = (int) (num1 / num2);
	// break;
	// }
	// check(result,ans);
	// }
	// }

	public int getNum1() {
		return num1;
	}

	public int getNum2() {
		return num2;
	}

	public boolean check(int ans, int num1, int num2,char op) {
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
		System.out.println("คำตอบจ้า: "+result+" "+num1+" "+num2);
		if (ans != result) {
//			check(ans,num1,num2,op);
			System.out.println("ผิดจ้า");
			return false;
		}
		return true;
	}

	public String getMessage() {
		if (message == "")
			return "null";
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// public static void main(String[] args) {
	// Calculator calculator = new Calculator();
	// calculator.question();
	// }
}
