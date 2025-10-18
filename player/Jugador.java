package player;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import objetos.Item;
import objetos.ItemTipo;
import objetos.AccesoProfundidad;
import objetos.NaveExploradora;
import entorno.NaveEstrellada;
import entorno.Zona;
import entorno.ZonaProfunda;
import entorno.ZonaVolcanica;



/**
 * Clase que representa al jugador. Implementa AccesoProfundidad.
 */
public class Jugador implements AccesoProfundidad {
    
    //Del diagrama
    private Oxigeno tanqueOxigeno;             
    private List<Item> inventario;             
    private Zona zonaActual;                   
    private int profundidadActual; 
    private boolean tienePlanos;            
    private NaveExploradora nave;                   
    private boolean trajeTermico;              
    private boolean mejoraTanque;   
    private boolean enNave = false;           

    //Constructor
    public Jugador(NaveExploradora naveInicial, Zona zonaInicial) {
        this.tanqueOxigeno = new Oxigeno();
        this.inventario = new ArrayList<>();
        this.nave = naveInicial;
        this.zonaActual = zonaInicial;
        this.profundidadActual = 0; 
        this.tienePlanos = false;
        this.trajeTermico = false;
        this.mejoraTanque = false;
    }

    //Del diagrama
    @Override
    public boolean puedeAcceder(int requerido) {
        Zona zona = this.zonaActual;

        // No puede entrar a zonas volcánicas sin traje térmico
        if (zona instanceof ZonaVolcanica && !trajeTermico) {
            System.out.println("⚠️ No puedes acceder a la Zona Volcánica sin el traje térmico.");
            return false;
        }
        if (requerido > 500 && !nave.puedeAcceder(requerido)) {
            System.out.println("⚠️ No puedes acceder más allá de 500 m sin el módulo de profundidad instalado en la nave.");
            return false;
        }

        // Sin mejora, solo puede llegar hasta 500 m (igual que la nave sin módulo)
        return true;
        
    }

    //Del diagrama
    public void verEstadoJugador() {
        System.out.println("Zona Actual: " + zonaActual.nombre + " | O2: " + tanqueOxigeno.getOxigenoRestante() + " | Profundidad: " + profundidadActual + "m");
    }

    public void nadar(int delta) {
        Zona zona = this.getZonaActual();
        int profundidadInicial = this.profundidadActual;
        int destino = this.profundidadActual + delta;

        if (destino < zona.getProfundidadMin() || destino > zona.getProfundidadMax()) {
            System.out.println("⚠️ No puedes nadar fuera del rango de esta zona (" + zona.getProfundidadMin() + " - " + zona.getProfundidadMax() + " m).");
            return;
        }

        // ⚠️ Solo limitamos si es una ZonaVolcanica sin traje térmico
        if (zona instanceof ZonaVolcanica && !trajeTermico) {
            System.out.println("⚠️ No puedes nadar en la Zona Volcánica sin el traje térmico.");
            return;
        }


        // Profundidad normalizada
        double d = zona.calcularProfundidadNormalizada(profundidadInicial);
        int distanciaRecorrida = Math.abs(destino - profundidadInicial);

        double presion = 0;
        if (!this.mejoraTanque) {
            if (zona instanceof ZonaProfunda) {
                presion = ((ZonaProfunda) zona).calcularPresion(this); // devuelve 10 + 6*d
            } else if (zona instanceof ZonaVolcanica) {
                System.out.println("⚠️ No se puede descender a Zona Volcánica sin el módulo de profundidad!");
                return;
            }
        }
        // Consumo de O2
        int Cmover = (int) Math.ceil((3 + 3*d) * Math.abs(distanciaRecorrida) / 50.0 );
        this.tanqueOxigeno.consumirO2(Cmover);

        if (this.getTanqueOxigeno().getOxigenoRestante() <= 0) {
            this.derrotaPorOxigeno();
            return; // interrumpe la función inmediatamente
        }

        // Actualizar profundidad
        this.setProfundidadActual(destino);

        System.out.println("Te moviste a " + destino + " m. Presión:" + presion + " | Oxígeno consumido: " + Cmover + ". Oxígeno restante: " + this.tanqueOxigeno.getOxigenoRestante());
    }
    
    
    public void agregarItem(Item nuevo) {
        if (nuevo == null) return;
        for (Item it : inventario) {
            if (it.getTipo() == nuevo.getTipo()) {
                it.setCantidad(it.getCantidad() + nuevo.getCantidad());
                System.out.println("🎒 Sumado: " + nuevo.getCantidad() + " x " + it.getTipo() + " (total: " + it.getCantidad() + ")");
                return;
            }
        }
        inventario.add(nuevo);
        System.out.println("🎒 Agregado: " + nuevo.getCantidad() + " x " + nuevo.getTipo());
    }

