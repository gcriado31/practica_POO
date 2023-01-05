package etsisi.bs0165;

import etsisi.pilas.*;

import java.awt.*;
import java.util.Scanner;


// TODO    LISTA CIRCULAR: IMPLEMENTAR EN CLASES.
// TODO    MODIFICAR ESQUEMAS DE JUEGO DE ENTRENAMIENTO Y DEMO.


/**
 * Esta clase abstracta contiene los métodos comunes (y mínimos) de los cuatro modos de juego: Demo, Enfrentamiento y Entrenamiento.
 */
public abstract class ModoJuego {

    // ATRIBUTOS
    protected Turno turno;
    protected DLCircularStack<Jugador> jugadores;
    private IteratorDLCircularStack<Jugador> iteradorJugadores;
    protected Jugador ganador;
    protected Validaciones reglas;
    protected Tablero tablero;

    protected final String EMPATE="¡¡¡EMPATE!!!";
    protected final String GANADOR="EL GANADOR ES ";
    protected final int NUMERO_JUGADORES=2;
    protected final Ficha fichaAzul=new Ficha('A', Color.BLUE);
    protected final Ficha fichaRoja= new Ficha('R',Color.RED);

    // ATRIBUTOS PRIVADOS
    protected final int NUM_FILAS=6;
    protected final int NUM_COLUMNAS=7;
    private final int INICIO_BUCLE=0;

    // CONSTRUCTOR

    protected ModoJuego(Tablero tablero) {
        this.tablero = tablero;
        this.reglas=new Validaciones(tablero);
        this.jugadores=this.menuJugadores(this.tablero);
        this.iteradorJugadores=new IteratorDLCircularStack<>(this.jugadores);
        this.turno=new Turno(this.jugadores);
    }


    // MÉTODOS ABSTRACTOS
    /**
     * Ejecuta el juego y mientras el jugador(es) quiera(n) se ejecutará una nueva partida.
     */
    protected abstract void jugar();

    /**
     * Presenta los resultados
     */
    protected abstract void resultados();

    /**
     * Da la bienvenida a los jugadores y almacena los jugadores.
     * @param tablero Se pasa un Tablero para que los jugadores ya tengan el tablero cuando se crean
     * @return Se devuelve el array de jugadores con toda la información.
     */
    protected abstract DLCircularStack<Jugador> menuJugadores(Tablero tablero);

    // MÉTODOS DE LA CLASE
    /**
     * Carga una nueva partida.
     */
    protected void nuevaPartida(){
        this.tablero=new Tablero(NUM_FILAS,NUM_COLUMNAS);
        actualizaTablero(tablero);
        System.out.println("\n\n----- NUEVA PARTIDA ------\n");
    }


    /**
     * Este método será el fin del juego y dará la opcion de volver a jugar otra partida.
     * @return Si el jugador(es) quiere volver a jugar "true" si no "false".
     */
    protected boolean fin(){
        Scanner input= new Scanner(System.in);
        System.out.println("¿DESEA VOLVER A JUGAR ESTE MODO?(S/N)");
        char respuesta= input.nextLine().toUpperCase().charAt(0);
        return respuesta=='S';
    }

    /**
     * Dibuja el tablero.
     */
    protected void dibujar(){this.tablero.dibujar();}

    /**
     * Pregunta la información al jugador.
     * @return Se devuelve un String con la información del juegador.
     */
    protected String infoJugador(){
        Scanner input= new Scanner(System.in);
        System.out.print("Introduzca su nombre: ");
        String nombre= input.nextLine();
        System.out.println();
        return nombre;
    }

    /**
     * Se actualiza el tablero en los jugadores.
     * @param tablero Se pasa el tablero que se quiere actualizar.
     */
    protected void actualizaTablero(Tablero tablero) {
        this.tablero=tablero;
        for(int i=INICIO_BUCLE;i<NUMERO_JUGADORES;i++) {
            try {
                this.iteradorJugadores.getInfo().setTablero(tablero);
            } catch (StackEmptyException e) {
                System.out.println(e.getMessage());
            }
        }
        this.iteradorJugadores.backToFrist();
    }

    /**
     * Cambia el turno.
     */
    protected void cambiaTurno() {this.turno.cambiaTurno();}

    /**
     * Llama al método hayGanador de tablero para saber si hay ganador.
     * @return Devolverá "true" si hay ganador, si no "false".
     */
    protected boolean hayGanador(Coordenadas coordenadas) {
        return reglas.hayGanador(turno.tieneTurno().getFicha(), coordenadas);
    }

    /**
     * El jugador pone la ficha y se comprueba si gana o empata y si no, se cambia el turno.
     * @return Devuelve si la partida termina.
     */
    protected boolean ponerFicha(){
        boolean finJuego=false;
        this.dibujar();
        System.out.println("Turno de: " +this.turno.nombreJugadorConTurno());
        Coordenadas posicion = null ;
        try {
            posicion=this.turno.tieneTurno().poner();
        }catch (SinFichasException ex) {
            System.out.println(ex.getMessage());
            finJuego = true;
        }finally {
            if (posicion!=null) {
                this.reglas.setTablero(tablero);
                if (this.hayGanador(posicion)) {
                    finJuego = true;
                    this.ganador = this.turno.tieneTurno();
                    this.resultados();
                } else if (this.tablero.tableroLleno()) {
                    finJuego = true;
                    this.ganador = null;
                    this.resultados();
                } else {
                    this.actualizaTablero(this.turno.tieneTurno().getTablero());
                    this.cambiaTurno();
                }
            }
        }
        return finJuego;
    }

}
