import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * La clase ElPobleDormit simula la vida en el pueblo Dormit.
 */
public class ElPobleDormit {
	
	//Establim una població mínima i màxima que no podrà ser canviada
	private static final int POBLACIO_MINIMA = 10;
    private static final int POBLACIO_MAXIMA = 30;
    private static final Random ALEATORI = new Random();
    //Creem una llista de ciutadans
    private static final ArrayList<Ciutada> ciutadans = new ArrayList<>();

    /**
     * Método principal que inicia la simulación del pueblo Dormit.
     * 
     */
	public static void main(String[] args) {

        System.out.println("Benvingut al Poble Dormit!");
        //Generarà una població aleatòria cada vegada que executa el joc
        generarPoblacioAleatoria();
        //Informar al jugador dels ciutadans que hi ha en el joc
        System.out.println("Població inicial: ");
        Ciutada.censar(ciutadans);
        
        //Mostrar menu
        boolean continuar = true;
        while (continuar) {
            if (verificarPoblacio()) {
                System.out.println("Tots els ciutadans són del mateix tipus. Fi del joc.");
                return;
            }
            continuar = mostrarMenu(continuar);            
        }

	}
	/**
     * Muestra el menú de opciones y permite al jugador seleccionar una opción.
     * 
     * @return true si el jugador desea continuar jugando, false si quiere salir del juego.
     */ 
	private static boolean mostrarMenu(boolean continuar) {
		Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
	        System.out.println("-------------------------------------------------");
	        System.out.println("Menú:");
	        System.out.println("1. Cens actual");
	        System.out.println("2. Passant un any");
	        System.out.println("3. Eixir del programa");
	        System.out.print("Selecciona una opció: ");
	        
	        opcion = scanner.nextInt();
	        scanner.nextLine();
	        try {	         	       
	        switch (opcion) {
	            case 1:
	                System.out.println("Cens actual:");
	                Ciutada.censar(ciutadans);
	                break;
	            case 2:
	                try {
	                	System.out.println("Passant un any...");
	                    passarAny();
	                    Ciutada.poblacionsTotals();
	                } catch (Exception e) {
	                    System.out.println("Error al passar l'any: " + e.getMessage());
	                }
	                break;
	            case 3:
	                System.out.println("Eixint del programa...");
	                return false; // El usuario quiere salir del juego
	            default:
	                System.out.println("Opció no vàlida. Escull una opció entre 1 i 3.");
	        }
		        } catch (Exception e) {
		            System.out.println("Excepció: " + e.getMessage());
		            return false; // Salir del programa en caso de excepción
		        } 
	        //scanner.close();
        } while (true);
        	    
	}
	
	/**
     * Simula el paso de un año en el pueblo Dormit.
     * 
     * @throws Exception Si ocurre un error al pasar el año.
     */
	private static void passarAny() throws Exception {
		try {
		//Si no existeix cap ciutadà llançarà una excepció
		if (ciutadans.isEmpty()) {
	        throw new IllegalStateException("No hi ha ciutadans disponibles.");
	    }
		// Crear una copia de la lista ciutadans pera poder iterar la lista 
		// y modificarla a la vez sin que nos de errro de "ConcurrentModificationException"
        ArrayList<Ciutada> copiaCiutadans = new ArrayList<>(ciutadans);
		for (Ciutada ciutada : copiaCiutadans) {
	        try {
	        	if (ciutada instanceof CicleVital) {
	        		actualitzarEdad(ciutada);
	        		realitzarAccio(ciutada, obtenirOponentAleatori(ciutadans.indexOf(ciutada)));
	        	}
	        } catch (Exception e) {
	            throw new Exception("Error al passar l'any: " + e.getMessage(), e);
	        }
		}
	    }catch (Exception e) {
	        System.out.println("Excepció al passar l'any: " + e.getMessage());
	        e.printStackTrace();
	    }
	}	
	/**
     * Realiza la acción correspondiente para el ciudadano dado y su oponente.
     * 
     * @param ciutada1 El primer ciudadano.
     * @param ciutada2 El segundo ciudadano.
     */
	private static void realitzarAccio(Ciutada ciutada1, Ciutada ciutada2) {
		if (ciutada1 == null || ciutada2 == null) {
	        throw new IllegalArgumentException("Els oponents no poden ser null.");
	    }
		if (ciutada1.getClass().equals(ciutada2.getClass())) {
	        procrear(ciutada1); // Si los ciutadans son del mismo tipo, se reproducen
	    } else {
	        combatre(ciutada1, ciutada2); // Si son de tipos diferentes, se enfrentan en combate
	    }
		
	}
	
	/**
     * Realiza un combate entre dos ciudadanos.
     * 
     * @param oponent1 El primer oponente.
     * @param oponent2 El segundo oponente.
     */
	private static void combatre(Ciutada oponent1, Ciutada oponent2) {
		Ciutada perdedor = oponent1.combat(oponent2);
	    if (perdedor != null) {
	        // Si hay un perdedor, se elimina de la lista de ciutadans
	        //ciutadans.remove(perdedor);
	        //Ciutada.setPoblacio(Ciutada.getPoblacio()-1);
	        // Si el perdedor es un humano y el ganador es un vampiro, se convierte en vampiro	        
	        Ciutada.setPoblacio(Ciutada.getPoblacio()-1);
	        ciutadans.remove(perdedor);
	    }
		
	}
	/**
     * Obtiene un oponente aleatorio para un ciudadano en la lista de ciudadanos.
     * 
     * @param actual El índice del ciudadano actual en la lista de ciudadanos.
     * @return Un ciudadano aleatorio que no sea el mismo que el actual.
     */
	private static Ciutada obtenirOponentAleatori(int actual) {
		if (ciutadans.size() <= 1) {
	        throw new IllegalStateException("No hi ha cap oponent disponible.");
	    }

	    Random random = new Random();
	    int index;
	    Ciutada oponent; 
	    do {
	        index = random.nextInt(ciutadans.size());
	        oponent = ciutadans.get(index);
	    } while (index == actual || !ciutadans.contains(oponent));

	    return oponent;
	}
	/**
     * Actualiza la edad del ciudadano si implementa la interfaz CicleVital.
     * 
     * @param ciutada El ciudadano cuya edad se va a actualizar.
     */
	private static void actualitzarEdad(Ciutada ciutada) {
		if (ciutada instanceof CicleVital) {
	        ((CicleVital) ciutada).envellir(ciutadans);
	    } else {
	        System.out.println("No se puede actualizar la edad de este ciutada.");
	    }
		
	}
	/**
     * Realiza la reproducción de un ciudadano si implementa la interfaz CicleVital.
     * 
     * @param amant1 El ciudadano que va a reproducirse.
     */
	private static void procrear(Ciutada amant1) {
	    if (amant1 instanceof CicleVital && ciutadans.size()< POBLACIO_MAXIMA) {
	        ((CicleVital) amant1).reproduir(ciutadans);
	    } else if (ciutadans.size() > POBLACIO_MAXIMA){
	        System.out.println("No s'ha pogut reproduir, límit de població màxima assolit.");
	    }
	}


	/**
     * Genera una población aleatoria de ciudadanos entre el mínimo y máximo establecidos en el sistema.
     */
	private static void generarPoblacioAleatoria() {
		int poblacioInicial = ALEATORI.nextInt(POBLACIO_MAXIMA - POBLACIO_MINIMA + 1) + POBLACIO_MINIMA;
        for (int i = 0; i < poblacioInicial; i++) {
            ciutadans.add(obtenirCiutadaAleatori());
        }
	}
	/**
     * Crea y retorna un ciudadano aleatorio (humano, lobo o vampiro).
     * 
     * @return Un ciudadano aleatorio.
     */
	private static Ciutada obtenirCiutadaAleatori() {
    	int randomNum = ALEATORI.nextInt(3);
        switch (randomNum) {
            case 0:
                return new Huma();
            case 1:
                return new Llop();
            default:
                return new Vampir();
        }
	}
	/**
     * Verifica si todos los ciudadanos en el pueblo son del mismo tipo.
     * 
     * @return true si todos los ciudadanos son del mismo tipo, false de lo contrario.
     */
	private static boolean verificarPoblacio() {
        Set<String> tipusDiferents = new HashSet<>();
        for (Ciutada ciutada : ciutadans) {
            tipusDiferents.add(ciutada.getClass().getSimpleName());
        }
        if (tipusDiferents.size() == 1) {
            System.out.println("Sols queda un tipus de essers en el poble");
            return true;
        }
        return false;
    }

}

	



