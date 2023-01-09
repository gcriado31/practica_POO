package etsisi.juego;

/**
 * Esta clase almacena el fichaJugador y las coordenadas donde se ha puesto la ficha.
 * Nos ayudará en el patrón undo-redo.
 */
public class Movimiento {
    // ATRIBUTOS
    private Ficha fichaJugador;
    private Coordenadas coordenadasFichaIntroducida;

    // CONSTRUCTOR
    public Movimiento(Ficha tablero, Coordenadas fichaIntroducida){
        this.fichaJugador =tablero;
        this.coordenadasFichaIntroducida =fichaIntroducida;
    }

    // GETTERS Y SETTERS
    public Ficha getFichaJugador() {
        return fichaJugador;
    }

    public void setFichaJugador(Ficha fichaJugador) {
        this.fichaJugador = fichaJugador;
    }

    public Coordenadas getCoordenadasFichaIntroducida() {
        return coordenadasFichaIntroducida;
    }

    public void setCoordenadasFichaIntroducida(Coordenadas coordenadasFichaIntroducida) {
        this.coordenadasFichaIntroducida = coordenadasFichaIntroducida;
    }

}
