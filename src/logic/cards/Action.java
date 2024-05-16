package logic.cards;

public interface Action {
	// Las cartas devolverán un objeto que implemente esta interfaz
	public void apply(int player, ActionReceiver list); // Las acciones que han de sufrir los jugadores
	public String info(); // El mensaje con la info de la acción
}
