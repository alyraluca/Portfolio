import java.util.ArrayList;
/**
 * La clase abstracta Ciutada representa a un ciudadano en el pueblo Dormit.
 * Implementa las interfaces Vulnerable y Batalla.
 * Es una clase abstracta ya que no se puede instanciar directamente.
 */
public abstract class Ciutada implements Vulnerable, Batalla {

	//Atributos
	private static int poblacio = 0;
    private String nom;
    
    //Constructor
    public Ciutada(String nom) {
        this.nom = nom;
        poblacio++;
    }
    
    /**
     * Método para obtener la población total de ciudadanos en el pueblo.
     * 
     * @return El número total de ciudadanos.
     */
	public static int getPoblacio() {
		return poblacio;
	}
	/**
     * Método para obtener el nombre del ciudadano.
     * 
     * @return El nombre del ciudadano.
     */
	public String getNom() {
		return nom;
	}
	/**
     * Método para establecer la población total de ciudadanos en el pueblo.
     * 
     * @param poblacio La nueva población total de ciudadanos.
     */
	public static void setPoblacio(int poblacio) {
		Ciutada.poblacio = poblacio;
	}
	/**
     * Método para establecer el nombre del ciudadano.
     * 
     * @param nom El nuevo nombre del ciudadano.
     */
	public void setNom(String nom) {
		this.nom = nom;
	}
    
	/**
     * Método para obtener una representación en cadena del ciudadano, incluyendo su nombre.
     * 
     * @return Representación en cadena del ciudadano.
     */
	public String toString() {
        return "Nom del ciutada: " + nom;
    }
	
	/**
     * Método para imprimir los ciudadanos y mostrar las poblaciones totales.
     * 
     * @param ciutadans La lista de ciudadanos en el pueblo.
     */
	public static void censar(ArrayList<Ciutada> ciutadans) {
        for (Ciutada ciutada : ciutadans) {
            System.out.println(ciutada);
        }
        poblacionsTotals();
    }
	/**
     * Método para imprimir las poblaciones totales de humanos, lobos y vampiros en el pueblo.
     */
	public static void poblacionsTotals() {
		System.out.println("Actualment hi ha un cens de: " + getPoblacio());
		System.out.println(Huma.getPoblacio() + " humans, " + Llop.getPoblacio() + " llops i " + Vampir.getPoblacio() + " vampirs");
	}
	/**
     * Método abstracto que indica el comportamiento cuando un ciudadano muere.
     * 
     * @param ciutadans La lista de ciudadanos en el pueblo.
     */
	public abstract void morir(ArrayList<Ciutada> ciutadans);

	
	
    
    
}
