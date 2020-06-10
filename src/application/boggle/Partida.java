package application.boggle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Clase Partida.
 * 
 * Atributos: -maxRondas <-- Almacena el número máximo de rondas que puede tener
 * una partida. -partidasCreadas <-- Almacena un número entero que representa
 * las veces que se han inicializado partidas. -jugadores[] <-- Array de objetos
 * de tipo Jugador. -numRondas <-- Número entero.
 * 
 * Métodos: -iniciarPartida <-- Se encarga de inicializar la partida y gestionar
 * los turnos de los jugadores. -decideGanador <-- Lee la puntuación de los
 * jugadores de la partida y devuelve el que tenga la puntuación más alta.
 * 
 * @author David Fontalba
 * @version 1.1.
 *
 */
public class Partida {
  // Atributos de la clase, el número de partidas creadas.
  private static int MAXRONDAS = 5;
  private static int partidasCreadas = 0;

  // Atributos de la partida.
  public Cubilete cubilete = new Cubilete();
  private Jugador jugadores[];
  private int numRondas;

  /**
   * @param numRondas El número de rondas que se jugará
   * @param jugadores Los jugadores que van a participar
   */
  public Partida(int numRondas, Jugador... jugadores) {
    this.numRondas = Math.min(MAXRONDAS, numRondas);
    this.jugadores = jugadores;
    partidasCreadas++;
  }

  /**
   * iniciarPartida se encarga del desarrollo de los turnos, y al final llama a
   * {@link decideGanador}
   */
  public void iniciarPartida() {

    // Bucle para las rondas
    for (int i = 0; i < this.numRondas; i++) {
      System.out.println("Prepárate, la ronda " + (i + 1) + " va a comenzar.");

      // Bucle para los turnos
      for (int j = 0; j < this.jugadores.length; j++) {
        System.out.println("Es el turno de " + this.jugadores[j] + ".");
        // Añadida tirada de dado y muestra del resultado
        this.cubilete.tirarDados();
        System.out.println(this.cubilete.toString());
        // Quitamos las repeticiones
        Set<String> palabras = jugadores[j].inicioTurno();
        System.out.println("\nFin del turno " + (j + 1) + ".");

        ArrayList<String> palabrasProcesadas = comprueba(palabras);

        System.out.println("Palabras válidas");
        for (String palabraProcesada : palabrasProcesadas) {
          System.out.println(" - " + palabraProcesada);
        }

        jugadores[j].sumaPuntuacion(sumaPuntos(palabrasProcesadas));

        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

      }
      System.out.println("Fin de la ronda " + (i + 1) + ".");

    }

    decideGanador();

  }

  /**
   * decideGanador compara la puntacion de cada jugador y el jugador que tenga más
   * puntuación gana.
   */
  private void decideGanador() {
    ArrayList<Integer> ganadores = new ArrayList<Integer>(); // Almacena la posición del jugador que tiene más
    // puntuación
    int aux = 0; // Almacena la puntuación máxima

    // Bucle para leer la puntuación de los jugadores
    for (int i = 0; i < this.jugadores.length; i++) {

      // Si el jugador a leer tiene más puntuación
      if (this.jugadores[i].getPuntuacion() > aux) {
        ganadores.clear();
        ganadores.add(i);
        aux = this.jugadores[i].getPuntuacion();

        // Si el jugador a leer tiene la misma puntuación
      } else if (this.jugadores[i].getPuntuacion() == aux) {
        ganadores.add(i);
      }

    }

    for (Integer integer : ganadores) {
      Jugador ganador = jugadores[integer];

      ganador.sumarPartidasGanadas();

    }

    // Si hay más de un ganador
    if (ganadores.size() > 1) {
      System.out.print("Felicidades a los ganadores, ");

      for (int i = 0; i < ganadores.size(); i++) {
        // El último ganador de la lista
        if (i + 1 == ganadores.size()) {
          System.out.println("y " + this.jugadores[ganadores.get(i)].getNombre() + ".");

          // El resto de ganadores
        } else {
          System.out.print(this.jugadores[ganadores.get(i)].getNombre() + ", ");
        }

      }
      // Si solo hay un ganador
    } else {
      System.out.println("Felicidades " + this.jugadores[ganadores.get(0)].getNombre() + ", ¡Has ganado!");

    }

    System.out.println("Recuento de puntuaciones: ");
    for (Jugador jugador : this.jugadores) {
      System.out.println(jugador.getNombre() + ": " + jugador.getPuntuacion());
    }
  }

  /**
   * comprueba se encarga de comprobar que las palabras sean correctas y filtra
   * las incorrectas.
   * 
   * @param aFiltrar La lista de palabras no filtradas
   * @return La lista de palabras filtrada
   */
  private ArrayList<String> comprueba(Set<String> aFiltrar) {

    ArrayList<String> palabrasFiltradas = new ArrayList<>();

    for (String palabraNoFiltrada : aFiltrar) {

      if (palabraNoFiltrada.length() < 3 || palabraNoFiltrada.length() > 23) {
        continue;
      }

      palabraNoFiltrada = comprobarExistenciaPalabra(palabraNoFiltrada);

      if (palabraNoFiltrada.isEmpty()) {
        continue;
      }
      palabraNoFiltrada = comprobarMatrizBienFormada(palabraNoFiltrada);

      if (palabraNoFiltrada.isEmpty()) {
        continue;
      }

      palabrasFiltradas.add(palabraNoFiltrada);

    }

    return palabrasFiltradas;
  }

