package entorno;

import player.Jugador;
import objetos.ItemTipo;
import java.util.EnumSet;

/**
 * Zona Profunda (200-999 m).
 */
public class ZonaProfunda extends Zona {
    
    private int presion; 

    public ZonaProfunda() {
        super("Zona Profunda", 200, 999, EnumSet.of(ItemTipo.Plata, ItemTipo.Oro, ItemTipo.Acero, ItemTipo.Diamante, ItemTipo.Magnetita));
        this.presion = 10;
    }
    @Override
    public void explorar(Jugador jugador) {
        System.out.println("Explorando en Zona Profunda. ¡Cuidado con la presión!");
    }
    //Getter
    public int getPresion() { return presion; }
}