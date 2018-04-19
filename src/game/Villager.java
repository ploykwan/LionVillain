package game;

public class Villager {
	private int distance;
	private double time;
	private Calculator calculator;
	private int people;
	private String name;

	public Villager(String name, double time, int people, int distance) {
		this.name = name;
		this.time = time;
		this.people = people;
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return name;
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
}
