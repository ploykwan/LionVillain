package game;

/**
 * The Villager for this game contain x-coordinate, y-coordinate, distance of x,
 * distance of y, and active for use in objectpool.
 * @author Pimwalun Witchawanitchanun
 *
 */
public class Villager {
	private boolean active;
	private int x,dx;
	private int speed = 10;

	/**
	 * Initialize new Villager.
	 */
	public Villager() {
	}
	
	/**
	 * Initialize new Villager with x, y, dx, dy, and active.
	 * @param x is x-coordinate.
	 * @param y is y-coordinate.
	 * @param dx is distance of x.
	 * @param dy is distance of y.
	 * @param active
	 */
	public Villager(int x,int dx, boolean active) {
		setProperties(x, dx, active);
	}
	
	/**
	 * Speed of villager.
	 */
	public void move() {
		x += dx * speed;
	}

	/**
	 * Return coordinate of x.
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Set coordinate of x.
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Return distance of x.
	 * @return dx
	 */
	public int getDx() {
		return dx;
	}

	/**
	 * Set distance of x to increase x unit.
	 * @param dx
	 */
	public void setDx(int dx) {
		this.dx += dx;
	}
	
	/**
	 * Return boolean active of villager.
	 * @return active
	 */
	public boolean getActive() {
		return active;
	}
	
	/**
	 * Set properties of villager.
	 * @param x
	 * @param dx
	 * @param dy
	 * @param active
	 */
	public void setProperties(int x, int dx, boolean active) {
		this.x = x;
		this.dx = dx;
		this.active = active;
	}
}
