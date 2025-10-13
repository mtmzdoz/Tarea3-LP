package player;

import java.util.List;
import java.util.ArrayList;
import objetos.Item;
import objetos.AccesoProfundidad;
import objetos.NaveExploradora;
import entorno.Zona;

/**
 * Clase que representa al jugador. Implementa AccesoProfundidad.
 */
public class Jugador implements AccesoProfundidad {
    
    private Oxigeno tanqueOxigeno;             
    private List<Item> inventario;             
    private Zona zonaActual;                   
    private int profundidadActual; 
    private boolean tienePlanos;            
    private NaveExploradora nave;                   
    private boolean trajeTermico;              
    private boolean mejoraTanque;              

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
        // Lógica simple para la EM: por ahora, asume que a nado se puede.
        return true; 
    }

    //Del diagrama
    public void verEstadoJugador() {
        System.out.println("Estado del Jugador | O2: " + tanqueOxigeno.getOxigenoRestante() + " | Zona: " + zonaActual.nombre + " | Profundidad: " + profundidadActual + "m");
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


    public void nadar(int metros) {
        // Verificar si puede acceder a esa profundidad (puedes agregar lógica real luego)
        if (!puedeAcceder(metros)) {
            System.out.println("No puedes alcanzar esa profundidad nadando.");
            return;
        }

        // Actualiza la profundidad
        this.profundidadActual = metros;

        // Calcula consumo de oxígeno (ejemplo simple: 1 O2 por cada 10 metros, mínimo 1)
        int consumo = Math.max(1, metros / 10);

        // Consume oxígeno usando tu clase Oxigeno
        tanqueOxigeno.consumirO2(consumo);

        System.out.println("El jugador nada hasta " + metros + " metros de profundidad. (-" + consumo + " O2)");
        System.out.println("Oxígeno restante: " + tanqueOxigeno.getOxigenoRestante() + "/" + tanqueOxigeno.getCapacidadMaxima());
    }

    public void viajarAZona(Zona nuevaZona) {
        if (nuevaZona == null) {
            System.out.println("La zona destino es nula. No se puede viajar.");
            return;
        }

        System.out.println("Viajando desde " + zonaActual.nombre + " hacia " + nuevaZona.nombre + "...");

        // Cambia la zona del jugador usando tu setter
        setZonaActual(nuevaZona);

        // Reinicia la profundidad al llegar
        setProfundidadActual(0);

        // Opcional: recargar oxígeno al máximo
        tanqueOxigeno.recargarCompleto();

        System.out.println("Has llegado a " + nuevaZona.nombre + ". Oxígeno recargado al máximo (" + tanqueOxigeno.getCapacidadMaxima() + ").");
    }

}