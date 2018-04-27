package game;

import java.util.Scanner;

public class Calculator {
	private int num1, num2, ans;
	private boolean correct = true;
	private char operator[] = { '+', '-', '*', '/' };
	private char op;
	private int id, result = 0;
	private String message;

	
	
	public String question(int ans) {
		while (true) {
			num1 = (int) (1 + (Math.random() * 12));
			num2 = (int) (1 + (Math.random() * 12));
			id = (int) (Math.random() * 4);
			op = operator[id];
			this.message = num1 + " " + op + " " + num2;
			System.out.println(num1 + " " + op + " " + num2);
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
			check(result);
		}

	}
	
	public int getNum1() {
		return num1;
	}
	
	public int getNum2() {
		return num2;
	}
	
	private boolean check(int result) {
		Scanner scanner = new Scanner(System.in);
		ans = scanner.nextInt();
		if(ans != result) {
			check(result);
		}
		return true;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static void main(String[] args) {
		Calculator calculator = new Calculator();
		calculator.question(0);
	}
}
