package objetos;

/**
 * Representa el Robot Excavador. Hereda de Vehiculo.
 */
public class RobotExcavador extends Vehiculo {

    private int capacidadCarga; 

    //Constructor
    public RobotExcavador() {
        super(); // Llama al constructor de Vehiculo para inicializar la bodega
        this.capacidadCarga = 1000; 
    }

    // El método transferirObjetos() es heredado de Vehiculo.

    // Getters
    public int getCapacidadCarga() {
        return capacidadCarga;
    }
    //Setter
    public void setCapacidadCarga(int capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }
}