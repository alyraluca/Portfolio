import java.util.ArrayList;
import java.util.Random;

/**
 * La clase Llop representa a un ciudadano lobo en el pueblo Dormit.
 * Extiende la clase Ciutada e implementa las interfaces Vulnerable, Batalla y CicleVital.
 */
public class Llop extends Ciutada implements Vulnerable, Batalla, CicleVital{

	 //Atributos de classe
	 private static int totalLlops = 0;
	 private static int ultimLlop = 0;
	 private static final Random ALEATORI = new Random();
	 //Atributos de instancia 
	 private String vulnerable;
	 private int vida;
	 private static final int POBLACIO_MAXIMA = 20;
	 
	 /**
	  * Constructor de la clase Llop.
	  */
	 public Llop() {
	        // Llama al constructor de la clase padre con un nombre único para el lobo
	        super("LLOP" + (++ultimLlop));
	        // Establece la vulnerabilidad del lobo (vulnerable a los humanos)
	        this.vulnerable = Vulnerable.HUMA;
	        // Asigna una vida aleatoria al lobo
	        vida = ALEATORI.nextInt(VITALITAT_MAXIMA * 2) + 1;
	        // Incrementa el contador de lobboss
	        totalLlops++;
	    }
	 /**
	     * Método para obtener el tipo de vulnerabilidad del lobo.
	     * 
	     * @return El tipo de vulnerabilidad del lobo.
	     */
	@Override
	public String getVulnerable() {
			return vulnerable;
		}
	
	/**
     * Método para obtener el número total de lobos en el pueblo.
     * 
     * @return El número total de lobos.
     */
	public static int getPoblacio() {
	        return totalLlops;
	    }

	/**
     * Método para actualizar el número total de lobos.
     * 
     * @param numero El nuevo número total de lobos.
     */
    public static void setPoblacio(int numero) {
    	totalLlops = numero;
    }
    
    /**
     * Método para simular un combate entre un lobo y otro ciudadano.
     * 
     * @param oponent El oponente con el que el lobo va a combatir.
     * @return El perdedor del combate, si lo hay.
     */
    @Override
    public Ciutada combat(Ciutada oponent) {
        if (oponent instanceof Huma) {
            // Combate entre lobo y humano, el lobo pierde
            System.out.println(getNom() + " ataca " + oponent.getNom() + " pero mor amb una bala de plata.");
            totalLlops--;
            //Ciutada.setPoblacio(Ciutada.getPoblacio() - 1);
            return null;
        } else if (oponent instanceof Vampir) {
            //Combate entre lobo y vampiro, el lobo gana
            System.out.println(getNom() + " ataca " + oponent.getNom() + " guanya y es fa un collar amb els seus ullals.");
            //Ciutada.setPoblacio(Ciutada.getPoblacio()-1);
            Vampir.setPoblacio(Vampir.getPoblacio()-1);
            return oponent;
        }
        // En otros casos, no hay perdedor si lucha un lobo con otro lobo
        return null;
    }

    /**
     * Método para reproducir a los lobos, produciendo un número aleatorio de crías
     * utilitzant el doble de la NATALITAT_MAXIMA
     * 
     * @param ciutadans La lista de ciudadanos en el pueblo.
     */
    @Override
    public void reproduir(ArrayList<Ciutada> ciutadans) {
        int numCadells = ALEATORI.nextInt(NATALITAT_MAXIMA * 2 + 1);
        if (totalLlops < POBLACIO_MAXIMA) {
            for (int i = 0; i < numCadells; i++) {
                Llop cadells = new Llop();
                //Ciutada.setPoblacio(Ciutada.getPoblacio() + 1);
                ciutadans.add(cadells);
                totalLlops++;
                Ciutada.setPoblacio(Ciutada.getPoblacio()+1);
                System.out.println("Estem de enhorabona! " + getNom() + " ha tingut un cadell: " + cadells.getNom());
            }
        } else {
            System.out.println("No s'ha pogut reproduir, límit de població màxima assolit.");
        }
    }
    
   
    /**
     * Método que se ejecuta cuando un lobo muere.
     * 
     * @param ciutadans La lista de ciudadanos en el pueblo.
     */
    @Override
    public void morir(ArrayList<Ciutada> ciutadans) {
    	// Disminuye la población
        Ciutada.setPoblacio(Ciutada.getPoblacio()-1);
        // Elimina al humano de la lista de ciudadanos
        ciutadans.remove(this);
        //Disminuye la población de llops.
        totalLlops--;
    }
    
    /**
     * Método para envejecer a los lobos, reduciendo su vida.
     * Los lobos envejecen el doble de rápido que los humanos.
     * 
     * @param ciutadans La lista de ciudadanos en el pueblo.
     */
    @Override
    public void envellir(ArrayList<Ciutada> ciutadans) {
        if (vida > 2) {
            vida -= 2; // Los lobos envejecen el doble de rápido
        } else {
        	// El lobo muere de vejez
        	System.out.println(getNom() + " ha mort de vell");
            morir(ciutadans);
        }
    }
    /**
     * Método para obtener una representación en cadena del lobo,
     * incluyendo su nombre, su vida y su vulnerabilidad.
     * 
     * @return Representación en cadena del lobo.
     */
  	@Override
  	public String toString() {
  	    return "Nom del ciutada: " + getNom() + ", Vida: " + vida + ", Vulnerable: " + getVulnerable();
  	}
  	
}











