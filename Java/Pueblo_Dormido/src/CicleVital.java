import java.util.ArrayList;
/**
 * La interfaz CicleVital define el ciclo vital de un ser vivo en el pueblo Dormit.
 * Contiene métodos para la reproducción y el envejecimiento de los habitantes del pueblo.
 */
public interface CicleVital {
    // Constantes para representar los límites de reproducción y vitalidad máxima
	int NATALITAT_MAXIMA = 1;
    int VITALITAT_MAXIMA = 2;

    /**
     * Método para reproducir nuevos individuos en el pueblo.
     * 
     * @param ciutadans La lista de ciudadanos en el pueblo.
     */
    void reproduir(ArrayList<Ciutada> ciutadans);
    
    /**
     * Método para hacer que los ciudadanos envejezcan un año.
     * 
     * @param ciutadans La lista de ciudadanos en el pueblo.
     */
    void envellir(ArrayList<Ciutada> ciutadans);
}
