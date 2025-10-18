

import player.Jugador;
import objetos.NaveExploradora;
import entorno.ZonaArrecife;
import entorno.ZonaProfunda;
import entorno.ZonaVolcanica;
import entorno.NaveEstrellada;


import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner Scan = new Scanner(System.in); //para los inputs

        ZonaVolcanica ZonaVolcanica = new ZonaVolcanica();
        ZonaArrecife ZonaArrecife = new ZonaArrecife();
        ZonaProfunda ZonaProfunda = new ZonaProfunda();
        NaveEstrellada NaveEstrellada = new NaveEstrellada();
        NaveExploradora Nave = new NaveExploradora(ZonaArrecife);
        Jugador jugador = new Jugador(Nave, ZonaArrecife);
        
        NaveEstrellada.setZonaAnterior(null);
        NaveEstrellada.setZonaSiguiente(ZonaArrecife);
        
        ZonaArrecife.setZonaAnterior(NaveEstrellada);
        ZonaArrecife.setZonaSiguiente(ZonaProfunda);
        
        ZonaProfunda.setZonaAnterior(ZonaArrecife);
        ZonaProfunda.setZonaSiguiente(ZonaVolcanica);
        
        ZonaVolcanica.setZonaAnterior(ZonaProfunda);
        ZonaVolcanica.setZonaSiguiente(null);

        
        boolean jugando = true;

        System.out.println("=== Inicio del juego ===");
        while (jugando){
            jugador.verEstadoJugador();
            
            System.out.println("1) Subir o descender en profundidad (a nado)");
            System.out.println("2) Explorar");
            System.out.println("3) Recoger recursos");
            System.out.println("4) Entrar a la Nave Exploradora");
            System.out.println("5) Ver profundidad actual");
            System.out.println("6) Ver oxígeno restante");
            System.out.println("7) Ver inventario");
            System.out.println("0) Salir del juego");
            System.out.print("> ");

            int opcion = Scan.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Indica profundidad destino (rango zona " + jugador.getZonaActual().getProfundidadMin() + "-" + jugador.getZonaActual().getProfundidadMax() + " m):\n> ");
                    int destino = Scan.nextInt();
                    int delta = destino - jugador.getProfundidadActual();
                    jugador.nadar(delta);
                    break;

                case 2:
                    jugador.getZonaActual().explorar(jugador);
                    break;

                case 3:
                    jugador.getZonaActual().recolectar(jugador,Scan);
                    break;

                case 4:
                    NaveExploradora nave = jugador.getNave();
                    nave.setZonaActual(jugador.getZonaActual()); // La nave se ubica en la zona actual
                    nave.entrar(jugador); // Recarga O2 y posiciona jugador en profundidad de anclaje
                    if (jugador.getZonaActual() instanceof NaveEstrellada naveEstrellada) {
                        naveEstrellada.resetearAccionesSinTraje();
                    }
                    nave.MenuNave(jugador, Scan);
                    break;
               

                case 5:
                    System.out.println("Profundidad actual: " + jugador.getProfundidadActual() + " m");
                    break;

                case 6:
                    System.out.println("Oxígeno restante: " + jugador.getTanqueOxigeno().getOxigenoRestante());
                    break;

                case 7:
                    jugador.verInventario();
                    break;
                case 0:
                    System.out.println("Saliendo del juego...");
                    jugando = false;
                    break;

                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
                    break;
                }
            }
            Scan.close();
        } 
    
    }



