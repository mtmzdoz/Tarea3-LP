package objetos;

public class Item {
   //privados + / públicos -
    private ItemTipo tipo;
    private int cantidad;

    // Constructor
    public Item(ItemTipo tipo, int cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    // Getter
    public ItemTipo getTipo() {
        return tipo;
    }

    public int getCantidad() {
        return cantidad;
    }
    
    // Setter 
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }  
}
