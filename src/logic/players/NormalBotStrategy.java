package logic.players;

import java.util.Random;

public class NormalBotStrategy implements Strategy{
	
	String name = "normal";
	boolean inProcess = false;
	Random rand = new Random();

	public int select() {
		int card;
		// Elige aleatoriamente si no conoce ninguna pareja y con una probabilidad del 70% si no está en medio de revelar una pareja
		if(!BotEye.getIfPending() || (rand.nextDouble() < 0.3 && !inProcess)) { 
			return BotEye.getRandCard();
		}
		else {
			if(!inProcess) {
				inProcess = true;
				card = BotEye.getFirstPending1();
			}
			else {
				card = BotEye.getFirstPending2();
				inProcess = false;
			}
		}
		return card;
	}
	
	public Strategy parse(String diff) {
		if(diff.toLowerCase().contentEquals(name)) return new NormalBotStrategy();
		else return null;
	}

	public String getName() {
		return name;
	}
}
