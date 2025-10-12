package objetos; 

public class NaveExploradora extends Vehiculo implements AccesoProfundidad {

    private int profundidadSoportada; 
    private ModuloProfundidad moduloInstalado;

    public class ModuloProfundidad { 
        
        private int profundidadExtra; 
        
        public ModuloProfundidad() {
            this.profundidadExtra = 1000;
        }
        
        // Método solicitado en el diagrama. Ahora no necesita recibir la nave como parámetro.
        public void aumentarProfundidad() {
            // Accede DIRECTAMENTE al miembro privado de la instancia de NaveExploradora que lo contiene
            profundidadSoportada += this.profundidadExtra; 
        }
        
        // Getter
        public int getProfundidadExtra() {
            return profundidadExtra;
        }
    }

    public NaveExploradora() {
        super();
        this.profundidadSoportada = 500;
        this.moduloInstalado = null;
    }
    
    // Método para instalar el módulo (llama al método del módulo)
    public void instalarModulo(ModuloProfundidad modulo) {
        if (this.moduloInstalado == null) {
            modulo.aumentarProfundidad(); 
            this.moduloInstalado = modulo;
            System.out.println("Módulo instalado. Profundidad soportada: " + this.profundidadSoportada + "m.");
        }
    }

    // Implementación del método de la interfaz AccesoProfundidad.
    public boolean puedeAcceder(int requerido) {
        return requerido <= this.profundidadSoportada;
    }
    //Getter
    public int getProfundidadSoportada() { 
        return profundidadSoportada; 
    }

    
}