package game;

public class DualVillager {
	private Villager v1, v2;
	
	public DualVillager(Villager v1, Villager v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public void V1Correct() {
		v2.setDistance(v2.getDistance()-v1.getPeople());
	}
	
	public void V2Correct() {
		v1.setDistance(v1.getDistance()-v2.getPeople());
	}
	
	public boolean V1Lose() {
		return v1.getDistance() < v2.getDistance();
	}
	
	public boolean V2Lose() {
		return v2.getDistance() < v1.getDistance();
	}
	
	public Villager getV1() {
		return v1;
	}
	
	public Villager getV2() {
		return v2;
	}
	
	public boolean end() {
		return V1Lose() || V2Lose();
	}
}