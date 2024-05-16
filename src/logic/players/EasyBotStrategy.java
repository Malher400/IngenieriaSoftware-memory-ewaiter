package logic.players;


public class EasyBotStrategy implements Strategy{
	
	String name = "easy";

	public int select() {
		return BotEye.getRandCard();
	}
	
	public Strategy parse(String diff) {
		if(diff.toLowerCase().contentEquals(name)) return new EasyBotStrategy();
		else return null;
	}
	
	public String getName() {
		return name;
	}

}
