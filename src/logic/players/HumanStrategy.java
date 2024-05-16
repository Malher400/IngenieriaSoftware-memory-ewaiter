package logic.players;

public class HumanStrategy implements Strategy{
	
	private String name = "human";

	public int select() {
		return -1;
	}

	public Strategy parse(String diff) {
		if(diff.toLowerCase().contentEquals(name)) return new HumanStrategy();
		else return null;
	}

	public String getName() {
		return name;
	}
}
