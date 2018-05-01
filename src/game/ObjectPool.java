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
	// private Thread remove;
	private int timeremove = 0;

	public ObjectPool() {
		alive = true;
		villagers = new ArrayList<Villager>();
		mainLoop = new Thread() {
			@Override
			public void run() {
				while (alive) {
					tick();
					setChanged();
					notifyObservers();
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
		for (Villager bullet : villagers) {
			timeremove++;
			if (timeremove == 100) {
				System.out.println("delete");
				toRemove.add(bullet);
				timeremove = 0;
			}
		}
		for (Villager bullet : toRemove) {
			bullet.setProperties(0, 0, 0, false);
			mainLoop.stop();
			villagers.remove(bullet);
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
		List<Villager> bulletList = VillagerPool.getInstance().getBulletList();
		Villager bullet = bulletList.get(0);
		bullet.setProperties(x, -1, 0, true);
		villagers.add(bullet);
		// System.out.println(bullet.toString());
	}

}
