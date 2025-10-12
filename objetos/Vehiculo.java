package objetos;

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

    // El método es CONCRETO, no necesita ser implementado por las subclases (a menos que quieran).
    public void transferirObjetos(List<Item> origen, List<Item> destino) {
        // Implementación se hará más adelante.
    }
    //Getter
    public List<Item> getBodega() { return bodega; }
}