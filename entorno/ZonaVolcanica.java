package entorno;

import player.Jugador;
import objetos.Item;
import objetos.ItemTipo;
import java.util.EnumSet;
import java.util.Random;
import java.util.Scanner;

public class ZonaVolcanica extends Zona {
    private boolean planoEncontrado; 
    private int presion;
    private Random rand;
    

    public ZonaVolcanica() {
        super("Zona Volcánica", 1000, 1500, EnumSet.of(ItemTipo.Titanio, ItemTipo.Sulfuro, ItemTipo.Uranio));
        this.planoEncontrado = false;
        this.presion= 10000;
        this.rand= new Random();
    }

    public void recolectar(Jugador jugador, Scanner scan) {

        if (rand.nextDouble() < 0.20) {
            jugador.perdidaConciencia();
            return;
        }

        System.out.println("⛏️ Recolectando recursos en la Zona Profunda...");
        int z = jugador.getProfundidadActual();
        double d = calcularProfundidadNormalizada(z);
        double pres = jugador.getMejoraTanque() ? 0 : calcularPresion(jugador); //Ya que verificamos antes de bajar y si se puede bajar es porque tenemos la mejora piezas tanque

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
        ItemTipo[] recursos = { ItemTipo.Titanio, ItemTipo.Sulfuro, ItemTipo.Uranio};

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

       
        int n_min = 3, n_max = 8;
        int cantidad = Math.max(1, (int) Math.floor(n_min + (n_max - n_min) * d));

        // 6️⃣ Entregar recurso
        jugador.agregarItem(new Item(recursoElegido, cantidad));
        System.out.println("🔹 Has recolectado: " + cantidad + " x " + recursoElegido);
    }

    @Override
    public void explorar(Jugador jugador) {
        System.out.println("🌋 Explorando la Zona Volcánica... ");

        if (rand.nextDouble() < 0.20) {
            jugador.perdidaConciencia();
            return;
        }

        // 2️⃣ Calcular costo de oxígeno
        double d = calcularProfundidadNormalizada(jugador.getProfundidadActual());
        double pres = 0; //Ya que verificamos antes de bajar y si se puede bajar es porque tenemos la mejora piezas tanque 

        int Cexplorar = (int) Math.ceil(12 + 10 * d + pres);
        jugador.getTanqueOxigeno().consumirO2(Cexplorar);
        System.out.println("💨 Consumo de O₂ por explorar: " + Cexplorar);


        if (jugador.getTanqueOxigeno().getOxigenoRestante() <= 0) {
            jugador.derrotaPorOxigeno();
            return;
        }

        otorgarRecompensa(jugador, d);
    }

    private void otorgarRecompensa(Jugador jugador, double d) {

        if (!planoEncontrado && rand.nextDouble() < 0.15) {
            System.out.println("📜 ¡Has encontrado el PLANO_NAVE! Este es el objeto final del juego.");
            jugador.agregarItem(new Item(ItemTipo.PLANO_NAVE,1));
            planoEncontrado = true;
            return;
        }else{
            ItemTipo[] recursos = {ItemTipo.Titanio, ItemTipo.Sulfuro, ItemTipo.Uranio};
            ItemTipo recurso = recursos[rand.nextInt(recursos.length)];

            int n_min = 2; // menor cantidad, por dificultad
            int cantidad = Math.max(1, (int) Math.floor(n_min * d));

            jugador.agregarItem(new Item(recurso, cantidad));
            System.out.println("🔹 Has recolectado: " + cantidad + " x " + recurso);
        }
    }

    public double calcularPresion(Jugador jugador) {
        if (jugador.getMejoraTanque()) {
            return 0;
        }
        return presion; 
    }

    
}