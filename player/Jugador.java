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
    
    //Del diagrama
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

    public void moverEnProfundidad(int delta) {
        Zona zona = this.getZonaActual();
        int nuevaProfundidad = this.profundidadActual;
        int destino = nuevaProfundidad + delta;

        // Validar límites de la zona
        if (nuevaProfundidad < zona.getProfundidadMin()) {
        nuevaProfundidad = zona.getProfundidadMin();
        }
        if (nuevaProfundidad > zona.getProfundidadMax()) {
            nuevaProfundidad = zona.getProfundidadMax();
        }

        // Profundidad normalizada
        double d = zona.calcularProfundidadNormalizada(nuevaProfundidad);

        int distanciaRecorrida = Math.abs(destino - nuevaProfundidad);

        // Consumo de O2
        int costoO2 = (int) Math.ceil((3 + 3*d) * Math.abs(distanciaRecorrida) / 50.0);
        this.tanqueOxigeno.consumirO2(costoO2);

        // Actualizar profundidad
        this.setProfundidadActual(destino);

        System.out.println("Te moviste a " + destino + " m. Oxígeno consumido: " + costoO2 + ". Oxígeno restante: " + this.tanqueOxigeno.getOxigenoRestante() + "\n");
    }

    private boolean enNave = false;

    public void setEnNave(boolean enNave) {
        this.enNave = enNave;
    }

    public boolean isEnNave() {
        return enNave;
    }


}