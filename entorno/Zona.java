package entorno;

import player.Jugador;
import objetos.ItemTipo;
import java.util.EnumSet;


public abstract class Zona {
    
    public String nombre; 
    private int profundidadMin;            
    private int profundidadMax;            
    private EnumSet<ItemTipo> recursos;    

    //Constructor
    public Zona(String nombre, int min, int max, EnumSet<ItemTipo> recursosDisponibles) {
        this.nombre = nombre;
        this.profundidadMin = min;
        this.profundidadMax = max;
        this.recursos = recursosDisponibles;
    }

    // Método concreto: no requiere implementación en subclases.
    public void entrar(Jugador jugador) {
        jugador.setZonaActual(this);
        System.out.println("El jugador ha entrado en " + this.nombre);
    }

    // Método ABSTRACTO: OBLIGA a todas las subclases a tener un método explorar.
    public abstract void explorar(Jugador jugador);

    // Getter
    public int getProfundidadMin() { 
        return profundidadMin; 
    }
    public int getProfundidadMax() {
        return profundidadMax; 
    }
    public EnumSet<ItemTipo> getRecursos() { 
        return recursos; 
    } 
    
    // Método utilitario para O2 (aunque la lógica de la fórmula se puede poner en Main)
    public double calcularProfundidadNormalizada(int z) {
        double divisor = Math.max(1, this.profundidadMax - this.profundidadMin);
        return (double) (z - this.profundidadMin) / divisor;
    }
}