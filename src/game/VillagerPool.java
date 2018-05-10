package game;

import java.util.ArrayList;
import java.util.List;

public class VillagerPool {
	private static VillagerPool villagerPool;
	private List<Villager> villagers;

	public VillagerPool() {
		villagers = new ArrayList<>();
		for (int i = 0; i < 10; i++)
			villagers.add(new Villager(0, 0, 0, 0, false));
	}

	public static VillagerPool getInstance() {
		if (villagerPool == null)
			villagerPool = new VillagerPool();
		return villagerPool;
	}

	public List<Villager> getBulletList() {
		List<Villager> inactiveVillager = new ArrayList<>();
		for (Villager b : villagers) {
			if (!b.getActive())
				inactiveVillager.add(b);
		}
		return inactiveVillager;
	}
}
