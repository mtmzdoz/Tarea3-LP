import player.Jugador;
import objetos.NaveExploradora;
import entorno.Zona;
import entorno.ZonaArrecife;
import entorno.ZonaProfunda;
import entorno.ZonaVolcanica;
import entorno.NaveEstrellada;


import java.util.Scanner;



public class Main {
    public static void main(String[] args) {

        ZonaVolcanica ZonaVolcanica = new ZonaVolcanica();
        ZonaArrecife ZonaArrecife = new ZonaArrecife();
        ZonaProfunda ZonaProfunda = new ZonaProfunda();
        NaveEstrellada NaveEstrellada = new NaveEstrellada();
        NaveExploradora Nave = new NaveExploradora(ZonaArrecife);
        Jugador jugador = new Jugador(Nave, NaveEstrellada);

        NaveEstrellada.setZonaSiguiente(ZonaArrecife);

        ZonaArrecife.setZonaAnterior(NaveEstrellada);
        ZonaArrecife.setZonaSiguiente(ZonaProfunda);
        
        ZonaProfunda.setZonaAnterior(ZonaArrecife);
        ZonaProfunda.setZonaSiguiente(ZonaVolcanica);
        
        ZonaVolcanica.setZonaAnterior(ZonaProfunda);
        ZonaVolcanica.setZonaSiguiente(null);

        Scanner Scan = new Scanner(System.in); //para los inputs
        boolean jugando = true;

        System.out.println("=== Inicio del juego ===");
        while (jugando){
            jugador.verEstadoJugador();

            if (jugador.getZonaActual() instanceof NaveEstrellada){
                System.out.println("1) Ir a Zona Arrecife");
                System.out.println("0) Salir");
                System.out.print("> ");
                int opcion = Scan.nextInt();

                switch (opcion) {
                    case 1:
                        jugador.setZonaActual(ZonaArrecife);
                        jugador.setProfundidadActual(0);
                        System.out.println("=== Has llegado a Zona Arrecife ===");
                        break;

                    case 0:
                        System.out.println("Saliendo del juego...");
                        jugando = false;
                        break;

                    default:
                        System.out.println("Opción inválida. Intenta de nuevo.");
                }
    
            }else{
                System.out.println("1) Subir o descender en profundidad (a nado)");
                System.out.println("2) Explorar");
                System.out.println("3) Recoger recursos");
                System.out.println("4) Entrar a la Nave Exploradora");
                System.out.println("5) Ver profundidad actual");
                System.out.println("6) Ver oxígeno restante");
                System.out.println("7) Ver inventario");
                System.out.println("0) Salir");
                System.out.print("> ");

                int opcion = Scan.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.print("Indica profundidad destino (rango zona " + jugador.getZonaActual().getProfundidadMin() + "-" + jugador.getZonaActual().getProfundidadMax() + " m):\n> ");
                        int destino = Scan.nextInt();
                        int deltaZ = destino - jugador.getProfundidadActual();
                        jugador.moverEnProfundidad(deltaZ);
                        break;

                    case 2:
                        jugador.getZonaActual().explorar(jugador);
                        break;

                    case 3:
                        System.out.println("Función de recolectar aún no implementada.");
                        break;

                    case 4:
                        NaveExploradora nave = jugador.getNave();
                        nave.setZonaActual(jugador.getZonaActual()); // La nave se ubica en la zona actual
                        nave.entrar(jugador); // Recarga O2 y posiciona jugador en profundidad de anclaje

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
                                nave.setProfundidadAnclaje(jugador.getProfundidadActual());
                                System.out.println("Ingresa la nueva profundidad de anclaje (" + nave.getZonaActual().getProfundidadMin() + "-" + nave.getZonaActual().getProfundidadMax() + " m): ");
                                int nuevoAnclaje = Scan.nextInt();

                                // Validar que esté dentro del rango
                                if (nuevoAnclaje < nave.getZonaActual().getProfundidadMin() || nuevoAnclaje > nave.getZonaActual().getProfundidadMax()) {
                                    System.out.println("Profundidad inválida para esta zona.");
                                } else if (nuevoAnclaje > 500 && nave.getModuloInstalado() == null) {
                                    // Restricción de 500 m sin módulo
                                    System.out.println("No puedes anclar la nave por encima de 500 m sin módulo de profundidad.");
                                } else {
                                    nave.setProfundidadAnclaje(nuevoAnclaje);
                                    System.out.println("Nuevo anclaje establecido en " + nuevoAnclaje + " m.");
                                }
                                break;
                            case 2:
                                System.out.println("Funcionalidad de crear objetos (pendiente de implementar).");
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
                                        if (jugador.puedeAcceder(minProf) || nave.puedeAcceder(minProf)) {
                                            jugador.setZonaActual(siguienteZona);
                                            nave.setZonaActual(siguienteZona);
                                            nave.setProfundidadAnclaje(minProf);
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
                                        nave.setZonaActual(anteriorZona);
                                        nave.setProfundidadAnclaje(minProf);
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
                                nave.salir(jugador);
                                enNave = false;
                                break;
                            default:
                                System.out.println("Opción inválida.");
                            }
                        }
                        break;

                    case 5:
                        System.out.println("Profundidad actual: " + jugador.getProfundidadActual() + " m");
                        break;

                    case 6:
                        System.out.println("Oxígeno restante: " + jugador.getTanqueOxigeno().getOxigenoRestante());
                        break;

                    case 7:
                        System.out.println("Inventario (pendiente de implementación): " + jugador.getZonaActual().nombre);
                        break;
                    case 0:
                        System.out.println("Saliendo del juego...");
                        jugando = false;
                        break;

                    default:
                        System.out.println("Opción inválida. Intenta de nuevo.");
                }
            }
        }

        Scan.close();
        
    
    }
}

