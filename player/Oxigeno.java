package player;

/**
 * Gestiona el oxígeno.
 */
public class Oxigeno {
    
    private int oxigenoRestante;    
    private int capacidadMaxima;    

    public Oxigeno() {
        this.capacidadMaxima = 60;
        this.oxigenoRestante = 60;
    }

    public void consumirO2(int unidades) {
        this.oxigenoRestante = Math.max(0, this.oxigenoRestante - unidades);
    }

    public void recargarCompleto() {
        this.oxigenoRestante = this.capacidadMaxima;
    }
    //Getter
    public int getOxigenoRestante() { return oxigenoRestante; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    //Setter
    public void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }
}