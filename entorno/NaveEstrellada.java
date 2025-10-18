// Archivo: entorno/NaveEstrellada.java
package entorno;
import player.Jugador;
import objetos.Item;
import objetos.ItemTipo;
import java.util.EnumSet;
import java.util.Random;
import java.util.Scanner;

public class NaveEstrellada extends Zona {
    private boolean moduloEncontrado; 
    private Random rand;
    private int accionesSinTraje;

    public NaveEstrellada() {
        super("Nave Estrellada", 0, 0, EnumSet.noneOf(ItemTipo.class)); 
        this.moduloEncontrado = false;
        this.rand = new Random();
        this.accionesSinTraje = 0;
    }
    public void recolectar(Jugador jugador, Scanner scan) {
        System.out.println("Explorando en Zona Profunda. ¡Cuidado con la presión!");
        

    }

    @Override
    public void explorar(Jugador jugador) {
        System.out.println("🚀 Explorando el interior de la Nave Estrellada...");

        // 1️⃣ No hay consumo de oxígeno
        // 2️⃣ Sofocación: sin traje térmico, máximo 1 acción
        if (!jugador.isTrajeTermico()) {
            accionesSinTraje++;
            if (accionesSinTraje > 1) {
                System.out.println("🥵 El calor extremo te supera... ¡Has sucumbido a la sofocación!");
                jugador.derrotaPorSofocacion(); // nuevo método en Jugador
                return;
            }
        }

        // 3️⃣ Posibilidad de hallar el módulo de profundidad (único, 25%)
        if (!moduloEncontrado && rand.nextDouble() < 0.25) {
            System.out.println("⚙️ ¡Has encontrado el MODULO_PROFUNDIDAD!");
            jugador.agregarItem(new Item(ItemTipo.MODULO_PROFUNDIDAD, 1));
            moduloEncontrado = true;
            return;
        }

        // 4️⃣ Recompensas dependiendo del traje
        if (jugador.isTrajeTermico()) {
            // Puede recolectar recursos
            ItemTipo[] recursos = {ItemTipo.Cables, ItemTipo.Piezas_Metal};
            ItemTipo recurso = recursos[rand.nextInt(recursos.length)];
            jugador.agregarItem(new Item(recurso, 1 + rand.nextInt(2)));
            System.out.println("🔩 Has recolectado: " + recurso);
        } else {
            System.out.println("No encuentras nada más entre los restos de la nave...");
        }
    }

    

   
}

