package Laboratorio07;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.*;
import java.util.Calendar;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;



class EjemploTemperaturaProvincia {
  public static void main(String[] args) {
    int                numHebras, codProvincia, desp;
    String             nombreFichero = "";
    long               t1, t2, tt[];
    double             ts, tp;
    PuebloMaximaMinimaS MaxMinS;
    PuebloMaximaMinima MaxMinH;
    
    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 3 ) {
      System.out.println( "ERROR: numero de argumentos incorrecto.");
      System.out.println( "Uso: java programa <numHebras> <provincia> <desplazamiento>" );
      System.exit( -1 );
    }
    try {
      numHebras    = Integer.parseInt( args[ 0 ] );
      codProvincia = Integer.parseInt( args[ 1 ] );
      desp         = Integer.parseInt( args[ 2 ] );
    } catch( NumberFormatException ex ) {
      numHebras    = -1;
      codProvincia = -1;
      desp         = -1;
      System.out.println( "ERROR: Numero de entrada incorrecto." );
      System.exit( -1 );
    }
    if (numHebras <= 0) {
      System.out.println( "ERROR: El numero de Hebras debe ser un numero entero mayor que 0." );
      System.exit( -1 );
    }
    if ((codProvincia < 1) || (codProvincia > 50)) {
      System.out.println( "ERROR: El codigo de la provincia debe ser un numero entero " +
                          "comprendido entre 1 y 50." );
      System.exit( -1 );
    }
    if ((desp < 0) || (desp >= 7)) {
      System.out.println( "ERROR: El desplazamiento debe ser un numero entero comprendido " +
                          "entre 0 y 6." );
      System.exit( -1 );
    }
    if (codProvincia < 10) {
      nombreFichero = "codPueblos_0" + codProvincia + ".txt";
    } else {
      nombreFichero = "codPueblos_"  + codProvincia + ".txt";
    }
    System.out.println();
    System.out.println( "Obtiene el pueblo de una provincia con mayor diferencia " +
                        "de temperatura." );
    
    // Seleccion del dia elegido
    String fecha;
    Calendar c = Calendar.getInstance();
    Integer dia, mes, anyo;
    
    c.add(Calendar.DAY_OF_MONTH, desp);
    dia = c.get(Calendar.DATE);
    mes = c.get(Calendar.MONTH) + 1;
    anyo = c.get(Calendar.YEAR);
    fecha = String.format("%02d", anyo) + "-" + String.format("%02d", mes) + "-" +
    String.format("%02d", dia);
    System.out.println(fecha);
    
    //
    // Implementacion secuencial sin temporizar.
    //
    MaxMinS = new PuebloMaximaMinimaS();
    MaxMinH = new PuebloMaximaMinima();
    File f = new File(nombreFichero);
    if (f.exists()) {
      obtenMayorDiferenciaDeFichero (nombreFichero, fecha, codProvincia, MaxMinS, MaxMinH, 
                                      0, numHebras);
    } else {
      obtenMayorDiferenciaAFichero_Secuencial (nombreFichero, fecha, codProvincia, MaxMinS);
    }
    System.out.println( "  Pueblo: " + MaxMinS.damePueblo() + " , Maxima = " +
                       MaxMinS.dameTemperaturaMaxima() + " , Minima = " +
                       MaxMinS.dameTemperaturaMinima() );
    
    //
    // Implementacion secuencial.
    //
    System.out.println();
    t1 = System.nanoTime();
    MaxMinS = new PuebloMaximaMinimaS();
    MaxMinH = new PuebloMaximaMinima();
    obtenMayorDiferenciaDeFichero (nombreFichero, fecha, codProvincia, MaxMinS, MaxMinH, 
                                      0, numHebras);
    t2 = System.nanoTime();
    ts = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion secuencial.                           " );
    System.out.println( " Tiempo(s): " + ts );
    System.out.println( "  Pueblo: " + MaxMinS.damePueblo() + " , Maxima = " +
                       MaxMinS.dameTemperaturaMaxima() + " , Minima = " +
                       MaxMinS.dameTemperaturaMinima() );
    
