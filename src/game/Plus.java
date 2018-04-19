package game;

public class Plus implements Calculator{
	private int num1;
	private int num2;
	
	@Override
	public int covert(int num1, int num2) {
		return num1 + num2;
	}
}
