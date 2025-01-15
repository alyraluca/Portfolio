/**
 * La interfaz Vulnerable representa la vulnerabilidad de un ciudadano en el contexto del juego.
 * Contiene métodos para obtener el tipo de vulnerabilidad.
 */
public interface Vulnerable {
	// Constantes para representar los tipos de vulnerabilidad
    String VAMPIR = "Vampir";
    String HUMA = "Huma";
    String LLOP = "Llop";
    
    /**
     * Método para obtener el tipo de vulnerabilidad de un ciudadano.
     * 
     * @return El tipo de vulnerabilidad.
     */
	String getVulnerable();
	
}