    //
    // Implementacion paralela: Gestion Propia.
    //
    System.out.println();
    t1 = System.nanoTime();
    MaxMinH = new PuebloMaximaMinima();
    obtenMayorDiferenciaDeFichero (nombreFichero, fecha, codProvincia, MaxMinS, MaxMinH,
                                      1, numHebras);
    t2 = System.nanoTime();
    tp = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela: Gestion Propia.     " );
    System.out.println( " Tiempo(s): " + tp + " , Incremento: " + ts/tp );
    System.out.println( "  Pueblo: " + MaxMinS.damePueblo() + " , Maxima = " +
            MaxMinS.dameTemperaturaMaxima() + " , Minima = " +
            MaxMinS.dameTemperaturaMinima() );

    //
    // Implementacion paralela: Thread Pool isTerminated.
    //
    System.out.println();
    t1 = System.nanoTime();
    MaxMinH = new PuebloMaximaMinima();
    obtenMayorDiferenciaDeFichero (nombreFichero, fecha, codProvincia, MaxMinS, MaxMinH,
            2, numHebras);
    t2 = System.nanoTime();
    tp = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela: Thread Pool isTerminated.     " );
    System.out.println( " Tiempo(s): " + tp + " , Incremento: " + ts/tp );
    System.out.println( "  Pueblo: " + MaxMinS.damePueblo() + " , Maxima = " +
            MaxMinS.dameTemperaturaMaxima() + " , Minima = " +
            MaxMinS.dameTemperaturaMinima() );
    //
    // Implementacion paralela: Thread Pool con awaitTermination.
    //

    System.out.println();
    t1 = System.nanoTime();
    MaxMinH = new PuebloMaximaMinima();
    obtenMayorDiferenciaDeFichero (nombreFichero, fecha, codProvincia, MaxMinS, MaxMinH,
            3, numHebras);
    t2 = System.nanoTime();
    tp = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela: Thread Pool awaitTermination.     " );
    System.out.println( " Tiempo(s): " + tp + " , Incremento: " + ts/tp );
    System.out.println( "  Pueblo: " + MaxMinS.damePueblo() + " , Maxima = " +
            MaxMinS.dameTemperaturaMaxima() + " , Minima = " +
            MaxMinS.dameTemperaturaMinima() );
    
