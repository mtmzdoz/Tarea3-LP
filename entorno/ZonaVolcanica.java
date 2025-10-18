package entorno;

import player.Jugador;
import objetos.ItemTipo;
import java.util.EnumSet;
import java.util.Scanner;

public class ZonaVolcanica extends Zona {
    private boolean planoEncontrado; 
    

    public ZonaVolcanica() {
        super("Zona Volcánica", 1000, 1500, EnumSet.of(ItemTipo.Titanio, ItemTipo.Sulfuro, ItemTipo.Uranio));
        this.planoEncontrado = false;
    }

    public void recolectar(Jugador jugador, Scanner scan) {
        System.out.println("Explorando en Zona Profunda. ¡Cuidado con la presión!");
        

    }
    @Override
    public void explorar(Jugador jugador) {
        System.out.println("Explorando en Zona Volcánica. ¡Es muy caliente!");
    }
}