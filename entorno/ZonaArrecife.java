package entorno;

import player.Jugador;
import objetos.Item;
import objetos.ItemTipo;
import java.util.EnumSet;
import java.util.Random; 
import java.util.Scanner;

/**
 * Zona Arrecife (0-199 m). Implementa explorar.
 */
public class ZonaArrecife extends Zona {
    
    private int piezasTanque;
    private Random rand; 
    
    //Constructor
    public ZonaArrecife() {
        super("Zona Arrecife", 0, 199, EnumSet.of(ItemTipo.Cuarzo, ItemTipo.Silicio, ItemTipo.Cobre));
        this.piezasTanque = 3;
        this.rand = new Random();
        this.ZonaSiguiente = null; 
    }

    @Override
    public void recolectar(Jugador jugador, Scanner scan) {
        int z = jugador.getProfundidadActual();
        double d = calcularProfundidadNormalizada(z);

        // Presión (Arrecife = 0)
        double pres = jugador.getMejoraTanque() ? 0 : 0;

        // 1. Calcular costo de O2
        int Crecolectar = (int) Math.ceil(10 + 6*d + pres);
        jugador.getTanqueOxigeno().consumirO2(Crecolectar);

        System.out.println("💨 Consumo de O₂ por recolectar: " + Crecolectar);

        // 2. Verificar muerte
        if (jugador.getTanqueOxigeno().getOxigenoRestante() <= 0) {
            jugador.derrotaPorOxigeno();
            return;
        }

        // 3️⃣ Mostrar opciones al jugador
        System.out.println("\n🌿 ¿Qué recurso deseas recolectar?");
        ItemTipo[] recursos = {ItemTipo.Cuarzo, ItemTipo.Silicio, ItemTipo.Cobre};
        for (int i = 0; i < recursos.length; i++) {
            System.out.println((i + 1) + ") " + recursos[i]);
        }
        System.out.print("> ");
        int opcion = scan.nextInt();

        // Validar elección
        if (opcion < 1 || opcion > recursos.length) {
            System.out.println("❌ Opción inválida. Cancelando recolección.");
            return;
        }

        ItemTipo recursoElegido = recursos[opcion - 1];

        // 3. Calcular cantidad obtenida con la fórmula n(d)
        int n_min = 1;
        int n_max = 3;
        int cantidad = Math.max(1, (int) Math.floor(n_min + (n_max - n_min) * d));

        jugador.agregarItem(new Item(recursoElegido, cantidad));
        System.out.println("🔹 Has recolectado: " + cantidad + " x " + recursoElegido);
    }

    
    @Override
    // Implementación del método abstracto de Zona.
    public void explorar(Jugador jugador) {
        // Implementación de la lógica de O2 y Loot para la EM (10 pts)
        
        int z = jugador.getProfundidadActual();
        double d = calcularProfundidadNormalizada(z);

        // Presión (Arrecife = 0)
        double pres = jugador.getMejoraTanque() ? 0 : 0;
        
        // 1. Calcular Costo de O2
        int Cexplorar = (int) Math.ceil(12 + 10*d + pres);
        jugador.getTanqueOxigeno().consumirO2(Cexplorar);
        
        System.out.println("💨 Consumo de O₂ por explorar: " + Cexplorar);

        if (jugador.getTanqueOxigeno().getOxigenoRestante() <= 0) {
            jugador.derrotaPorOxigeno();
            return; // interrumpe la función inmediatamente
        }

       otorgarRecompensa(jugador);
    }

    private void otorgarRecompensa(Jugador jugador){
        double probabilidad= rand.nextDouble();

        // 2. Sortear PIEZA_TANQUE (30%, stock 3)
        if (piezasTanque > 0 && probabilidad < 0.3) {
            System.out.println("¡Encontraste una PIEZA_TANQUE!");
            jugador.agregarItem(new Item(ItemTipo.PIEZA_TANQUE));
            piezasTanque--;
        } else {
            // 3. Recurso aleatorio (max(1, floor(n_min * d)))
            ItemTipo[] recursos = {ItemTipo.Cuarzo, ItemTipo.Silicio, ItemTipo.Cobre};
            ItemTipo recurso = recursos[rand.nextInt(recursos.length)];
            jugador.agregarItem(new Item(recurso));
            System.out.println("🔹 Has recolectado: " + recurso);
        }
    }

    //Getter
    public int getPiezasTanque(){
        return piezasTanque;
    }
    //Setter
    public void setPiezasTanque(int piezasTanque){
        this.piezasTanque = piezasTanque; 
    }

   
}