package etsisi.juego;


import etsisi.pilas.*;


/**
 * Esta clase respresenta al jugador, pondrá ficha a través de Tablero y almacena nombre y ficha del jugador.
 */
public class Jugador {
    // ATRIBUTOS
    protected String nombre;
    protected int fichasRestantes;
    protected Ficha ficha;
    protected final String ERROR_TABLERO_LLENO="ERROR: TABLERO LLENO";
    private final int MAX_FICHAS=21;

    //CONSTRUCTORES
    public Jugador (String nombre, Ficha ficha){
        this.nombre=nombre;
        this.ficha=ficha;
        this.fichasRestantes=MAX_FICHAS;
    }

    // SETTERS Y GETTERS

    public String getNombre() {
        return nombre;
    }

    public Ficha getFicha() {
        return this.ficha;
    }

    public void setFichasRestantes(int fichasRestantes) {
        this.fichasRestantes = fichasRestantes;
    }

    //MÉTODOS

    /**
     * Método del jugador para poner su ficha.
     */
    protected Coordenadas poner (Tablero tablero) throws SinFichasException {
        if(!tablero.tableroLleno()){
            this.fichasRestantes--;
            return tablero.ponerFicha(ficha);
        }else if (this.fichasRestantes==0) {
            throw new SinFichasException(this.nombre);
        }else{
           Viewer.printString(ERROR_TABLERO_LLENO);
            return null;
        }
    }
}
