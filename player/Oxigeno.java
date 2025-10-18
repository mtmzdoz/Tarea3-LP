package player;



/**
 * Gestiona el oxígeno.
 */
public class Oxigeno {
    
    private int oxigenoRestante;    
    private int capacidadMaxima;    

    //Inicialmente
    public Oxigeno() {
        this.capacidadMaxima = 60; 
        this.oxigenoRestante = 60;
    }
    //Cuando se mejora capacidad
    public void duplicarCapacidad() {
        this.capacidadMaxima *= 2;
        this.oxigenoRestante = capacidadMaxima;
    }


    public void consumirO2(int unidades) {
        this.oxigenoRestante = Math.max(0, this.oxigenoRestante - unidades);
    

    }
    
    public void recargarCompleto() {
        this.oxigenoRestante = this.capacidadMaxima;
    }
    public void aumentarCapacidad(int extra) {
        this.capacidadMaxima += extra;
        this.oxigenoRestante = this.capacidadMaxima; // Se recarga al máximo automáticamente
    }

    //Getter
    public int getOxigenoRestante() { 
        return oxigenoRestante; }
    public int getCapacidadMaxima() { 
        return capacidadMaxima; }
    //Setter
    public void setCapacidadMaxima(int capacidadMaxima) { 
        this.capacidadMaxima = capacidadMaxima; }
}