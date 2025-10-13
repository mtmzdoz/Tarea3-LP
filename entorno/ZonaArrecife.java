package entorno;

import player.Jugador;
import objetos.ItemTipo;
import java.util.EnumSet;
import java.util.Random; 

/**
 * Zona Arrecife (0-199 m). Implementa explorar.
 */
public class ZonaArrecife extends Zona {
    
    private int piezasTanque; 

    public ZonaArrecife() {
        super("Zona Arrecife", 0, 199, EnumSet.of(ItemTipo.Cuarzo, ItemTipo.Silicio, ItemTipo.Cobre));
        this.piezasTanque = 3;
    }
    @Override
    // Implementación del método abstracto de Zona.
    public void explorar(Jugador jugador) {
        // Implementación de la lógica de O2 y Loot para la EM (10 pts)
        
        int z = jugador.getProfundidadActual();
        double d = calcularProfundidadNormalizada(z);
        
        // 1. Calcular Costo de O2 (C_explorar(d) simplificado)
        double costo = 12 + 10 * d; // pres(d) es 0 en Arrecife
        int costoO2 = (int) Math.ceil(costo);
        jugador.getTanqueOxigeno().consumirO2(costoO2);
        
        System.out.println("Explorando en Arrecife a " + z + "m. Costo O2: " + costoO2);

        // 2. Sortear PIEZA_TANQUE (30%, stock 3)
        Random rand = new Random();
        if (piezasTanque > 0 && rand.nextDouble() < 0.30) {
            System.out.println("¡Encontraste una PIEZA_TANQUE!");
            // (PENDIENTE: añadir al inventario)
            piezasTanque--;
        } else {
            // 3. Recurso aleatorio (max(1, floor(n_min * d)))
            int n_min = 1;
            int cantidad = (int) Math.max(1, Math.floor(n_min * d));
            ItemTipo recursoGanado = (ItemTipo) getRecursos().toArray()[rand.nextInt(getRecursos().size())];
            System.out.println("Recolectas " + cantidad + " de " + recursoGanado);
            // (PENDIENTE: añadir al inventario)
        }
    }
    //Getter
    public int getPiezasTanque() {
         return piezasTanque; }
    //Setter
    public void setPiezasTanque(int piezasTanque) {
         this.piezasTanque = piezasTanque; }
}