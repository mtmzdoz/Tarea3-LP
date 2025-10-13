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
    // Implementación del método de la interfaz AccesoProfundidad.
    public boolean puedeAcceder(int requerido) {
        // Lógica simple para la EM: por ahora, asume que a nado se puede.
        return true; 
    }

    // Método en el diagrama
    public void verEstadoJugador() {
        System.out.println("Estado del Jugador: O2: " + tanqueOxigeno.getOxigenoRestante() + " | Zona: " + zonaActual.nombre + " | Profundidad: " + profundidadActual + "m");
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
        this.zonaActual = zonaActual; }
    public void setProfundidadActual(int profundidadActual) { 
        this.profundidadActual = profundidadActual; }
    public boolean getMejoraTanque() {
         return mejoraTanque; } 
}