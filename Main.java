import player.Jugador;
import objetos.NaveExploradora;
import entorno.Zona;
import entorno.ZonaArrecife;
import entorno.ZonaProfunda;
import entorno.ZonaVolcanica;
import entorno.NaveEstrellada;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {

        NaveEstrellada ZonaInicial = new NaveEstrellada();
        NaveExploradora Nave = new NaveExploradora();
        ZonaVolcanica ZonaVolcanica = new ZonaVolcanica();
        ZonaArrecife ZonaArrecife = new ZonaArrecife();
        ZonaProfunda ZonaProfunda = new ZonaProfunda();
        Jugador jugador = new Jugador(Nave, ZonaInicial);

    
        jugador.verEstadoJugador();

        System.out.println("\n--- Estado inicial ---");
        jugador.verEstadoJugador();

        System.out.println("\n--- Movimiento a nado ---");
        jugador.nadar(50);
        jugador.verEstadoJugador();

        System.out.println("\n--- Viaje a la Zona Volcánica ---");
        jugador.viajarAZona(ZonaVolcanica);
        jugador.verEstadoJugador();
    }
}

