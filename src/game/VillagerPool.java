package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Configure villagers to release.
 * @author Pimwalun Witchawanitchanun
 *
 */
public class VillagerPool {
	private static VillagerPool villagerPool;
	private List<Villager> villagers;

	/**
	 * Initialize new VillagerPool to determine the number of villager release.
	 */
	public VillagerPool() {
		villagers = new ArrayList<>();
		for (int i = 0; i < 10; i++)
			villagers.add(new Villager(0, 0, false));
	}

	/**
	 * Return an object of VillagerPool.
	 * @return get an instance of VillagerPool.
	 */
	public static VillagerPool getInstance() {
		if (villagerPool == null)
			villagerPool = new VillagerPool();
		return villagerPool;
	}

	/**
	 * Return list of Villager to release.
	 * @return inactiveVillager of Villager to release.
	 */
	public List<Villager> getVillagerList() {
		List<Villager> inactiveVillager = new ArrayList<>();
		for (Villager b : villagers) {
			if (!b.getActive())
				inactiveVillager.add(b);
		}
		return inactiveVillager;
	}
}
