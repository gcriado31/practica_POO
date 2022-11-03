package etsisi.bs0165;
import java.awt.*;
import java.util.Scanner;

/**
 *  Esta clase representará el tablero, pondrá las fichas, controlará si hay ganador o si se llena.
 */
public class Tablero {

    //ATRIBUTOS
    private int numColumnas;
    private int numFilas;
    private Casilla [][] casillas;
    private final String INTRODUCIR_COLUMNA="INTRODUZCA UNA COLUMNA: ";
    private final String ERROR_COLUMNA_LLENA="LA COLUMNA ESTÁ LLENA\nELIJA OTRA";
    private final String ERROR_COLUMNA_INCORRECTA="ERROR COLUMNA INCORRECTA\nVALORES VALIDOS [0-6]\n"+INTRODUCIR_COLUMNA;
    private final int INICIO_BUCLE=0;
    /**
     * Esta constante es el número de fichas que hay que tener en horizontal, vertical o diagonal para ganar
     */
    private final int NUM_FICHAS_GANADOR=4;

    //CONSTRUCTOR
    public Tablero(int numFilas,int numColumnas){
        this.numFilas=numFilas;
        this.numColumnas=numColumnas;
        iniciarCasillas();
    }

    /**
     * Este método inicia el array de casillas con las casillas en blanco.
     */
    private void iniciarCasillas(){
        this.casillas = new Casilla[numFilas][numColumnas];
        for (int i = INICIO_BUCLE; i < numFilas; i++) {
            for (int j = INICIO_BUCLE; j < numColumnas; j++) {
             this.casillas[i][j]=new Casilla(i,j);
            }
        }
    }

    //GETTERS
    public int getNumColumnas() {
        return numColumnas;
    }

    public int getNumFilas() {
        return numFilas;
    }

    //MÉTODOS
    private boolean isEmpty (int fila, int columna){
        return casillas[fila][columna].isEmpty();
    }

    /**
     * Comprueba si el tablero está lleno.
     * @return Devolverá "true" si está lleno.
     */
    public boolean tableroLleno(){
        int contadorCasillasVacias=0;
        for (int i = INICIO_BUCLE; i < casillas.length; i++) {
            for (int j = INICIO_BUCLE; j < casillas[i].length; j++) {
                if (isEmpty(i,j)){
                    contadorCasillasVacias++;
                }
            }
        }
       return (contadorCasillasVacias==0);
    }

    /**
     * Este método se encarga de poner la ficha en el tablero.
     * @param ficha Se pasa una Ficha para saber qué ficha se ha de poner.
     */
    public void ponerFicha (Ficha ficha){
        int columna=pedirColumna();
        int fila=caeFichaFila(columna);
        while (fila==-1) { // Mientras se seleccione una columna llena se pedirá una nueva y se mostrará un mensaje de error (FUTURA ColumnaLlenaExcepcion)
            System.out.println(ERROR_COLUMNA_LLENA);
            columna=pedirColumna();
            fila=caeFichaFila(columna);
        }
        casillas[fila][columna].setFicha(ficha);
    }

    /**
     * Desliza la ficha por la columna.
     *
     * @param columna Se introduce la columna para que deslice por ella hasta la posición más baja vacía.
     * @return  Devuelve la posición fila mas baja que está vacía. En caso de que esté llena la columna devolverá -1.
     */
    private int caeFichaFila(int columna){
        boolean casillaLlena=false;
        int indicadorFila=0;
        do{
            if(!isEmpty(indicadorFila,columna)){
                casillaLlena=true;
                indicadorFila--; //Restamos 1 ya que esa posición está ocupada
            }else{
                indicadorFila++;
            }
        }while(!casillaLlena && indicadorFila<numFilas);
        if(indicadorFila==numFilas){ //Esto significa que la columna está vacía y ha llegado hasta el final
            indicadorFila=numFilas-1;
        }
        return indicadorFila;
    }

    public void dibujar(){
        System.out.println("-----------------------------------------------------");
        for (int i = INICIO_BUCLE; i < numFilas; i++) {
            for (int j = INICIO_BUCLE; j < numColumnas; j++) {
                System.out.print(casillas[i][j].dibujar()+"\t");
            }
            System.out.println();
        }
        System.out.println("------------------------------------------------------");
    }

    /**
     * Pide la columna.
     * @return Devuelve el valor de la columna después de asegurase de que existe dicha columna.
     */
    private int pedirColumna(){
        Scanner input = new Scanner(System.in);
        System.out.print(INTRODUCIR_COLUMNA);
        int pos=input.nextInt();
        boolean posCorrecta = pos<=numColumnas;
        while(!posCorrecta){   //SEGURAMENTE AQUÍ POSTERIORMENTE IRÁ UNA EXCEPCION "ColumnaIncorrectaException"
            System.out.print(ERROR_COLUMNA_INCORRECTA);
            pos=input.nextInt();
            posCorrecta = pos<=numFilas;
            System.out.println();
        }
        System.out.println();
        return pos;
    }

    /**
     * Este método se encarga de encontrar (si hay) ganador.
     * @param ficha Se pasa una Ficha para buscar si esa ficha es ganadora.
     * @return Devuelve "true" si hay ganador.
     */
    public boolean hayGanador(Ficha ficha){
        boolean hayGanador=false;
        if(checkFilas(ficha)){
            return true;
        } else if (checkColumnas(ficha)) {
            return true;
        }
        return hayGanador;
    }

    /**
     * Busca por filas si hay 4 fichas en horizontal.
     * @param ficha Se pasa una Ficha para buscar si esa ficha es ganadora.
     * @return Devuelve "true" si hay ganador.
     */
    private boolean checkFilas (Ficha ficha){
        int i = INICIO_BUCLE;
        boolean hayganador=false;
        while(i < casillas.length && !hayganador) {
            int contadorCasillas=0;
            for (int j = INICIO_BUCLE; j < casillas[i].length; j++) {
                if(!isEmpty(i,j)){
                    if (casillas[i][j].getFicha().equals(ficha)){
                        contadorCasillas++;
                    }
                }
            }
            if(contadorCasillas>=NUM_FICHAS_GANADOR){
                hayganador=true;
            }else {
                i++;
            }
        }
        return hayganador;
    }

    /**
     * Busca por filas si hay 4 fichas en vertical.
     * @param ficha Se pasa una Ficha para buscar si esa ficha es ganadora.
     * @return Devuelve "true" si hay ganador.
     */
    private boolean checkColumnas (Ficha ficha){
        int i = 0;
        boolean hayganador=false;
        while(i < numColumnas && !hayganador) {
            int contadorCasillas=0;
            for (int j = INICIO_BUCLE; j < numFilas; j++) {
                if(!isEmpty(j,i)){
                    if (casillas[j][i].getFicha().equals(ficha)){
                        contadorCasillas++;
                    }
                }
            }
            if(contadorCasillas>=NUM_FICHAS_GANADOR){
                hayganador=true;
            }else {
                i++;
            }
        }
        return hayganador;
    }

    /**
     * Busca por filas si hay 4 fichas en diagonal.
     * @param ficha Se pasa una Ficha para buscar si esa ficha es ganadora.
     * @return Devuelve "true" si hay ganador.
     */
    public boolean checkDiagonales(Ficha ficha){
        // FALTA POR HACER
        return false;
    }

    public static void main(String[] args) {
        Tablero t1= new Tablero(6,7);
        t1.dibujar();
        t1.ponerFicha(new Ficha('P', Color.BLUE));
        t1.dibujar();
    }

}
