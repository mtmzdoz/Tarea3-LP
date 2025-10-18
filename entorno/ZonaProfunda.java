package entorno;

import player.Jugador;
import objetos.Item;
import objetos.ItemTipo;
import java.util.EnumSet;
import java.util.Random;
import java.util.Scanner;


/**
 * Zona Profunda (200-999 m).
 */
public class ZonaProfunda extends Zona {
    
    private int presion;
    private Random rand;

    public ZonaProfunda() {
        super("Zona Profunda", 200, 999, EnumSet.of(ItemTipo.Plata, ItemTipo.Oro, ItemTipo.Acero, ItemTipo.Diamante, ItemTipo.Magnetita));
        this.presion = 10;
        this.ZonaSiguiente = null;
        this.rand= new Random();
    }
    public void recolectar(Jugador jugador, Scanner scan) {
       System.out.println("⛏️ Recolectando recursos en la Zona Profunda...");

        int z = jugador.getProfundidadActual();
        double d = calcularProfundidadNormalizada(z);
        double pres = jugador.getMejoraTanque() ? 0 : calcularPresion(jugador);

        // 1️⃣ Calcular costo de oxígeno
        int Crecolectar = (int) Math.ceil(10 + 6 * d + pres);
        jugador.getTanqueOxigeno().consumirO2(Crecolectar);
        System.out.println("💨 Consumo de O₂ por recolectar: " + Crecolectar);

        // 2️⃣ Verificar si murió por falta de O₂
        if (jugador.getTanqueOxigeno().getOxigenoRestante() <= 0) {
            jugador.derrotaPorOxigeno();
            return;
        }

        // 3️⃣ Mostrar opciones de recursos
        System.out.println("\n🌋 ¿Qué recurso deseas recolectar?");
        ItemTipo[] recursos = {ItemTipo.Plata, ItemTipo.Oro, ItemTipo.Acero, ItemTipo.Diamante, ItemTipo.Magnetita};

        for (int i = 0; i < recursos.length; i++) {
            System.out.println((i + 1) + ") " + recursos[i]);
        }
        System.out.print("> ");
        int opcion = scan.nextInt();

        // 4️⃣ Validar elección
        if (opcion < 1 || opcion > recursos.length) {
            System.out.println("❌ Opción inválida. Cancelando recolección.");
            return;
        }

        ItemTipo recursoElegido = recursos[opcion - 1];

        // 5️⃣ Calcular cantidad obtenida con la fórmula n(d) = max(1, floor(n_min * d))
        int n_min = 2; 
        int n_max = 6;
        int cantidad =  Math.max(1, (int) Math.floor(n_min + (n_max - n_min) * d));

        // 6️⃣ Entregar recurso al jugador
        jugador.agregarItem(new Item(recursoElegido, cantidad));
        System.out.println("🔹 Has recolectado: " + cantidad + " x " + recursoElegido);

    }
    @Override
    public void explorar(Jugador jugador) {
        int z = jugador.getProfundidadActual();
        double d = calcularProfundidadNormalizada(z);

        // Presión
        double pres = jugador.getMejoraTanque() ? 0 : calcularPresion(jugador);

        // Calcular costo de oxígeno para explorar
        int Cexplorar = (int) Math.ceil(12 + 10 * d + pres);
        jugador.getTanqueOxigeno().consumirO2(Cexplorar);
        System.out.println("💨 Consumo de O₂ por explorar: " + Cexplorar);

        // Verificar si murió por falta de O₂
        if (jugador.getTanqueOxigeno().getOxigenoRestante() <= 0) {
            jugador.derrotaPorOxigeno();
            return;
        }

        // Si no hay objeto de progresión → recurso aleatorio
        otorgarRecompensa(jugador, d);
        

    }

    private void otorgarRecompensa(Jugador jugador, double d) {
        // Recursos disponibles
        ItemTipo[] recursos = { ItemTipo.Plata, ItemTipo.Oro, ItemTipo.Acero, ItemTipo.Diamante,ItemTipo.Magnetita};

        // Cantidad según la fórmula: max(1, floor(n_min * d))
        int n_min = 2; // más alto que en zonas superficiales
        int cantidad = Math.max(1, (int) Math.floor(n_min * d));

        // Seleccionar recurso aleatorio
        ItemTipo recurso = recursos[rand.nextInt(recursos.length)];

        jugador.agregarItem(new Item(recurso, cantidad));
        System.out.println("🔹 Has obtenido " + cantidad + " x " + recurso);
    }

    public double calcularPresion(Jugador jugador) {
        if (jugador.getMejoraTanque()) {
            return 0;
        }
        // Calcula profundidad normalizada d (0 a 1)
        double d = calcularProfundidadNormalizada(jugador.getProfundidadActual());
        return presion + 6 * d; // fórmula de presión para ZonaProfunda
    }

   
    
}