/**
 *
 */
package juego;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Iker Garcia Ramirez
 * @date 8/5/2015
 *
 */
public class Juego {

    /*
     * Nivel principiante: 8 × 8 casillas y 10 minas. Nivel intermedio: 16 × 16
     * casillas y 40 minas. Nivel experto: 16 × 30 casillas y 99 minas. Nivel
     * personalizado: en este caso el usuario personaliza su juego eligiendo el
     * número de minas y el tamaño de la cuadrícula
     */
    private static final int TAMANIO_CUADRICULA_PRINCIPIANTE = 8;
    private static final int NUMERO_DE_MINAS_PRINCIPIANTE = 10;
    private static final int TAMANIO_CUADRICULA_INTERMEDIO = 16;
    private static final int NUMERO_DE_MINAS_INTERMEDIO = 40;
    private static final int TAMANIO_CUADRICULA_EXPERTO_X = 30;
    private static final int TAMANIO_CUADRICULA_EXPERTO_Y = 16;
    private static final int NUMERO_DE_MINAS_EXPERTO = 99;
    private int[][] tablero;
    private int[][] resultado;
    private int dificultad; // 0 principiante, 1 intermedio, 2 experto, 3
    // personalizado
    private int filas;
    private int columnas;
    private int bombas;
    private int bombasAcertadas;

    public Juego(int dificultad) {
	this.dificultad = dificultad;
	iniciarVariables(dificultad);
	inicializarArrays();
	colocarMinas();
    }

    /**
     * @return the dificultad
     */
    public int getDificultad() {
	return dificultad;
    }

    /**
     * @param bombasAcertadas
     *            the bombasAcertadas to set
     */
    public void setBombasAcertadas(int bombasAcertadas) {
	this.bombasAcertadas = bombasAcertadas;
    }

    /**
     * @return the bombasAcertadas
     */
    public int getBombasAcertadas() {
	return bombasAcertadas;
    }

    public Juego(int filas, int columnas, int bombas) {
	this.filas = filas;
	this.columnas = columnas;
	this.bombas = bombas;
	bombasAcertadas = 0;
	dificultad = 3;
	inicializarArrays();
	colocarMinas();
    }

    private void inicializarArrays() {
	tablero = new int[filas][columnas];
	resultado = new int[filas][columnas];
	for (int i = 0; i < resultado.length; i++) {
	    Arrays.fill(resultado[i], -2);
	}

    }

    public void iniciarVariables(int dificultad) {
	if (dificultad == 0) {
	    filas = TAMANIO_CUADRICULA_PRINCIPIANTE;
	    columnas = TAMANIO_CUADRICULA_PRINCIPIANTE;
	    bombas = NUMERO_DE_MINAS_PRINCIPIANTE;
	}
	if (dificultad == 1) {
	    filas = TAMANIO_CUADRICULA_INTERMEDIO;
	    columnas = TAMANIO_CUADRICULA_INTERMEDIO;
	    bombas = NUMERO_DE_MINAS_INTERMEDIO;
	}
	if (dificultad == 2) {
	    filas = TAMANIO_CUADRICULA_EXPERTO_Y;
	    columnas = TAMANIO_CUADRICULA_EXPERTO_X;
	    bombas = NUMERO_DE_MINAS_EXPERTO;
	}
	bombasAcertadas = 0;
    }

    public void colocarMinas() {
	Random rnd = new Random();
	for (int i = 0; i < bombas;) {
	    int y = rnd.nextInt(filas);
	    int x = rnd.nextInt(columnas);
	    if (tablero[y][x] != -1) {
		tablero[y][x] = -1;
		ponerContadores(y, x);
		i++;
	    }

	}

    }

    public void verTablero() {
	for (int i = 0; i < filas; i++) {
	    for (int j = 0; j < columnas; j++) {
		System.out.print(tablero[i][j] + "\t");
	    }
	    System.out.println();
	}
    }

    public void verResultado() {
	for (int i = 0; i < filas; i++) {
	    for (int j = 0; j < columnas; j++) {
		System.out.print(resultado[i][j] + "\t");
	    }
	    System.out.println();
	}
    }

    public void ponerContadores(int y, int x) {
	// contadores superiores
	if ((y - 1) >= 0 && (x - 1) >= 0 && tablero[y - 1][x - 1] != -1) {
	    tablero[y - 1][x - 1]++;
	}
	if ((y - 1) >= 0 && (x + 1) < columnas && tablero[y - 1][x + 1] != -1) {
	    tablero[y - 1][x + 1]++;
	}
	if ((y - 1) >= 0 && tablero[y - 1][x] != -1) {
	    tablero[y - 1][x]++;
	}
	// contadores inferiores
	if ((y + 1) < filas && (x - 1) >= 0 && tablero[y + 1][x - 1] != -1) {
	    tablero[y + 1][x - 1]++;
	}
	if ((y + 1) < filas && (x + 1) < columnas
		&& tablero[y + 1][x + 1] != -1) {
	    tablero[y + 1][x + 1]++;
	}
	if ((y + 1) < filas && tablero[y + 1][x] != -1) {
	    tablero[y + 1][x]++;
	}

	// contadores lados
	if ((x + 1) < columnas && tablero[y][x + 1] != -1) {
	    tablero[y][x + 1]++;
	}
	if ((x - 1) >= 0 && tablero[y][x - 1] != -1) {
	    tablero[y][x - 1]++;
	}

    }

    public boolean esMina(int y, int x) {
	if (tablero[y][x] == -1)
	    return true;
	return false;

    }

    public boolean esNumero(int y, int x) {
	if (tablero[y][x] > 0)
	    return true;

	return false;
    }

    /**
     * @return the tablero
     */
    public int[][] getTablero() {
	return tablero;
    }

    /**
     * @return the filas
     */
    public int getFilas() {
	return filas;
    }

    /**
     * @return the columnas
     */
    public int getColumnas() {
	return columnas;
    }

    public void decrementarBombasAcertadas() {
	bombasAcertadas--;
    }

    public void incrementarBombasAcertadas() {
	bombasAcertadas++;
    }

    public boolean completo() {
	if (bombasAcertadas == bombas)
	    return true;

	return false;
    }

    public void mostratBombas() {
	for (int i = 0; i < filas; i++) {
	    for (int j = 0; j < columnas; j++) {
		if (tablero[i][j] == -1) {
		    resultado[i][j] = -1;
		}
	    }
	}
    }

    public void tirar(int y, int x) {

    }

    public static void main(String[] args) {
	Juego j = new Juego(0);
	j.colocarMinas();
	j.verTablero();
	j.mostratBombas();
	j.verResultado();
    }

    /**
     * @return the resultado
     */
    public int[][] getResultado() {
	return resultado;
    }

}
