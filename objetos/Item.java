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

    //Constructor secundario del tipo y agrega +1
    public Item(ItemTipo tipo) {
        this(tipo, 1);
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
