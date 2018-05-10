package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ObjectPool extends Observable {

	private int width = 1280;
	private int height = 720;

	private List<Villager> villagers;
	private Thread mainLoop;
	private boolean alive;
	private Calculator game = new Calculator();
	// private Thread remove;
	private int timeremove = 0;

	private int stop = 750;

	public ObjectPool() {
		alive = true;
		villagers = new ArrayList<Villager>();
		mainLoop = new Thread() {
			@Override
			public void run() {
				while (alive) {
					tick();
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

	public void tick() {
		moveBullets();
		cleanupVillagers();
	}

	private void moveBullets() {
		for (Villager villager : villagers) {
			villager.move();
		}
	}

	private void cleanupVillagers() {
		List<Villager> toRemove = new ArrayList<Villager>();
		for (Villager villager : villagers) {
			timeremove++;
			if (villager.getX() == (-900 + getStop())) {
				toRemove.add(villager);
				timeremove = 0;
			}
		}
		for (Villager villager : toRemove) {
			villager.setProperties(0, 0, 0, false);
			villagers.remove(villager);
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<Villager> getVillager() {
		return villagers;
	}

	public void burstVillagers(int x) {
		List<Villager> villagerList = VillagerPool.getInstance().getBulletList();
		Villager villager = villagerList.get(0);
		villager.setProperties(x, -1, 0, true);
		villagers.add(villager);
	}

	public int getStop() {
		return stop;
	}

	public void setStop(int stop) {
		this.stop = stop;
	}

}
