package logic.players;

public interface Strategy {
	public int select();
	public Strategy parse(String diff);
	public String getName();
}
