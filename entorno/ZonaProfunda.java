package entorno;

import player.Jugador;
import objetos.NaveExploradora;
import objetos.ItemTipo;
import java.util.EnumSet;
import java.util.Scanner;


/**
 * Zona Profunda (200-999 m).
 */
public class ZonaProfunda extends Zona {
    
    private int presion; 

    public ZonaProfunda() {
        super("Zona Profunda", 200, 999, EnumSet.of(ItemTipo.Plata, ItemTipo.Oro, ItemTipo.Acero, ItemTipo.Diamante, ItemTipo.Magnetita));
        this.presion = 10;
        this.ZonaSiguiente = null;
    }
    public void recolectar(Jugador jugador, Scanner scan) {
        System.out.println("Explorando en Zona Profunda. ¡Cuidado con la presión!");
        

    }
    @Override
    public void explorar(Jugador jugador) {
        System.out.println("Explorando en Zona Profunda. ¡Cuidado con la presión!");
        

    }


    public double calcularPresion(Jugador jugador) {
        if (jugador.getMejoraTanque()) {
            return 0;
        }
        // Calcula profundidad normalizada d (0 a 1)
        double d = calcularProfundidadNormalizada(jugador.getProfundidadActual());
        return presion + 6 * d; // fórmula de presión para ZonaProfunda
    }

    //Getter
    public int getPresion(){ 
        return presion; 
    }

    
}