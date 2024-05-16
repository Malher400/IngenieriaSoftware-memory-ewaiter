package cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logic.cards.Action;
import logic.cards.ActionReceiver;
import logic.cards.BasicCard;
import logic.cards.BombCard;
import logic.cards.DeathSkullCard;
import logic.cards.ElixirPair;
import logic.cards.GoldenPair;
import logic.cards.NightmareCard;
import logic.cards.PoisonCard;
import logic.cards.ShieldPair;
import logic.cards.ThiefPair;
import logic.players.HumanStrategy;
import logic.players.Player;

class ActionTest {
	
	private static Player p1;
	private static BasicCard c1;
	private static BombCard c2;
	private static DeathSkullCard c3;
	private static ElixirPair c4;
	private static GoldenPair c5;
	private static NightmareCard c6;
	private static PoisonCard c7;
	private static ShieldPair c8;
	private static ThiefPair c9;
	public static final int POINTS = 10;
	public static final String basicInfo = " discovered a pair of basic cards: +" + POINTS + " points.";
	public static final int SUBTRACTION = 20;
	public static final String bombInfo = " discovered a hidden bomb: -" + SUBTRACTION + " points.";
	public static final String deathSkullInfo = " gets a taste of death: loses all points.";
	public static final int EXTRA_PERCENTAGE = 50;
	public static final String elixirPairInfo = " discovered a pair of vials, why not try them? +" + EXTRA_PERCENTAGE + "% in future points."; 
	public static final int EXTRA_POINTS = 50;
	public static final String goldenPairInfo = " discovered a pair of shiny rocks, wonders if they are worth something: +" + EXTRA_POINTS + " points.";
	public static final String nightmareInfo = " is having a dreadful nightmare: will miss next turn.";
	public static final int LESS_PERCENTAGE = -20;
	public static final String poisonInfo = " discovered a nasty looking potion ...and drank it. Poison everywhere: " + LESS_PERCENTAGE + "% in future points.";
	public static final String shieldPairInfo = " discovered a pair of interesting pieces of metal. Might be useful: +Shield.";
	public static final int STEAL = 10;
	public static final String thiefPairInfo = " discovered a pair of picklocks. Playing fair is for losers: steals up to " + STEAL + " points form the other players.";
			
	
	@BeforeAll
	public static void constructorTest() { 
		p1 = new Player("Marta", new HumanStrategy());
		c1 = new BasicCard("watermelon", "basic"); 
		c2 = new BombCard();
		c3 = new DeathSkullCard(); 
		c4 = new ElixirPair(); 
		c5 = new GoldenPair();
		c6 = new NightmareCard();
		c7 = new PoisonCard(); 
		c8 = new ShieldPair(); 
		c9 = new ThiefPair(); 
	}

	
	
	@Test
	void infoTest() {
		
		
		String s1 = c1.activate_pair().info(); 
		assertEquals(s1, basicInfo);
		
		String s2 = c2.activate_pair().info(); 
		assertEquals(s2, bombInfo);
		
		String s3 = c3.activate_pair().info(); 
		assertEquals(s3, deathSkullInfo);
		
		String s4 = c4.activate_pair().info(); 
		assertEquals(s4, elixirPairInfo);
		
		String s5 = c5.activate_pair().info(); 
		assertEquals(s5, goldenPairInfo);
		
		String s6 = c6.activate_pair().info(); 
		assertEquals(s6, nightmareInfo);
		
		String s7 = c7.activate_pair().info(); 
		assertEquals(s7, poisonInfo);
		
		String s8 = c8.activate_pair().info(); 
		assertEquals(s8, shieldPairInfo);
		
		String s9 = c9.activate_pair().info(); 
		assertEquals(s9, thiefPairInfo);
	}
	
	

}
