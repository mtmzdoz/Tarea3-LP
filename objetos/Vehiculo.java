package objetos;

import player.Jugador;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase abstracta base para los vehículos.
 */
public abstract class Vehiculo {
    
    private List<Item> bodega;

    public Vehiculo() {
        this.bodega = new ArrayList<>();
    }

    
    public void transferirObjetos(Jugador jugador) {
        if (jugador.getInventario().isEmpty()) {
            System.out.println("🎒 No tienes objetos que guardar.");
            return;
        }
        for (Item itemJugador : jugador.getInventario()) {
            agregarItem(itemJugador);
        }

        jugador.getInventario().clear();
        System.out.println("✅ Todos tus objetos han sido guardados en la bodega.\n");
         
    }

    //Se reusa en retirar objetos 
    private void agregarItem(Item nuevo) {
        for (Item item : bodega) {
            if (item.getTipo() == nuevo.getTipo()) {
                item.setCantidad(item.getCantidad() + nuevo.getCantidad());
                return;
            }
        }
        bodega.add(new Item(nuevo.getTipo(), nuevo.getCantidad()));
    }
    //Getter
    public List<Item> getBodega() { 
        return bodega; }
}