  /**
   * 
   * sumaPuntos se encarga de sumar los puntos de las palabras
   * 
   * @param palabras La lista de palabras que ya ha sido filtrada
   * @return La suma de lo que puntúa cada palabra
   */
  private int sumaPuntos(ArrayList<String> palabras) {

    int resultadoFinal = 0;

    for (String palabra : palabras) {
      switch (palabra.length()) {
      case 0:
      case 1:
      case 2:
      case 3:
        break;
      case 4:
        resultadoFinal += 1;
        break;
      case 5:
        resultadoFinal += 2;
        break;
      case 6:
        resultadoFinal += 3;
        break;
      case 7:
        resultadoFinal += 5;
        break;
      default:
        resultadoFinal += 11;
        break;
      }
    }

    return resultadoFinal;
  }

  /**
   * 
   * comprobarExistenciaPalabra comprueba a través de un api la existencia de la
   * palabra que llega a través del argumento palabraAFiltrar
   * 
   * @param palabraAFiltrar La palabra a comprobar si existe
   * @return Devulve la palabra si existe, en caso contrario, devuelve una
   *         {@link String} vacía
   */
  private String comprobarExistenciaPalabra(String palabraAFiltrar) {

    try {
      Process p = Runtime.getRuntime().exec("python raescript.py " + palabraAFiltrar);

      while (p.isAlive()) {
      }

      if (p.exitValue() > 0)
        return "";
    } catch (IOException e) {
      return "";
    }

    return palabraAFiltrar;

  }

  /**
   * 
   * Compruba si la palabra está bien formada respecto a lo que dicen las normas
   * del Boggle, es decir que que letra de la palabra tenga que estar continua o
   * adyadcente tanto a la siguiente como a la anterior.
   * 
   * @param palabra Palabra para comprobar
   * @return Devulve la palabra si está bien formada, en caso contrario, devuelve
   *         una {@link String} vacía
   */
  private String comprobarMatrizBienFormada(String palabra) {
    char[][] c = cubilete.caras;
    palabra = palabra.toUpperCase();
    String validador = "";

    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 5; y++) {
        int newX = x;
        int newY = y;
        for (int letraPos = 0; letraPos < palabra.length(); letraPos++) {
          if (c[newX][newY] == (palabra.charAt(letraPos))) {

            validador += c[newX][newY];
            if (validador.equals(palabra)) {
              return palabra;
            }
            try {
              if (c[newX + 1][newY] == (palabra.charAt(letraPos + 1))) {
                newX = newX + 1;
                continue;
              }
            } catch (IndexOutOfBoundsException e) {
            }
            try {
              if (c[newX - 1][newY] == (palabra.charAt(letraPos + 1))) {
                newX = newX - 1;
              }
            } catch (IndexOutOfBoundsException e) {
            }
            try {
              if (c[newX][newY + 1] == (palabra.charAt(letraPos + 1))) {
                newY = newY + 1;
                continue;
              }
            } catch (IndexOutOfBoundsException e) {
            }
            try {
              if (c[newX][newY - 1] == (palabra.charAt(letraPos + 1))) {
                newY = newY - 1;
                continue;
              }
            } catch (IndexOutOfBoundsException e) {
            }
            try {
              if (c[newX + 1][newY + 1] == (palabra.charAt(letraPos + 1))) {
                newY = newY + 1;
                newX = newX + 1;
                continue;
              }
            } catch (IndexOutOfBoundsException e) {
            }
            try {
              if (c[newX + 1][newY - 1] == (palabra.charAt(letraPos + 1))) {
                newY = newY - 1;
                newX = newX + 1;
                continue;
              }
            } catch (IndexOutOfBoundsException e) {
            }
            try {
              if (c[newX - 1][newY + 1] == (palabra.charAt(letraPos + 1))) {
                newY = newY + 1;
                newX = newX - 1;
                continue;
              }
            } catch (IndexOutOfBoundsException e) {
            }
            try {
              if (c[newX - 1][newY - 1] == (palabra.charAt(letraPos + 1))) {
                newY = newY - 1;
                newX = newX - 1;
                continue;
              }
            } catch (IndexOutOfBoundsException e) {
            }
          } else {
            validador = "";
            break;
          }
          letraPos++;

        }

      }
    }

    return "";

  }

  public void guardarArchivo() throws IOException {
    for (Jugador jugador : jugadores) {
      jugador.guardarArchivo();
    }
  }

  /**
   * @return El número de partidas creadas
   */
  public static int getPartidasCreadas() {
    return partidasCreadas;
  }
}