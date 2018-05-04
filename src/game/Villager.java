package game;

public class Villager {
	private int distance;
	private int people;
	private String nameVillage;
	private boolean active;
	private int x,y,dx,dy;
	private int speed = 10;

	public Villager() {
		
	}
	public Villager(int x, int y, int dx, int dy, boolean active) {
		setProperties(x, dx, dy, active);
	}
	
	public void move() {
		x += dx * speed;
		y += dy * speed;
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
	
	public boolean getActive() {
		return active;
	}
	
	public void setProperties(int x, int dx, int dy, boolean active) {
		this.x = x;
		this.dx = dx;
		this.dy = dy;
		this.active = active;
	}
	
	@Override
	public String toString() {
		return nameVillage;
	} 

}
