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
        System.out.println("🧰 Intentando recolectar en la Nave Estrellada...");

        double d = calcularProfundidadNormalizada(jugador.getProfundidadActual());
        int n_min = 1;
        int n_max = 4;
        int cantidad = Math.max(1, (int) Math.floor(n_min + (n_max - n_min) * d));

        ItemTipo[] recursos = { ItemTipo.Cables, ItemTipo.Piezas_Metal };
        ItemTipo recurso = recursos[rand.nextInt(recursos.length)];

        jugador.agregarItem(new Item(recurso, cantidad));
        System.out.println("🔩 Has recolectado: " + cantidad + " x " + recurso);

        if (!jugador.isTrajeTermico()) {
            accionesSinTraje++;
            if (accionesSinTraje == 1) {
                // Segunda vez: advertencia y riesgo de muerte
                System.out.println("\n🥵 El calor dentro de la nave es insoportable sin traje térmico...");
                System.out.println("Si vuelves a intentar recolectar, morirás por sofocación.");
                System.out.println("Te recomiendo salir de la nave o crear el traje térmico antes de continuar.");
                accionesSinTraje++; // Marca que ya se advirtió
             
            } else if (accionesSinTraje > 1) {
                // Tercer intento o más → muerte
                System.out.println("💀 Has ignorado el calor... ¡Has sucumbido a la sofocación!");
                jugador.derrotaPorSofocacion();
                return;
            }
        }
        
    }

    


    @Override
    public void explorar(Jugador jugador) {
        System.out.println("🚀 Explorando el interior de la Nave Estrellada...");

        // 1️⃣ No hay consumo de oxígeno
        // 2️⃣ Sofocación: sin traje térmico, máximo 1 acción
        if (!jugador.isTrajeTermico()) {
            
            accionesSinTraje++;
            if (accionesSinTraje == 1) {
                // Segunda vez: advertencia y riesgo de muerte
                System.out.println("\n🥵 El calor dentro de la nave es insoportable sin traje térmico...");
                System.out.println("Si vuelves a intentar explorar o recolectar, morirás por sofocación.");
                System.out.println("Te recomiendo salir de la nave o crear el traje térmico antes de continuar.");
                accionesSinTraje++; // Marca que ya se advirtió
             
            } else if (accionesSinTraje > 1) {
                // Tercer intento o más → muerte
                System.out.println("💀 Has ignorado el calor... ¡Has sucumbido a la sofocación!");
                jugador.derrotaPorSofocacion();
                return;
            }
        }

        if (!(jugador.getZonaActual() instanceof NaveEstrellada)) {
            return;
        }

        double randVal = rand.nextDouble();
        System.out.println("🎲 Probabilidad generada: " + randVal);


        // 3️⃣ Posibilidad de hallar el módulo de profundidad (único, 25%)
        if (!moduloEncontrado && randVal < 0.25) {
            System.out.println("⚙️ ¡Has encontrado el MODULO_PROFUNDIDAD!");
            jugador.agregarItem(new Item(ItemTipo.MODULO_PROFUNDIDAD, 1));
            moduloEncontrado = true;
            return;
        }

        //Recompensas si no se encontro el traje
        double d = calcularProfundidadNormalizada(jugador.getProfundidadActual());
        int n_min = 1;
        int cantidad = Math.max(1, (int) Math.floor(n_min * d));

        // 🔹 Como d = 0 en esta zona, aseguramos que al menos obtenga 1 recurso
        if (cantidad < 1){ 
            cantidad = 1;
        }
        // 🔹 Seleccionar recurso aleatorio
        ItemTipo[] recursos = { ItemTipo.Cables, ItemTipo.Piezas_Metal };
        ItemTipo recurso = recursos[rand.nextInt(recursos.length)];

        jugador.agregarItem(new Item(recurso, cantidad));
        System.out.println("🔩 Has recolectado: " + cantidad + " x " + recurso);


    }

    public void resetearAccionesSinTraje() {
        this.accionesSinTraje = 0;
    }


   
}