    //
    // Implementacion paralela: Thread Pool con Future.
    //
    System.out.println();
    t1 = System.nanoTime();
    MaxMinH = new PuebloMaximaMinima();
    obtenMayorDiferenciaDeFichero (nombreFichero, fecha, codProvincia, MaxMinS, MaxMinH,
            4, numHebras);
    t2 = System.nanoTime();
    tp = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela: Thread Pool con Future.     " );
    System.out.println( " Tiempo(s): " + tp + " , Incremento: " + ts/tp );
    System.out.println( "  Pueblo: " + MaxMinS.damePueblo() + " , Maxima = " +
            MaxMinS.dameTemperaturaMaxima() + " , Minima = " +
            MaxMinS.dameTemperaturaMinima() );
    
  }
  
  // --------------------------------------------------------------------------
  public static void obtenMayorDiferenciaAFichero_Secuencial (String nombreFichero, 
                       String fecha, int codProvincia, PuebloMaximaMinimaS MaxMin) {
    FileWriter fichero = null;
    PrintWriter pw = null;
    
    // Verifica todas los codigos de pueblos y escribe el fichero
    try
    {
      // Apertura del fichero y creacion de FileWriter para poder
      // hacer una lectura comoda (disponer del metodo readLine()).
      
      fichero = new FileWriter(nombreFichero);
      pw = new PrintWriter(fichero);
      
      for (int i=codProvincia*1000; i<(codProvincia+1)*1000; i++){
        if (ProcesaPueblo(fecha, i, MaxMin, false) == true) {
          pw.println(i);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        // Nuevamente aprovechamos el finally para
        // asegurarnos que se cierra el fichero.
        if (null != fichero)
          fichero.close();
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
  }
  
  // --------------------------------------------------------------------------
  
  public static void obtenMayorDiferenciaDeFichero (String nombreFichero, String fecha,
                                                    int codProvincia, PuebloMaximaMinimaS MaxMinS, PuebloMaximaMinima MaxMinH,
                                                    int opcion, int numHebras) {
    File fichero = null;
    FileReader fr = null;
    BufferedReader br = null;
    
    // Procesa el fichero
    try
    {
      // Apertura del fichero y creacion de BufferedReader para poder
      // hacer una lectura comoda (disponer del metodo readLine()).
      fichero = new File (nombreFichero);
      fr = new FileReader (fichero);
      br = new BufferedReader(fr);
      
      String           linea;
      ExecutorService  exec;
      switch (opcion) {
        case 0:  // Caso secuencial
          while( ( linea = br.readLine() ) != null ) {
            int codPueblo = Integer.parseInt(linea);
            ProcesaPueblo(fecha, codPueblo, MaxMinS, false);
          }
          break;
        case 1:  // Gestion Propia
          LinkedBlockingQueue<TareaEnColaGestionPropia> tareas = new LinkedBlockingQueue();
          HebraConsumidora[] t = new HebraConsumidora[numHebras];
          for (int i = 0; i<numHebras;i++){
            t[i] = new HebraConsumidora(tareas, fecha, MaxMinH );
            t[i].start();
          }
          while( ( linea = br.readLine() ) != null ) {
            int codPueblo = Integer.parseInt(linea);
            tareas.add(new TareaEnColaGestionPropia(codPueblo));
          }
          for (int i = 0; i<numHebras;i++){
            tareas.add(new TareaEnColaGestionPropia(-1));
          }
          for (int i = 0; i<numHebras;i++){
            t[i].join();
          }

          break;
        case 2: // ThreadPools con isTerminated
          exec = Executors.newFixedThreadPool ( numHebras );
          while( ( linea = br.readLine() ) != null ) {
            int codPueblo = Integer.parseInt(linea);
            exec.execute ( new TareaPool(codPueblo, fecha, MaxMinH ) ) ;
          }
          exec.shutdown() ;
          while (!exec.isTerminated()){}
          break;
        case 3: // ThreadPools con awaitTermination
          exec = Executors.newFixedThreadPool(numHebras);
          while( ( linea = br.readLine() ) != null ) {
            int codPueblo = Integer.parseInt(linea);
            exec.execute( new TareaPool(codPueblo, fecha, MaxMinH ) ) ;
          }
          exec.shutdown() ;
          try {
            while( !exec.awaitTermination(2L , TimeUnit.MILLISECONDS)){ }
          } catch ( InterruptedException ex ) {
            ex.printStackTrace () ;
          }
          break;
        case 4: // ThreadPools + con Future
          Future<PuebloMaximaMinima> f;
          int numTareas = 0;
          exec = Executors.newFixedThreadPool(numHebras);
          ArrayList < Future< PuebloMaximaMinima >> alf = new ArrayList < Future < PuebloMaximaMinima > >();

          while( ( linea = br.readLine() ) != null ) {
            int codPueblo = Integer.parseInt(linea);
            numTareas++;
            f = exec.submit ( new TareaPoolCallable(codPueblo, fecha, MaxMinH) ) ;
            alf.add( f );
          }
          exec . shutdown () ;
          for ( int i = 0; i < numTareas ; i ++ ) {
          try {
            f = alf.get ( i ) ;
            f.get();
            } catch ( ExecutionException ex ) {
            ex . printStackTrace () ;
            } catch ( InterruptedException ex ) {
            ex . printStackTrace () ;
            }
          }
          break;
        default:
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      // En el finally se cierra el fichero, para asegurar
      // que el cierre se completa tanto si todo va bien 
      // como si activa una excepcion.
      try{
        if( null != fr ){
          fr.close();
        }
      }catch (Exception e2){
        e2.printStackTrace();
      }
    }
  }
  
  // --------------------------------------------------------------------------
  public static boolean ProcesaPueblo (String fecha, int codPueblo, PuebloMaximaMinima MaxMin,
                                       boolean imprime) {
    URL            url;
    InputStream    is = null;
    BufferedReader br;
    String         line, poblacion = new String (), provincia = new String ();
    int            state, num[]=new int[2];
    boolean        res = false;
    
    // Procesamiento de la informacion XML asociada a codPueblo
    // Actualizacion de MaxMin de acuerdo a los valores obtenidos
    try {
      String urlStr = "https://www.aemet.es/xml/municipios/localidad_" +
                         String.format("%05d",codPueblo)+ ".xml";
      url = new URL(urlStr);
      is  = url.openStream();  // throws an IOException
      br  = new BufferedReader(new InputStreamReader(is));
      if (imprime) System.out.println(urlStr);
      
      state = 0;
      while (((line = br.readLine()) != null) && (state < 6)) {
        //        System.out.println (line);
        if ((state == 0) && (line.contains ("nombre"))) {
          poblacion=line.split(">")[1].split("<")[0].split("/")[0];
          state++;
        } else if ((state == 1) && (line.contains ("provincia"))) {
          provincia=line.split(">")[1].split("<")[0].split("/")[0];
          state++;
        } else if ((state == 2) && (line.contains (fecha))) {
          state++;
        } else if ((state == 3) && (line.contains ("temperatura"))) {
          state++;
        } else if ((state > 3) && ((line.contains ("maxima")) || (line.contains ("minima")))) {
          num[state-4] = Integer.parseInt (line.split(">")[1].split("<")[0]);
          state++;
        }
      }
      // System.out.println("(" + codPueblo + ") " + poblacion + "(" + provincia + ") => " +
      //                    "(" + num[0] + " , " + num[1] + ")");
      MaxMin.actualizaMaxMin (poblacion, codPueblo, num[0], num[1]);
      res = true;
    } catch (MalformedURLException mue) {
      mue.printStackTrace();
    } catch (IOException ioe) {
      //      ioe.printStackTrace();
    } finally {
      try {
        if (is != null) is.close();
      } catch (IOException ioe) {
        // nothing to see here
      }
    }
    return res;
  }

  public static boolean ProcesaPueblo (String fecha, int codPueblo, PuebloMaximaMinimaS MaxMin,
                                       boolean imprime) {
    URL            url;
    InputStream    is = null;
    BufferedReader br;
    String         line, poblacion = new String (), provincia = new String ();
    int            state, num[]=new int[2];
    boolean        res = false;

    // Procesamiento de la informacion XML asociada a codPueblo
    // Actualizacion de MaxMin de acuerdo a los valores obtenidos
    try {
      String urlStr = "https://www.aemet.es/xml/municipios/localidad_" +
              String.format("%05d",codPueblo)+ ".xml";
      url = new URL(urlStr);
      is  = url.openStream();  // throws an IOException
      br  = new BufferedReader(new InputStreamReader(is));
      if (imprime) System.out.println(urlStr);

      state = 0;
      while (((line = br.readLine()) != null) && (state < 6)) {
        //        System.out.println (line);
        if ((state == 0) && (line.contains ("nombre"))) {
          poblacion=line.split(">")[1].split("<")[0].split("/")[0];
          state++;
        } else if ((state == 1) && (line.contains ("provincia"))) {
          provincia=line.split(">")[1].split("<")[0].split("/")[0];
          state++;
        } else if ((state == 2) && (line.contains (fecha))) {
          state++;
        } else if ((state == 3) && (line.contains ("temperatura"))) {
          state++;
        } else if ((state > 3) && ((line.contains ("maxima")) || (line.contains ("minima")))) {
          num[state-4] = Integer.parseInt (line.split(">")[1].split("<")[0]);
          state++;
        }
      }
      // System.out.println("(" + codPueblo + ") " + poblacion + "(" + provincia + ") => " +
      //                    "(" + num[0] + " , " + num[1] + ")");
      MaxMin.actualizaMaxMin (poblacion, codPueblo, num[0], num[1]);
      res = true;
    } catch (MalformedURLException mue) {
      mue.printStackTrace();
    } catch (IOException ioe) {
      //      ioe.printStackTrace();
    } finally {
      try {
        if (is != null) is.close();
      } catch (IOException ioe) {
        // nothing to see here
      }
    }
    return res;
  }
}
// ============================================================================
class PuebloMaximaMinimaS {
  // ============================================================================
  String poblacion;
  int    codigo, max, min;


  // --------------------------------------------------------------------------
  public PuebloMaximaMinimaS() {
    poblacion = null;
    codigo    = -1;
    max       = -1;
    min       = -1;
  }

  // --------------------------------------------------------------------------
  public void actualizaMaxMin( String poblacion, int codigo, int max, int min ) {
    if ((this.poblacion == null) || ((this.max-this.min) < (max-min)) ||
            (((this.max-this.min) == (max-min)) && (this.min > min)) ||
            (((this.max-this.min) == (max-min)) && (this.min == min) && (this.codigo < codigo))
    ) {
      this.poblacion = poblacion;
      this.codigo = codigo;
      this.max = max;
      this.min = min;
    }
  }

  // --------------------------------------------------------------------------
  public String damePueblo() {
    return this.poblacion + "(" + this.codigo + ")";
  }

  // --------------------------------------------------------------------------
  public int dameCodigo() {
    return this.codigo;
  }

  // --------------------------------------------------------------------------
  public int dameTemperaturaMaxima() {
    return this.max;
  }

  // --------------------------------------------------------------------------
  public int dameTemperaturaMinima() {
    return this.min;
  }
}
// ============================================================================
class PuebloMaximaMinima {
  // ============================================================================
  String poblacion;
  int    codigo, max, min;
  
  
  // --------------------------------------------------------------------------
  public PuebloMaximaMinima() {
    poblacion = null;
    codigo    = -1;
    max       = -1;
    min       = -1;
  }
  
  // --------------------------------------------------------------------------
  public synchronized void actualizaMaxMin( String poblacion, int codigo, int max, int min ) {
    if ((this.poblacion == null) || ((this.max-this.min) < (max-min)) ||
        (((this.max-this.min) == (max-min)) && (this.min > min)) ||
        (((this.max-this.min) == (max-min)) && (this.min == min) && (this.codigo < codigo))
        ) {
      this.poblacion = poblacion;
      this.codigo = codigo;
      this.max = max;
      this.min = min;
    }
  }
  
  // --------------------------------------------------------------------------
  public synchronized String damePueblo() {
    return this.poblacion + "(" + this.codigo + ")";
  }
  
  // --------------------------------------------------------------------------
  public synchronized int dameCodigo() {
    return this.codigo;
  }
  
  // --------------------------------------------------------------------------
  public synchronized int dameTemperaturaMaxima() {
    return this.max;
  }
  
  // --------------------------------------------------------------------------
  public synchronized int dameTemperaturaMinima() {
    return this.min;
  }
}

// ============================================================================
class TareaEnColaGestionPropia {
  // ============================================================================
  boolean esVeneno;
  int codPueblo;

  public TareaEnColaGestionPropia(int codPueblo) {
    this.codPueblo = codPueblo;
    this.esVeneno = (codPueblo == -1);
  }

}

// ============================================================================
class TareaPool implements Runnable {
  // ============================================================================
  int codPueblo;
  String fecha;
  PuebloMaximaMinima MaxMinS;

  public TareaPool(int codPueblo, String fecha, PuebloMaximaMinima MaxMinS) {
    this.codPueblo = codPueblo;
    this.fecha = fecha;
    this.MaxMinS = MaxMinS;

  }

  @Override
  public void run() {
    EjemploTemperaturaProvincia.ProcesaPueblo(fecha, codPueblo, MaxMinS, false);
  }
}

class TareaPoolCallable implements Callable {
  // ============================================================================
  int codPueblo;
  String fecha;
  PuebloMaximaMinima MaxMinS;

  public TareaPoolCallable(int codPueblo, String fecha, PuebloMaximaMinima MaxMinS) {
    this.codPueblo = codPueblo;
    this.fecha = fecha;
    this.MaxMinS = MaxMinS;

  }

  @Override
  public PuebloMaximaMinima call() throws Exception {
    EjemploTemperaturaProvincia.ProcesaPueblo(fecha, codPueblo, MaxMinS, false);
    return MaxMinS;
  }
}

class HebraConsumidora extends Thread {
  public LinkedBlockingQueue<TareaEnColaGestionPropia> tareas;
  String fecha;
  PuebloMaximaMinima MaxMinS;

  public HebraConsumidora(LinkedBlockingQueue<TareaEnColaGestionPropia> tareas, String fecha,
                          PuebloMaximaMinima MaxMinS) {
    this.tareas = tareas;
    this.fecha = fecha;
    this.MaxMinS = MaxMinS;
  }

  @Override
  public void run() {
    boolean fin = false;
    TareaEnColaGestionPropia tarea;
    while(!fin){
      try {
        tarea = tareas.take();
        fin = tarea.esVeneno;
        if(!tarea.esVeneno) EjemploTemperaturaProvincia.ProcesaPueblo(fecha, tarea.codPueblo, MaxMinS, false);

      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

}