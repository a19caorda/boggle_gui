package application.boggle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

/**
 * La clase Jugador se encarga de mantener el estado de cada jugador y de
 * iniciar el turno
 * 
 * @author David Castilla Ortiz
 * @version 1.1.0
 * 
 */
public class Jugador {

  //private final int TIEMPO_TURNO_SEC = 180;
  private int puntuacion = 0;
  private Set<String> palabras;

  @Expose
  private String nombre;
  @Expose
  private int puntuacion_maxima;
  @Expose 
  private int puntuacion_acumulada;
  @Expose
  private int partidas_jugadas;
  @Expose
  private int partidas_ganadas = 0;
  @Expose
  private int partidas_perdidas = 0;
  @Expose
  private JsonArray partidas;

  /**
   * Constructor para crear un jugador
   * 
   * @param nombre Nombre que se le dará al Jugador.
   */
  public Jugador(String nombre) {
    this.nombre = nombre;
  }

  public int getPuntuacion_maxima() {
    return puntuacion_maxima;
  }

  public int getPuntuacion_acumulada() {
    return puntuacion_acumulada;
  }

  public int getPartidas_jugadas() {
    return partidas_jugadas;
  }

  public int getPartidas_ganadas() {
    return partidas_ganadas;
  }

  public int getPartidas_perdidas() {
    return partidas_perdidas;
  }

  public JsonArray getPartidas() {
    return partidas;
  }

  /**
   * @return el nombre del jugador
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * @param puntuacion la puntuacion a poner
   */
  public void setPuntuacion(int puntuacion) {
    this.puntuacion = puntuacion;
  }

  /**
   * @param puntuacion la puntuacion a sumar
   */
  public void sumaPuntuacion(int puntuacion) {
    this.puntuacion += puntuacion;
  }
  

  /**
   * @return la puntuacion del jugador
   */
  public int getPuntuacion() {
    return puntuacion;
  }

  public File getHistorialArchivo() {
    return new File("boggleHistorial/" + nombre + ".json");
  }
  public File getImageArchivo() {
    return new File("boggleImages/" + nombre + ".jpg");
  }

  /**
   * inicioTurno se encarga de iniciar el turno y seguir pidiendo palabras, y por
   * último devolver una colección de palabras únicas.
   */
  public Set<String> inicioTurno() {

    final ArrayList<String> palabras = new ArrayList<>();
    // final AtomicBoolean isGettingWords = new AtomicBoolean(true);

    /*
      Thread wordsThread = new Thread(new Runnable() {
      
      @Override public void run() { while (isGettingWords.get()) {
      System.out.printf("Palabra %d: ", palabras.size() + 1); try { String
      nuevaPalabra = Teclado.readString(); palabras.add(nuevaPalabra);
      
      } catch (NoSuchElementException e) { } catch (IndexOutOfBoundsException e) {
      } } }
      
      });

    try {
      wordsThread.start();
      Thread.sleep(1000 * TIEMPO_TURNO_SEC);

      Robot r = new Robot();
      r.keyPress(KeyEvent.VK_ENTER);
      r.keyRelease(KeyEvent.VK_ENTER);

      isGettingWords.set(false);

      // wordsThread.interrupt();

    } catch (InterruptedException | AWTException e) {
      return new HashSet<String>();
    }
    */

    return new HashSet<String>(palabras);

  }
  
  void sumarPartidasGanadas() {
    partidas_ganadas++;
  }

  public void guardarArchivo(boolean esGanador) throws IOException {

    File f = getHistorialArchivo();
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    if (f.exists()) {

      Jugador j = gson.fromJson(new FileReader(f), this.getClass());

      j.partidas_jugadas++;
      j.puntuacion_acumulada += puntuacion;
      j.partidas_ganadas += esGanador ? 1 : 0;
      j.partidas_perdidas = j.partidas_jugadas - j.partidas_ganadas;
      j.puntuacion_maxima = j.puntuacion_maxima > puntuacion ? j.puntuacion_maxima : puntuacion;
      
      JsonObject p = new JsonObject();
      p.addProperty("puntuacion", puntuacion);
      p.addProperty("fecha", new Date().toString());
      
      j.partidas.add(p);
      
      String json = gson.toJson(j);
      BufferedWriter bf = new BufferedWriter(new FileWriter(f));
      bf.write(json);
      bf.close();

    } else {

      BufferedWriter bf = new BufferedWriter(new FileWriter(f));
      partidas_jugadas = 1;
      puntuacion_acumulada = puntuacion;
      puntuacion_maxima = puntuacion;
      partidas_ganadas = esGanador ? 1 : 0;
      partidas_perdidas = partidas_jugadas - partidas_ganadas;

      JsonObject j = new JsonObject();
      j.addProperty("puntuacion", puntuacion);
      j.addProperty("fecha", new Date().toString());
      
      partidas = new JsonArray();
      partidas.add(j);
      
      String json = gson.toJson(this);
      bf.write(json);
      bf.close();

    }

  }

  @Override
  public String toString() {

    return this.nombre;
  }

  public void addWord(String text) {
    if (palabras == null) palabras = new HashSet<>();
    palabras.add(text);
  }

}