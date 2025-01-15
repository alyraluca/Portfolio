import java.util.ArrayList;
/**
 * La clase Vampir representa a un ciudadano vampiro en el pueblo Dormit.
 * Extiende la clase Ciutada e implementa las interfaces Vulnerable y Batalla.
 */
public class Vampir extends Ciutada implements Vulnerable, Batalla {

	// Atributs de classe
    private static int totalVampirs = 0;
    private static int ultimVampir = 0;
    
    // Atributs d'instància
    public String vulnerable;
    
    /**
     * Constructor de la clase Vampir.
     */
	public Vampir() {
        // Llama al constructor de la clase padre con un nombre único para el vampiro
		super("VAMPIR" + ++ultimVampir);
        // Asigna la vulnerabilidad del vampiro, que es vulnerable a los lobos
		this.vulnerable = Vulnerable.LLOP;
        // Incrementa el contador de vampiros
        totalVampirs++;
	}

	/**
     * Método para obtener el tipo de vulnerabilidad del vampiro.
     * 
     * @return El tipo de vulnerabilidad del vampiro.
     */
	@Override
    public String getVulnerable() {
		return vulnerable;
    }
	
	/**
     * Método para obtener el número total de vampiros en el pueblo.
     * 
     * @return El número total de vampiros.
     */
    public static int getPoblacio() {
        return totalVampirs;
    }

    /**
     * Método para actualizar el número total de vampiros.
     * 
     * @param numero El nuevo número total de vampiros.
     */
    public static void setPoblacio(int numero) {
    	totalVampirs = numero;
    }
    
    /**
     * Método para simular un combate entre un vampiro y otro ciudadano.
     * 
     * @param oponent El oponente con el que el vampiro va a combatir.
     * @return El perdedor del combate, si lo hay.
     */
	@Override
	public Ciutada combat(Ciutada oponent) {
		if (oponent instanceof Huma) {
	        // Combate entre vampiro y humano, el humano pierde
			Vampir vampirNou = new Vampir();
			System.out.println(getNom() + " ataca " + oponent.getNom() + " guanya i el converteix en el seu vampir personal " + vampirNou.getNom());
			totalVampirs++;;
			Huma.setPoblacio(Huma.getPoblacio()-1);
			//Ciutada.setPoblacio(Ciutada.getPoblacio() - 1);
	        return oponent;
	    } else if (oponent instanceof Llop) {
	        // Combate entre vampiro y lobo, el vampiro pierde
	    	System.out.println(getNom() + " ataca " + oponent.getNom() + " pero mor a la llum del sol ");
	    	totalVampirs--;
	    	//Ciutada.setPoblacio(Ciutada.getPoblacio() - 1);
	        return null;
	    }
	    // En otros casos, no hay perdedor si lucha un vampiro con otro vampiro
		return null;
	}


	/**
     * Método que se ejecuta cuando un vampiro muere.
     * 
     * @param ciutadans La lista de ciudadanos en el pueblo.
     */
    @Override
    public void morir(ArrayList<Ciutada> ciutadans) {             
        // Disminuye la población
        Ciutada.setPoblacio(Ciutada.getPoblacio() - 1);
        // Elimina al vampir de la lista de ciudadanos
        ciutadans.remove(this);
        totalVampirs--;
    }
    
    /**
     * Método para obtener una representación en cadena del vampiro,
     * incluyendo su nombre y su vulnerabilidad.
     * 
     * @return Representación en cadena del vampiro.
     */
	@Override
	public String toString() {
		return "Nom del ciutada: " + getNom() + ", Vulnerable: " + getVulnerable();
	}

}
