package juego;

/**
 * Esta clase crea un cubilete de dados con las caras que salgan aleatoriamente
 * Tiene los siguientes métodos:
 *  tirarDados()
 *  toString()
 * @author Antonio García García
 * @version 1.0
 */
public class Cubilete {

  /**
   * Dados
   */
  private final char d1[] = { 'Q', 'B', 'Z', 'J', 'X', 'L' };
  private final char d2[] = { 'T', 'O', 'U', 'O', 'T', 'O' };
  private final char d3[] = { 'O', 'V', 'C', 'R', 'G', 'R' };
  private final char d4[] = { 'A', 'A', 'A', 'F', 'S', 'R' };
  private final char d5[] = { 'A', 'U', 'M', 'E', 'E', 'O' };

  private final char d6[] = { 'E', 'H', 'L', 'R', 'D', 'O' };
  private final char d7[] = { 'N', 'H', 'D', 'T', 'H', 'O' };
  private final char d8[] = { 'L', 'H', 'N', 'R', 'O', 'D' };
  private final char d9[] = { 'A', 'D', 'A', 'I', 'S', 'R' };
  private final char d10[] = { 'U', 'I', 'F', 'A', 'S', 'R' };

  private final char d11[] = { 'T', 'E', 'L', 'P', 'C', 'I' };
  private final char d12[] = { 'S', 'S', 'N', 'S', 'E', 'U' };
  private final char d13[] = { 'R', 'I', 'Y', 'P', 'R', 'H' };
  private final char d14[] = { 'D', 'O', 'R', 'D', 'L', 'N' };
  private final char d15[] = { 'C', 'C', 'Ñ', 'N', 'S', 'T' };

  private final char d16[] = { 'T', 'T', 'O', 'T', 'E', 'M' };
  private final char d17[] = { 'S', 'C', 'T', 'I', 'E', 'P' };
  private final char d18[] = { 'E', 'A', 'N', 'D', 'N', 'N' };
  private final char d19[] = { 'M', 'N', 'N', 'E', 'A', 'G' };
  private final char d20[] = { 'U', 'O', 'T', 'O', 'Ñ', 'N' };

  private final char d21[] = { 'A', 'E', 'A', 'E', 'E', 'H' };
  private final char d22[] = { 'Y', 'I', 'F', 'P', 'S', 'R' };
  private final char d23[] = { 'E', 'E', 'E', 'E', 'M', 'A' };
  private final char d24[] = { 'I', 'T', 'A', 'T', 'I', 'E' };
  private final char d25[] = { 'E', 'T', 'I', 'L', 'A', 'C' };

  /**
   * Matriz con todos los dados
   */
  private final char dados[][] = {d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25};  

  /**
   * Matriz donde se almacenarán las caras de los dados
   */
  char caras[][] = new char[5][5];

  /**
   * Crea un array bidimensional con las letras que salgan de haber "tirado" el dado
   * @return Un array bidimensional con las caras de los dados resultantes de "tirar" los dados
   */
  public char[][] tirarDados() {
    int dado = 0;
    for (int fila=0;fila<5;fila++) {
      for (int columna=0;columna<5;columna++) {
        caras[fila][columna] = (char) (dados[dado][(int) (Math.random()*5+1)]);
        dado++;
      }
    }
    return caras;
  }

  /**
   * Imprime por pantalla el objeto de la manera indicada en el método
   * @return Una cadena para ver graficamente el cubilete
   */
  public String toString() {
    String cadenaFinal = "\n";
    for (int fila=0;fila<5;fila++) {
      for (int columna=0;columna<5;columna++) {
        cadenaFinal += caras[fila][columna]+" ";
      }
      cadenaFinal += "\n";
    }
    return cadenaFinal;
  }

}