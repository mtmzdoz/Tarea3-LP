// Archivo: entorno/NaveEstrellada.java
package entorno;
import player.Jugador;
import objetos.ItemTipo;
import java.util.EnumSet;

public class NaveEstrellada extends Zona {
    private boolean moduloEncontrado; 

    public NaveEstrellada() {
        super("Nave Estrellada", 0, 0, EnumSet.noneOf(ItemTipo.class)); 
        this.moduloEncontrado = false;
    }
    @Override
    public void explorar(Jugador jugador) {
        System.out.println("Explorando el interior de la Nave Estrellada...");
    }

   
}

