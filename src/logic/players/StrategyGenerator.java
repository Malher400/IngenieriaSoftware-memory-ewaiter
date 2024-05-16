package logic.players;


public class StrategyGenerator {

	private static Strategy[] availableStrats = {
		new HumanStrategy(),
		new EasyBotStrategy(),
		new NormalBotStrategy(),
		new HardBotStrategy(),
	};
	
	// Devuelve el Bot adecuado
	public static Strategy parse(String diff) {
		Strategy s = null;
		int i = 0;
		while(i < availableStrats.length && s == null) {
			s = availableStrats[i].parse(diff);
			i++;
		}
		return s;
	}
	
}
