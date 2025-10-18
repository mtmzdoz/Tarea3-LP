package player;

import java.util.List;
import java.util.ArrayList;
import objetos.Item;
import objetos.AccesoProfundidad;
import objetos.NaveExploradora;
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
    @Override
    //Del diagrama
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
    
    // Getter 
    public Oxigeno getTanqueOxigeno() {
         return tanqueOxigeno; }
    public Zona getZonaActual() {
         return zonaActual; }
    public int getProfundidadActual() {
         return profundidadActual; }
    public NaveExploradora getNave() 
    { return nave; }

    //Setter
    public void setZonaActual(Zona zonaActual) { 
        this.zonaActual = zonaActual;
    }
    public void setProfundidadActual(int profundidadActual) { 
        this.profundidadActual = profundidadActual; 
    }
    public boolean getMejoraTanque() {
        return mejoraTanque; 
    } 

    //Creados
    public void viajarAZona(Zona nuevaZona) {
        if (nuevaZona == null) {
            System.out.println("La zona destino es nula. No se puede viajar.");
            return;
        }

        System.out.println("Viajando desde " + zonaActual.nombre + " hacia " + nuevaZona.nombre + "...");

        // Cambia la zona
        setZonaActual(nuevaZona);

        // Reinicia la profundidad al llegar
        setProfundidadActual(0);

        // Mensaje según si recarga oxígeno
        
    }

    public void nadar(int metros) {
        int delta = metros - this.profundidadActual;
        moverEnProfundidad(delta);
    }

    public void moverEnProfundidad(int delta) {
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
        int costoO2 = (int) Math.ceil((3 + 3*d) * Math.abs(distanciaRecorrida) / 50.0 );
        this.tanqueOxigeno.consumirO2(costoO2);

        // Actualizar profundidad
        this.setProfundidadActual(destino);

        System.out.println("Te moviste a " + destino + " m. Presión:" + presion + " | Oxígeno consumido: " + costoO2 + ". Oxígeno restante: " + this.tanqueOxigeno.getOxigenoRestante());
    }
    

    public void setEnNave(boolean enNave) {
        this.enNave = enNave;
    }

    public boolean isEnNave() {
        return enNave;
    }


}