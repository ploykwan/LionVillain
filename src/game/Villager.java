package game;

public class Villager {
	private int distance;
	private double time;
	private Calculator calculator;
	private int people;
	private String nameVillage;
	private int x,y,dx,dy;

	public Villager() {
		
	}
	public Villager(String name, double time, int people, int distance) {
		this.nameVillage = name;
		this.time = time;
		this.people = people;
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return nameVillage;
	}
	
	public double getTime() {
		return time;
	}
	
	public void setTime(double time) {
		this.time = time;
	}
	
	public int getPeople() {
		return people;
	}
	
	public void setPeople(int people) {
		this.people = people;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setDx(int dx) {
		this.dx += dx;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}
	
	
}
