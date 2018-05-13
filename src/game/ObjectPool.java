package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
/**
 * Reuse a villager.
 * @author Pimwalun Witchawanitchanun
 *
 */
public class ObjectPool extends Observable {

	private List<Villager> villagers;
	private Thread mainLoop;
	private boolean alive;

	private int stop = 750;

	/**
	 * Initialize new ObjectPool to reuse.
	 */
	public ObjectPool() {
		alive = true;
		villagers = new ArrayList<Villager>();
		mainLoop = new Thread() {
			@Override
			public void run() {
				while (alive) {
					moveVillager();
					cleanupVillagers();
					setChanged();
					notifyObservers(villagers);
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		mainLoop.start();
	}

	/**
	 * Move villager.
	 */
	private void moveVillager() {
		for (Villager villager : villagers) {
			villager.move();
		}
	}

	/**
	 * Remove when villager at a specified distance.
	 */
	private void cleanupVillagers() {
		List<Villager> toRemove = new ArrayList<Villager>();
		for (Villager villager : villagers) {
			if (villager.getX() <= (-900 + getStop())) {
				toRemove.add(villager);
			}
		}
		for (Villager villager : toRemove) {
			villager.setProperties(0, 0, false);
			villagers.remove(villager);
		}
	}

	/**
	 * Return list of a villager to release.
	 * @return
	 */
	public List<Villager> getVillager() {
		return villagers;
	}

	/**
	 * Release villager if user answered correctly.
	 * @param x 
	 */
	public void burstVillagers(int x) {
		List<Villager> villagerList = VillagerPool.getInstance().getVillagerList();
		Villager villager = villagerList.get(0);
		villager.setProperties(x, -1, true);
		villagers.add(villager);
	}

	/**
	 * Return distance to stop.
	 * @return distance to stop.
	 */
	public int getStop() {
		return stop;
	}

	/**
	 * Set distance to stop.
	 * @param stop
	 */
	public void setStop(int stop) {
		this.stop = stop;
	}

}
