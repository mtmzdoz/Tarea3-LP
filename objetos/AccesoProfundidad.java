package objetos;

/**
 * Interfaz que define la capacidad de un objeto de acceder o soportar 
 * una profundidad requerida. [cite: 135]
 */
public interface AccesoProfundidad {

    //Verifica si el objeto puede acceder o soportar una profundidad dada. [cite: 136]
    boolean puedeAcceder(int requerido);
}