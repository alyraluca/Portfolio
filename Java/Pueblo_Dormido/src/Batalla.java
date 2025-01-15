/**
 * La interfaz Batalla representa la capacidad de un ciudadano para participar en combates.
 * Define un método para llevar a cabo un combate entre dos ciudadanos.
 */
public interface Batalla {
	/**
     * Método para realizar un combate entre dos seres vivos.
     * 
     * @param oponente El oponente contra el que se va a combatir.
     * @return El resultado del combate, que puede ser el oponente derrotado o nulo.
     */
	Ciutada combat(Ciutada oponent);
}
