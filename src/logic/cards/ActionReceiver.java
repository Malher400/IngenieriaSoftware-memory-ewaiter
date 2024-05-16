package logic.cards;

public interface ActionReceiver {
	// Recoge todas las acciones que pueden ejecutar las cartas y que debe implementar el ArrayPlayers
	public void add_points(int turn, int points);
	public void sub_points(int turn, int points);
	public void zero(int p); 
	public void shield(int p); 
	public void steal(int p, int steal);
	public void take_potion(int p, int percentage);
	public void nightmare(int p);
	public boolean is_asleep(int turn);
}
