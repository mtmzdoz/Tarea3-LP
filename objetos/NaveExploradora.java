package objetos; 

import entorno.Zona;
import player.Jugador;
 

public class NaveExploradora extends Vehiculo implements AccesoProfundidad {

    private int profundidadSoportada; 
    private ModuloProfundidad moduloInstalado;
    private int profundidadAnclaje;
    private Zona ZonaActual;

    @Override
    // Implementación del método de la interfaz AccesoProfundidad.
    public boolean puedeAcceder(int requerido) {
        if (moduloInstalado != null) {
            return true;
        }
    
        // Sin módulo, solo se permite hasta 500 m
         return requerido <= 500;
        
        
    }

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

    //Constructor
    public NaveExploradora(Zona ZonaInicial) {
        super();
        this.profundidadSoportada = 500;
        this.moduloInstalado = null;
        this.ZonaActual = ZonaInicial;
        this.profundidadAnclaje = ZonaInicial.getProfundidadMin();

    }
    
    // Método para instalar el módulo (llama al método del módulo)
    public void instalarModulo(ModuloProfundidad modulo) {
        if (this.moduloInstalado == null) {
            modulo.aumentarProfundidad(); 
            this.moduloInstalado = modulo;
            System.out.println("Módulo instalado. Profundidad soportada: " + this.profundidadSoportada + "m.");
        }
    }
    
    //Getter
    public int getProfundidadSoportada() { 
        return profundidadSoportada; 
    }
    public int getProfundidadAnclaje() {
        return profundidadAnclaje;
    }
    public Zona getZonaActual() {
        return ZonaActual;
    }
    // Getter para el módulo instalado
    public ModuloProfundidad getModuloInstalado() {
        return moduloInstalado;
    }

    //Setter
    public void setZonaActual(Zona Zona) {
        this.ZonaActual = Zona;
    }
    public void setProfundidadAnclaje(int profundidad) {
        this.profundidadAnclaje = profundidad;
    }   


    // 🔹 Método para entrar a la nave
    public void entrar(Jugador jugador) {
       jugador.setEnNave(true);
    
        jugador.getTanqueOxigeno().recargarCompleto();
       
        jugador.setProfundidadActual(profundidadAnclaje);

        System.out.println("\n === Nave Exploradora ==="); //BORRAR DEBUG OXIGENO
        System.out.println("El oxígeno ha sido recargado: " + jugador.getTanqueOxigeno().getOxigenoRestante() + "| Profundidad de anclaje actual: " + profundidadAnclaje + " m\n");
    }

    // 🔹 Método para salir
    public void salir(Jugador jugador) {
        jugador.setEnNave(false);
        jugador.setProfundidadActual(profundidadAnclaje);
        System.out.println("Has salido de la Nave Exploradora a " + profundidadAnclaje + " m.\n");
    }

    public void moverNave(int nuevaProfundidad) {
        if (ZonaActual == null) {
            System.out.println("⚠️ La nave no está asignada a ninguna zona.");
            return;
        }

        if (nuevaProfundidad < ZonaActual.getProfundidadMin() ||
            nuevaProfundidad > ZonaActual.getProfundidadMax()) {
            System.out.println("⚠️ No se puede mover la nave fuera del rango permitido (" +
                    ZonaActual.getProfundidadMin() + "-" + ZonaActual.getProfundidadMax() + " m).");
            return;
        }

        System.out.println("Moviendo nave desde " + profundidadAnclaje + " m a " + nuevaProfundidad + " m (sin costo de O2).");
        this.profundidadAnclaje = nuevaProfundidad;
    }

}