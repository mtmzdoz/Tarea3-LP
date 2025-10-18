package objetos; 


import entorno.Zona;
import entorno.ZonaVolcanica;
import player.Jugador;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

 

public class NaveExploradora extends Vehiculo implements AccesoProfundidad {

    private int profundidadSoportada; 
    private ModuloProfundidad moduloInstalado;
    private int profundidadAnclaje;
    private Zona ZonaActual;
    private List<Item> inventarioNave = new ArrayList<>();

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
        this.inventarioNave = new ArrayList<>();

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
                    CrearObjetos(jugador, Scan);
                    break;
                case 3:
                    transferirObjetos(jugador);
                    break;
                case 4:
                    Zona zonaActual = this.getZonaActual();
                    String nombreSiguiente = (zonaActual.getZonaSiguiente() != null) ? zonaActual.getZonaSiguiente().nombre : "No hay";
                    String nombreAnterior = (zonaActual.getZonaAnterior() != null) ? zonaActual.getZonaAnterior().nombre : "No hay";
                    
                    System.out.println("1) Ir a zona siguiente (" + nombreSiguiente + ")");
                    System.out.println("2) Volver a zona anterior (" + nombreAnterior + ")");
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
                    System.out.println("=== 📦 Inventario de la Nave ===");

                    // Comprobar si la nave tiene objetos guardados
                    if (this.getBodega().isEmpty()) {
                        System.out.println("La nave no tiene objetos guardados.");
                    } else {
                        for (Item item : this.getBodega()) {
                            System.out.println("- " + item.getTipo() + " x" + item.getCantidad());
                        }
                        System.out.print("\n¿Deseas retirar algún objeto? (s/n): ");
                        String respuesta = Scan.next().toLowerCase();

                        if (respuesta.equals("s")) {
                            this.retirarObjetos(jugador, Scan);
                        } else {
                            System.out.println("Volviendo al menú principal...\n");
                        }   
                    }
                        System.out.println(); // Línea en blanco estética
                        break;
                case 6:

                    if (this.getZonaActual() instanceof ZonaVolcanica) {
                        if (!jugador.getMejoraTanque() && !jugador.isTrajeTermico()) {
                            System.out.println("💀 No puedes salir de la nave: la presión y el calor de la Zona Volcánica te destruirían instantáneamente.");
                            System.out.println("Necesitas el tanque mejorado y el traje térmico antes de intentar salir.");
                            break;
                        } else if (!jugador.getMejoraTanque()) {
                            System.out.println("⚠️ No puedes salir aún: la presión en esta zona es infinita sin el tanque mejorado.");
                            System.out.println("Instala la mejora del tanque antes de intentar bajar.");
                            break;
                        }else if (!jugador.isTrajeTermico()) {
                            System.out.println("🥵 No puedes salir aún: el calor extremo fundiría tu traje.");
                            System.out.println("Necesitas el traje térmico antes de salir a esta zona.");
                            break;
                        }
                    }

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

        int PiezasTanque = 0, Plata = 0, Cuarzo = 0, Silicio = 0, Oro = 0;
        boolean puedeInstalarModulo = false;

        // ✅ Buscar cuántas piezas tanque tiene el jugador
        for (Item item : jugador.getInventario()) {
            switch (item.getTipo()) {
                case PIEZA_TANQUE:
                    PiezasTanque = item.getCantidad();
                    break;
                case MODULO_PROFUNDIDAD:
                    puedeInstalarModulo = true;
                    break;
                case Plata: 
                    Plata = item.getCantidad();
                    break;
                case Cuarzo:
                    Cuarzo = item.getCantidad();
                    break;
                case Silicio: 
                    Silicio = item.getCantidad();
                    break;
                case Oro: 
                    Oro = item.getCantidad();
                    break;
                default:
                    break;
            }
        }


        System.out.println("1) Mejorar tanque (requiere 3 Piezas Tanque)");
        System.out.println("2) Instalar Modulo Profundidad en la nave");
        System.out.println("3) Crear traje térmico");
        if (jugador.getMejoraTanque()) {
            System.out.println("4) Mejorar capacidad de oxígeno (+30)");
        }
            
