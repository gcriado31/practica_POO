package etsisi.juego;

/**
 * Esta clase almacenará la fila y columna donde cae la ficha que se ha puesto
 */
public class Coordenadas {
    // ATRIBUTOS
    private int fila;
    private int columna;


    // CONSTRUCTOR
    public Coordenadas(int fila, int columna){
        this.fila=fila;
        this.columna=columna;
    }

    // SETTERS Y GETTERS
    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

}
