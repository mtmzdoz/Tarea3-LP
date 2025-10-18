package objetos; 

import java.util.Scanner;

import entorno.Zona;
import player.Jugador;
 

public class NaveExploradora extends Vehiculo implements AccesoProfundidad {

    private int profundidadSoportada; 
    private ModuloProfundidad moduloInstalado;
    private int profundidadAnclaje;
    private Zona ZonaActual;

    // Implementación del método de la interfaz AccesoProfundidad.
    @Override
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
    
    // 🔹 Método para entrar a la nave
    public void entrar(Jugador jugador) {
       jugador.setEnNave(true);
    
        jugador.getTanqueOxigeno().recargarCompleto();
       
        jugador.setProfundidadActual(profundidadAnclaje);

        System.out.println("\n === Nave Exploradora ==="); //BORRAR DEBUG OXIGENO
        System.out.println("El oxígeno ha sido recargado: " + jugador.getTanqueOxigeno().getOxigenoRestante() + " | Profundidad de anclaje actual: " + profundidadAnclaje + " m\n");
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

    public void MenuNave(Jugador jugador, Scanner Scan) {
                
        boolean enNave = true;
        while (enNave) {
            System.out.println("=== Menú Nave Exploradora ===");
            System.out.println("1) Ajustar profundidad de nave (anclaje)");
            System.out.println("2) Crear objetos");
            System.out.println("3) Guardar TODOS los objetos del jugador en la nave");
            System.out.println("4) Moverse a otra zona ");
            System.out.println("5) Ver inventario de la nave");
            System.out.println("6) Salir de la nave (volver al agua en el anclaje)");
            System.out.print("> ");
            int opcionNave = Scan.nextInt();

            switch (opcionNave) {
                case 1:
                    System.out.println("Ingresa la nueva profundidad de anclaje (" + this.getZonaActual().getProfundidadMin() + "-" + this.getZonaActual().getProfundidadMax() + " m): ");
                    int nuevoAnclaje = Scan.nextInt();

                    // Validar que esté dentro del rango
                    if (nuevoAnclaje < this.getZonaActual().getProfundidadMin() || nuevoAnclaje > this.getZonaActual().getProfundidadMax()) {
                        System.out.println("Profundidad inválida para esta zona.");
                    } else if (nuevoAnclaje > 500 && this.getModuloInstalado() == null) {
                        // Restricción de 500 m sin módulo
                        System.out.println("No puedes anclar la nave por encima de 500 m sin módulo de profundidad.");
                    } else {
                        this.setProfundidadAnclaje(nuevoAnclaje);
                        System.out.println("Nuevo anclaje establecido en " + nuevoAnclaje + " m.");
                    }
                    break;
                case 2:
                    System.out.println("Funcionalidad de crear objetos (pendiente de implementar).");
                    CrearObjetos(jugador, Scan);
                    break;
                case 3:
                    System.out.println("Funcionalidad de gestionar inventario (pendiente).");
                    break;
                case 4:
                    System.out.println("1) Ir a zona siguiente");
                    System.out.println("2) Volver a zona anterior");
                    System.out.print("> ");
                    int Opcion = Scan.nextInt();
           
                    if (Opcion == 1) {
                        Zona siguienteZona = jugador.getZonaActual().getZonaSiguiente();        
                            if(siguienteZona != null){
                                int minProf = siguienteZona.getProfundidadMin();
                                    if (jugador.puedeAcceder(minProf) || this.puedeAcceder(minProf)) {
                                        jugador.setZonaActual(siguienteZona);
                                        this.setZonaActual(siguienteZona);
                                        this.setProfundidadAnclaje(minProf);
                                        jugador.setProfundidadActual(minProf);
                                        System.out.println("Viajando a " + siguienteZona.nombre + " ...");
                                        System.out.println("Destino alcanzado. (" + siguienteZona.nombre + ", anclaje="+ minProf + ").\n");
                                    } else {
                                        System.out.println("No puedes acceder a esta zona aún (profundidad mínima " + minProf + " m).");
                                    }
                            } else {
                                System.out.println("No hay zona siguiente.");
                            }
                    }else if (Opcion == 2) {
                        Zona anteriorZona = jugador.getZonaActual().getZonaAnterior();
                            if (anteriorZona != null) {
                                int minProf = anteriorZona.getProfundidadMin();
                                jugador.setZonaActual(anteriorZona);
                                this.setZonaActual(anteriorZona);
                                this.setProfundidadAnclaje(minProf);
                                jugador.setProfundidadActual(minProf);
                                System.out.println("Regresando a " + anteriorZona.nombre + "...");
                                System.out.println("=== Has vuelto a " + anteriorZona.nombre + " (anclaje=" + minProf + "). ===");
                            } else {
                                System.out.println("No hay zona anterior.");
                            }
                    } else {
                        System.out.println("Opción inválida.");
                    }
                    break;
                case 5:
                    System.out.println("Inventario de la nave (pendiente).");
                    break;
                case 6:
                    this.salir(jugador);
                    jugador.setEnNave(false);
                    enNave = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        if (Scan.hasNextLine()) Scan.nextLine(); //Limpia linea por si acasoo
    }

    public void CrearObjetos(Jugador jugador, Scanner scan) {
        System.out.println("=== Mesa de Crafteo ===");

        boolean puedeMejorarTanque = false;
        boolean puedeInstalarModulo = false;

        // ✅ Buscar cuántas piezas tanque tiene el jugador
        for (Item item : jugador.getInventario()) {
            if (item.getTipo() == ItemTipo.PIEZA_TANQUE && item.getCantidad() >= 3) {
                puedeMejorarTanque = true;
            
            } else if (item.getTipo() == ItemTipo.MODULO_PROFUNDIDAD) {
                puedeInstalarModulo = true;
              
            }
        }

        

        if (puedeMejorarTanque || puedeInstalarModulo) {
            if (puedeMejorarTanque){
                System.out.println("1) Mejorar tanque (requiere 3 PIEZAS_TANQUE)");
            }
            if(puedeInstalarModulo){
                System.out.println("2) Instalar MODULO_PROFUNDIDAD en la nave");
            }
            System.out.println("0) Cancelar");
            System.out.print("> ");
            int opcion = scan.nextInt();

            switch (opcion) {
                case 1:
                    if (puedeMejorarTanque) {
                        mejorarTanque(jugador);
                    } else {
                        System.out.println("❌ No tienes suficientes PIEZAS_TANQUE.");
                    }
                    break;
                case 2:
                    if (puedeInstalarModulo) {
                        ModuloProfundidad modulo = new ModuloProfundidad();
                        this.instalarModulo(modulo);
                        jugador.getInventario().removeIf(i -> i.getTipo() == ItemTipo.MODULO_PROFUNDIDAD);
                        System.out.println("✅ MODULO_PROFUNDIDAD instalado correctamente.");
                    } else {
                        System.out.println("❌ No tienes un módulo de profundidad disponible.");
                    }
                    break;
                case 0:
                    System.out.println("Creación cancelada.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } else if (jugador.getMejoraTanque()) {
            System.out.println(" Ya tienes la mejora del tanque instalada.");
        } 

    }

    private void mejorarTanque(Jugador jugador) {
        for (Item item : jugador.getInventario()) {
            if (item.getTipo() == ItemTipo.PIEZA_TANQUE) {
                item.setCantidad(item.getCantidad() - 3);
                if (item.getCantidad() <= 0) {
                    jugador.getInventario().remove(item);
                }
                break;
            }
        }

        jugador.setMejoraTanque(true);
        jugador.getTanqueOxigeno().duplicarCapacidad();

        System.out.println("✅ ¡Has mejorado tu tanque!");
        System.out.println("💨 Capacidad de oxígeno duplicada y presión anulada en zonas profundas y volcánicas.");
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
}