        System.out.println("0) Cancelar");
        System.out.print("> ");
        int opcion = scan.nextInt();

        switch (opcion) {
            case 1:
                if (jugador.getMejoraTanque()) {
                    System.out.println("⚠️ Ya tienes instalada la mejora del tanque.");
                } else if ( PiezasTanque == 3) {
                    mejorarTanque(jugador);
                } else {
                    System.out.println("No tienes piezas suficientes");
                }
                break;
            case 2:
                if (this.getModuloInstalado() != null) {
                    System.out.println("⚠️ Ya tienes instalado un módulo de profundidad.");
                }else if( !puedeInstalarModulo){
                    System.out.println("No tienes el módulo profundidad en el inventario para mejorar");
                } else {
                    ModuloProfundidad modulo = new ModuloProfundidad();
                    this.instalarModulo(modulo);
                    jugador.getInventario().removeIf(i -> i.getTipo() == ItemTipo.MODULO_PROFUNDIDAD);
                    System.out.println("✅ MODULO_PROFUNDIDAD instalado correctamente.");
                }
                break;

            case 3:
                if (jugador.isTrajeTermico()) {
                    System.out.println("⚠️ Ya posees un traje térmico.");
                } else if (Silicio >= 10 && Oro >= 3 && Cuarzo >= 5) {
                    jugador.setTrajeTermico(true);
                    jugador.removerItem(ItemTipo.Silicio, 10);
                    jugador.removerItem(ItemTipo.Oro, 3);
                    jugador.removerItem(ItemTipo.Cuarzo, 5);
                    System.out.println("✅ Has creado un traje térmico. ¡Ahora podrás resistir el calor extremo!");
                } else {
                    System.out.println("❌ No tienes suficientes materiales");
                }
                break;
            case 4:
                if (!jugador.getMejoraTanque()) {
                    System.out.println("❌ Primero debes mejorar el tanque antes de aumentar su capacidad.");
                } else if (Plata >= 10 && Cuarzo >= 15) {
                    jugador.getTanqueOxigeno().aumentarCapacidad(30);
                    jugador.removerItem(ItemTipo.Plata, 10);
                    jugador.removerItem(ItemTipo.Cuarzo, 15);
                    System.out.println("✅ Has mejorado tu tanque de oxígeno (+30 capacidad).");
                } else {
                    System.out.println("❌ No tienes suficientes materiales.");
                }   
                break;


            case 0:
                System.out.println("Creación cancelada.");
                break;
            default:
                System.out.println("Opción inválida.");
        }

    }

    public void retirarObjetos(Jugador jugador, Scanner scan) {
        System.out.println("=== Bodega de la Nave ===");
        for (int i = 0; i < getBodega().size(); i++) {
            Item item = getBodega().get(i);
            System.out.println((i + 1) + ") " + item.getTipo() + " x" + item.getCantidad());
        }

        System.out.print("Selecciona el número del objeto a retirar (0 para cancelar): ");
        int opcion = scan.nextInt();
        if (opcion == 0 || opcion > getBodega().size()) return;

        Item seleccionado = getBodega().get(opcion - 1);
        System.out.print("¿Cuántas unidades deseas retirar? (máx " + seleccionado.getCantidad() + "): ");
        int cantidad = scan.nextInt();

        if (cantidad <= 0 || cantidad > seleccionado.getCantidad()) {
            System.out.println("❌ Cantidad inválida.");
            return;
        }

        jugador.agregarItem(new Item(seleccionado.getTipo(), cantidad));
        seleccionado.setCantidad(seleccionado.getCantidad() - cantidad);
        if (seleccionado.getCantidad() == 0) {
            getBodega().remove(seleccionado);
        }
        System.out.println("✅ Has retirado " + cantidad + " x " + seleccionado.getTipo() + " de la bodega.");
    
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
    public List<Item> getInventarioNave() {
        return inventarioNave;
    }

    //Setter
    public void setZonaActual(Zona Zona) {
        this.ZonaActual = Zona;
    }
    public void setProfundidadAnclaje(int profundidad) {
        this.profundidadAnclaje = profundidad;
    } 
}


