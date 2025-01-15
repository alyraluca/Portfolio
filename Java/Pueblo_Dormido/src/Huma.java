import java.util.ArrayList;
import java.util.Random;
/**
 * La clase Huma representa a un ciudadano humano en el pueblo Dormit.
 * Extiende la clase Ciutada e implementa las interfaces Vulnerable, Batalla y CicleVital.
 */
public class Huma extends Ciutada implements Vulnerable, Batalla, CicleVital {
	
	//Atributos de classe
	private static int totalHumans = 0;
    private static int ultimHuma = 0;
    private static final Random ALEATORI = new Random();
    
    //Atributos de instancia
    private String vulnerable;
    private int vida;
    private static final int POBLACIO_MAXIMA = 20;
    
    /**
     * La clase Huma representa a un ciudadano humano en el pueblo Dormit.
     * Extiende la clase Ciutada e implementa las interfaces Vulnerable, Batalla y CicleVital.
     */
    public Huma() {
        super("HUMA" + (++ultimHuma));
        totalHumans++;
        this.vulnerable = Vulnerable.VAMPIR;
        vida = ALEATORI.nextInt(VITALITAT_MAXIMA) + 1;
    }
    
    /**
     * Método para obtener el número total de humanos en el pueblo.
     * 
     * @return El número total de humanos.
     */
    public static int getPoblacio() {
        return totalHumans;
    }

    /**
     * Método para actualizar el número total de humanos.
     * 
     * @param numero El nuevo número total de humanos.
     */
    public static void setPoblacio(int numero) {
        totalHumans = numero;
    }

    /**
     * Método para simular un combate entre un humano y otro ciudadano.
     * 
     * @param oponent El oponente con el que el humano va a combatir.
     * @return El perdedor del combate, si lo hay.
     */
    @Override
    public Ciutada combat(Ciutada oponent) {
        if (oponent instanceof Vampir) {
            // Combate entre humano y vampiro, el humano pierde
        	Vampir vampirNou = new Vampir();
            System.out.println(getNom() + " ataca " + oponent.getNom() + " pero mor i es converteix en " + vampirNou.getNom());
            totalHumans--;  
            //Ciutada.setPoblacio(Ciutada.getPoblacio() - 1);
            Vampir.setPoblacio(Vampir.getPoblacio()+1);
            return null;
        } else if (oponent instanceof Llop) {
            // Combate entre humano y lobo, el humano gana
            System.out.println(getNom() + " ataca " + oponent.getNom() + " guanya i ven la seua pell per a fer abrics.");
            Llop.setPoblacio(Llop.getPoblacio() - 1);
            //Ciutada.setPoblacio(Ciutada.getPoblacio() - 1);
            return oponent;            
        }

        // En otros casos, un humano lucha con otro humano
        System.out.println();
        return null;
    } 
   
    /**
     * Método para reproducir a los humanos, produciendo un número aleatorio de hijos
     * utilitzant la NATALITAT_MAXIMA.
     * 
     * @param ciutadans La lista de ciudadanos en el pueblo.
     */
    @Override
    public void reproduir(ArrayList<Ciutada> ciutadans) {
        if (totalHumans < POBLACIO_MAXIMA) {            
                Huma fill = new Huma();
                ciutadans.add(fill);
                Ciutada.setPoblacio(Ciutada.getPoblacio()+1);
                totalHumans++;
                System.out.println("Estem de enhorabona! " + getNom() + " ha tingut un fill: " + fill.getNom());
            
        } else {
            System.out.println("No s'ha pogut reproduir, límit de població màxima assolit.");
        }
    }

    
    /**
     * Método que se ejecuta cuando un humano muere.
     * 
     * @param ciutadans La lista de ciudadanos en el pueblo.
     */
    @Override
    public void morir(ArrayList<Ciutada> ciutadans) {       
        
        // Disminuye la población
        Ciutada.setPoblacio(Ciutada.getPoblacio()-1);

        // Elimina al humano de la lista de ciudadanos
        ciutadans.remove(this);
        //Disminuye la población de humanos.
        totalHumans--;
    }
    /**
     * Método para envejecer a los humanos, reduciendo su vida.
     * 
     * @param ciutadans La lista de ciudadanos en el pueblo.
     */
    @Override
    public void envellir(ArrayList<Ciutada> ciutadans) {
        if (vida > 1) {
            vida--; // Reduce la vida del humano en uno
        } else{
            // Si la vida llega a cero, el humano muere
        	System.out.println(getNom() + " ha mort de vell");
            morir(ciutadans);
        }
    }
    /**
     * Método para obtener el tipo de vulnerabilidad del humano.
     * 
     * @return El tipo de vulnerabilidad del humano.
     */
	@Override
	public String getVulnerable() {
		return vulnerable;
	}
    
	/**
     * Método para obtener una representación en cadena del humano,
     * incluyendo su nombre, su vida y su vulnerabilidad.
     * 
     * @return Representación en cadena del humano.
     */
	@Override
	public String toString() {
	    return "Nom del ciutada: " + getNom() + ", Vida: " + vida + ", Vulnerable: " + getVulnerable();
	}

}






















