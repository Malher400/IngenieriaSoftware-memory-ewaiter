package logic.players;

public class HardBotStrategy implements Strategy{

	private String name = "hard";
	private boolean inProcess = false;

	public int select() {
		int card;
		if(!BotEye.getIfPending()) { // Si no conoce pareja, elige de forma aleatoria
			return BotEye.getRandUnseenCard();
		}
		else {
			if(!inProcess) {
				inProcess = true;
				card = BotEye.getFirstPending1(); // Selecciona la primera carta de la primera pareja
			}
			else {
				card = BotEye.getFirstPending2(); // Selecciona la segunda carta de la primera pareja
				inProcess = false;
			}
		}
		return card;
	}

	public Strategy parse(String diff) {
		if(diff.toLowerCase().contentEquals(name)) return new HardBotStrategy();
		else return null;
	}
	
	public String getName() {
		return name;
	}
}