    public void removerItem(ItemTipo tipo, int cantidad) {
        for (int i = 0; i < inventario.size(); i++) {
            Item item = inventario.get(i);
            if (item.getTipo() == tipo) {
                item.setCantidad(item.getCantidad() - cantidad);
                if (item.getCantidad() <= 0) inventario.remove(i);
                return;
            }
        }
    }

    public void verInventario() {
        if (inventario.isEmpty()) {
            System.out.println("🎒 Tu inventario está vacío.");
            return;
        }

        System.out.println("=== 🎒 Inventario del jugador ===");
        for (Item item : inventario) {
            System.out.println("- " + item.getTipo() + " x" + item.getCantidad());
        }
    }

    


    public void derrotaPorOxigeno() {
        System.out.println("\n💀 ¡Te has quedado sin oxígeno!");
        System.out.println("Pierdes todo tu inventario y reapareces en la nave...\n");

        inventario.removeIf(item -> item.getTipo() != ItemTipo.PIEZA_TANQUE  && item.getTipo() != ItemTipo.MODULO_PROFUNDIDAD && item.getTipo() != ItemTipo.PLANO_NAVE);

        // Reaparecer en la nave
        this.zonaActual = nave.getZonaActual(); 
        this.profundidadActual = nave.getProfundidadAnclaje();
        this.tanqueOxigeno.recargarCompleto();
        this.enNave = true;
        nave.entrar(this);

        nave.MenuNave(this, new Scanner(System.in));
        
    }

    public void derrotaPorSofocacion() {
        System.out.println("\n🥵 ¡Has sucumbido al calor extremo dentro de la nave!");
        System.out.println("Pierdes todo tu inventario y reapareces en la Nave Exploradora...\n");

        inventario.removeIf(item -> item.getTipo() != ItemTipo.PIEZA_TANQUE  && item.getTipo() != ItemTipo.MODULO_PROFUNDIDAD && item.getTipo() != ItemTipo.PLANO_NAVE);

        this.zonaActual = nave.getZonaActual(); 
        this.profundidadActual = nave.getProfundidadAnclaje();
        this.tanqueOxigeno.recargarCompleto();
        this.enNave = true;

        if (zonaActual instanceof NaveEstrellada naveEstrellada) {
            naveEstrellada.resetearAccionesSinTraje();
        }
        nave.entrar(this);

        nave.MenuNave(this, new Scanner(System.in));
    }

    public void perdidaConciencia(){
        System.out.println("\n💫 El calor y los gases te abruman... ¡pierdes la consciencia!");
            System.out.println("Cuando despiertas, estás de vuelta en la nave... pero has perdido tu inventario.");

        inventario.removeIf(item -> item.getTipo() != ItemTipo.PIEZA_TANQUE  && item.getTipo() != ItemTipo.MODULO_PROFUNDIDAD && item.getTipo() != ItemTipo.PLANO_NAVE);

        // Reaparecer en la nave
        this.zonaActual = nave.getZonaActual(); 
        this.profundidadActual = nave.getProfundidadAnclaje();
        this.tanqueOxigeno.recargarCompleto();
        this.enNave = true;
        nave.entrar(this);

        nave.MenuNave(this, new Scanner(System.in));
    }

    // Getters y Setters
    public Oxigeno getTanqueOxigeno(){
        return tanqueOxigeno; 
    }
    public boolean getMejoraTanque() {
        return mejoraTanque; 
    }
    public Zona getZonaActual() {
        return zonaActual;
    }
    public int getProfundidadActual(){
        return profundidadActual; 
    }
    public NaveExploradora getNave(){ 
        return nave; 
    }
    public boolean isEnNave() {
        return enNave;
    }
    public List<Item> getInventario() {
        return inventario;
    }
    // --- Traje térmico ---
    public boolean isTrajeTermico() {
        return trajeTermico;
    }

    


    public void setZonaActual(Zona zonaActual) { 
        this.zonaActual = zonaActual;
        //Desde o A nave estrellada resetamos por si acaso
        if (zonaActual instanceof NaveEstrellada naveEstrellada) {
            naveEstrellada.resetearAccionesSinTraje();
        }
    }
    public void setProfundidadActual(int profundidadActual) { 
        this.profundidadActual = profundidadActual; 
    }
    public void setEnNave(boolean enNave) {
        this.enNave = enNave;
    }
    public void setTanqueOxigeno(Oxigeno nuevoTanque) {
        this.tanqueOxigeno = nuevoTanque;
    }

    public void setMejoraTanque(boolean mejoraTanque) {
        this.mejoraTanque = mejoraTanque;
    }
    public void setTrajeTermico(boolean trajeTermico) {
        this.trajeTermico = trajeTermico;
    }
}