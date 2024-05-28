package Laboratorio066;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

 import java.util.concurrent.*;
 import java.util.concurrent.atomic.*;
 import java.util.Map;
 import java.util.stream.*;
 import java.util.function.*;
 import static java.util.stream.Collectors.*;
 import java.util.Comparator.*;

// ============================================================================
class EjemploPalabraMasUsada {
// ============================================================================

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    long                     t1, t2;
    double                   tt;
    int                      numHebras;
    String                   nombreFichero, palabraActual;
    Vector<String>           vectorLineas;
    HashMap<String,Integer>  hmCuentaPalabras;
    HashMap<String,Integer>  maCuentaPalabras;
    Hashtable<String,Integer>  mhCuentaPalabras;
    ConcurrentHashMap<String, Integer> chCuentaPalabras;
    ConcurrentHashMap<String, Integer> ch2CuentaPalabras;
    ConcurrentHashMap<String, AtomicInteger> ch3CuentaPalabras;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.err.println( "Uso: java programa <numHebras> <fichero>" );
      System.exit( -1 );
    }
    try {
      numHebras     = Integer.parseInt( args[ 0 ] );
      nombreFichero = args[ 1 ];
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      nombreFichero = "";
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }

    // Lectura y carga de lineas en "vectorLineas".
    vectorLineas = readFile( nombreFichero );
    System.out.println( "Numero de lineas leidas: " + vectorLineas.size() );
    System.out.println();

    //
    // Implementacion secuencial sin temporizar.
    //
    hmCuentaPalabras = new HashMap<String,Integer>( 1000, 0.75F );
    for( int i = 0; i < vectorLineas.size(); i++ ) {
      // Procesa la linea "i".
      String[] palabras = vectorLineas.get( i ).split( "\\W+" );
      for( int j = 0; j < palabras.length; j++ ) {
        // Procesa cada palabra de la linea "i", si es distinta de blanco.
        palabraActual = palabras[ j ].trim();
        if( palabraActual.length() > 0 ) {
          contabilizaPalabra( hmCuentaPalabras, palabraActual );
        }
      }
    }

    //
    // Implementacion paralela 1: Uso de synchronizedMap.
    //
    t1 = System.nanoTime();
    // ...
    maCuentaPalabras = new HashMap<String,Integer>( 1000, 0.75F );
    MiHebra1 ma[] = new MiHebra1[numHebras];
    for(int i = 0; i<numHebras; i++){
      ma[i] = new MiHebra1(i, numHebras, vectorLineas, maCuentaPalabras);
      ma[i].start();
    }
    try{
      for (int i = 0; i < numHebras ; i++) {
        ma[i].join();
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela 1: " );
    imprimePalabraMasUsadaYVeces( maCuentaPalabras );
    System.out.println( " Tiempo(s): " + tt  + " , Incremento " + tt/t2);
    System.out.println( "Num. elems. tabla hash: " + maCuentaPalabras.size());
    System.out.println();

    //
    // Implementacion paralela 2: Uso de Hashtable.
    //
    // ...
    t1 = System.nanoTime();
    // ...
    mhCuentaPalabras = new Hashtable<String,Integer>( 1000, 0.75F );
    MiHebra_2 mh[] = new MiHebra_2[numHebras];
    for(int i = 0; i<numHebras; i++){
      mh[i] = new MiHebra_2(vectorLineas, i, mhCuentaPalabras ,numHebras);
      mh[i].start();
    }
    try{
      for (int i = 0; i < numHebras ; i++) {
        mh[i].join();
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela 2: " );
    imprimePalabraMasUsadaYVeces( mhCuentaPalabras );
    System.out.println( " Tiempo(s): " + tt  + " , Incremento " + tt/t2);
    System.out.println( "Num. elems. tabla hash: " + mhCuentaPalabras.size());
    System.out.println();
    //
    // Implementacion paralela 3: Uso de ConcurrentHashMap
    //
    // ...
    t1 = System.nanoTime();
    // ...
    chCuentaPalabras = new ConcurrentHashMap<String, Integer>( 1000, 0.75F );
    MiHebra3 ch[] = new MiHebra3[numHebras];
    for(int i = 0; i<numHebras; i++){
      ch[i] = new MiHebra3(i, numHebras, vectorLineas, chCuentaPalabras);
      ch[i].start();
    }
    try{
      for (int i = 0; i < numHebras ; i++) {
        ch[i].join();
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela 3: " );
    imprimePalabraMasUsadaYVeces( chCuentaPalabras );
    System.out.println( " Tiempo(s): " + tt  + " , Incremento " + tt/t2);
    System.out.println( "Num. elems. tabla hash: " + chCuentaPalabras.size());
    System.out.println();
    //
    // Implementacion paralela 4: Uso de ConcurrentHashMap
    //
    // ...
    t1 = System.nanoTime();
    // ...
    ch2CuentaPalabras = new ConcurrentHashMap<String, Integer>( 1000, 0.75F );
    MiHebra4 ch2[] = new MiHebra4[numHebras];
    for(int i = 0; i<numHebras; i++){
      ch2[i] = new MiHebra4(i, numHebras, vectorLineas, ch2CuentaPalabras);
      ch2[i].start();
    }
    try{
      for (int i = 0; i < numHebras ; i++) {
        ch2[i].join();
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela 4: " );
    imprimePalabraMasUsadaYVeces( ch2CuentaPalabras );
    System.out.println( " Tiempo(s): " + tt  + " , Incremento " + tt/t2);
    System.out.println( "Num. elems. tabla hash: " + ch2CuentaPalabras.size());
    System.out.println();


    //
    // Implementacion paralela 5: Uso de ConcurrentHashMap
    //
    t1 = System.nanoTime();

    ConcurrentHashMap<String, AtomicInteger> concurrentHashMap = new ConcurrentHashMap<>(1000, 0.75F);
    MiHebra_5[] vc = new MiHebra_5[numHebras];
    for(int i = 0; i < numHebras; i++){
      vc[i] = new MiHebra_5(vectorLineas, i, concurrentHashMap, numHebras);
      vc[i].start();
    }
    for(int i = 0; i < numHebras; i++){
      try {
        vc[i].join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    imprimePalabraMasUsadaYVecesAtomic( concurrentHashMap );
    t2 = System.nanoTime();
    double tp = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela 5: " );
    System.out.println( " Tiempo(s): " + tp  + " , Incremento " + tt/tp);
    System.out.println( "Num. elems. tabla hash: " + concurrentHashMap.size());
    System.out.println();

    //
    // Implementacion paralela 6: Uso de ConcurrentHashMap 
    //
    t1 = System.nanoTime();

    int numHebras6 = 256;
    ConcurrentHashMap<String, AtomicInteger> concurrent256HashMap = new ConcurrentHashMap<>(1000, 0.75F,256);
    MiHebra_5[] vc256 = new MiHebra_5[numHebras6];


    for(int i = 0; i < numHebras6; i++){
      vc256[i] = new MiHebra_5(vectorLineas, i, concurrent256HashMap, numHebras6);
      vc256[i].start();
    }
    for(int i = 0; i < numHebras6; i++){
      try {
        vc256[i].join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    imprimePalabraMasUsadaYVecesAtomic( concurrent256HashMap );
    t2 = System.nanoTime();
    tp = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.print( "Implementacion paralela 6: " );
    System.out.println( " Tiempo(s): " + tp  + " , Incremento " + tt/tp);
    System.out.println( "Num. elems. tabla hash: " + concurrent256HashMap.size());
    System.out.println();

    //
    // Implementacion paralela 7: Uso de Streams
     t1 = System.nanoTime();
     Map<String,Long> stCuentaPalabras = vectorLineas.parallelStream()
                                           .filter( s -> s != null )
                                           .map( s -> s.split( "\\W+" ) )
                                           .flatMap( Arrays::stream )
                                           .map( String::trim )
                                           .filter( s -> (s.length() > 0) )
                                           .collect( groupingBy (s -> s, counting()));
    t2 = System.nanoTime();
    tp = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    imprimePalabraMasUsadaYVecesLong(stCuentaPalabras);
    System.out.print( "Implementacion paralela 7: " );
    System.out.println( " Tiempo(s): " + tp  + " , Incremento " + tt/tp);
    System.out.println( "Num. elems. tabla hash: " + stCuentaPalabras.size());
    System.out.println();

    System.out.println( "Fin de programa." );
  }

  // -------------------------------------------------------------------------
  public static Vector<String> readFile( String fileName ) {
    BufferedReader br;
    String         linea;
    Vector<String> data = new Vector<String>();

    try {
      br = new BufferedReader( new FileReader( fileName ) );
      while( ( linea = br.readLine() ) != null ) {
        //// System.out.println( "Leida linea: " + linea );
        data.add( linea );
      }
      br.close();
    } catch( FileNotFoundException ex ) {
      ex.printStackTrace();
    } catch( IOException ex ) {
      ex.printStackTrace();
    }
    return data;
  }

  // -------------------------------------------------------------------------
  public static void contabilizaPalabra(
          HashMap<String,Integer> cuentaPalabras,
          String palabra ) {
    Integer numVeces = cuentaPalabras.get( palabra );
    if( numVeces != null ) {
      cuentaPalabras.put( palabra, numVeces+1 );
    } else {
      cuentaPalabras.put( palabra, 1 );
    }
  }
  public static void contabilizaPalabraParalelo(Map<String, Integer> cuentaPalabras, String palabra ) {
    cuentaPalabras.compute(palabra, (k, v) -> v == null ? 1 : v + 1);
  }

  // --------------------------------------------------------------------------
  static void imprimePalabraMasUsadaYVecesLong(Map<String,Long> cuentaPalabras ) {
    Vector<Map.Entry> lista =new Vector<Map.Entry>( cuentaPalabras.entrySet() );

    String palabraMasUsada = "";
    long    numVecesPalabraMasUsada = 0;
    // Calcula la palabra mas usada.
    for( int i = 0; i < lista.size(); i++ ) {
      String palabra = ( String ) lista.get( i ).getKey();
      long numVeces = ( Long ) lista.get( i ).getValue();
      if( i == 0 ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      } else if( numVecesPalabraMasUsada < numVeces ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      }
    }
    // Imprime resultado.
    System.out.print( "( Palabra: '" + palabraMasUsada + "' " +
            "veces: " + numVecesPalabraMasUsada + " )" );
  }
  static void imprimePalabraMasUsadaYVeces(
          Map<String,Integer> cuentaPalabras ) {
    Vector<Map.Entry> lista =
            new Vector<Map.Entry>( cuentaPalabras.entrySet() );

    String palabraMasUsada = "";
    int    numVecesPalabraMasUsada = 0;
    // Calcula la palabra mas usada.
    for( int i = 0; i < lista.size(); i++ ) {
      String palabra = ( String ) lista.get( i ).getKey();
      int numVeces = ( Integer ) lista.get( i ).getValue();
      if( i == 0 ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      } else if( numVecesPalabraMasUsada < numVeces ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      }
    }
    // Imprime resultado.
    System.out.print( "( Palabra: '" + palabraMasUsada + "' " +
            "veces: " + numVecesPalabraMasUsada + " )" );
  }

  static void imprimePalabraMasUsadaYVecesAtomic(Map<String,AtomicInteger> cuentaPalabras ) {
    Vector<Map.Entry> lista = new Vector<Map.Entry>( cuentaPalabras.entrySet() );

    String palabraMasUsada = "";
    int    numVecesPalabraMasUsada = 0;
    // Calcula la palabra mas usada.
    for( int i = 0; i < lista.size(); i++ ) {
      String palabra = ( String ) lista.get( i ).getKey();
      AtomicInteger a = ( AtomicInteger ) lista.get( i ).getValue();
      int numVeces = a.get();
      if( i == 0 ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      } else if( numVecesPalabraMasUsada < numVeces ) {
        palabraMasUsada = palabra;
        numVecesPalabraMasUsada = numVeces;
      }
    }
    // Imprime resultado.
    System.out.print( "( Palabra: '" + palabraMasUsada + "' " +
            "veces: " + numVecesPalabraMasUsada + " )" );
  }

  // --------------------------------------------------------------------------
  static void printCuentaPalabrasOrdenadas(
          HashMap<String,Integer> cuentaPalabras ) {
    int             i, numVeces;
    List<Map.Entry> list = new Vector<Map.Entry>( cuentaPalabras.entrySet() );

    // Ordena por valor.
    Collections.sort(
            list,
            new Comparator<Map.Entry>() {
              public int compare( Map.Entry e1, Map.Entry e2 ) {
                Integer i1 = ( Integer ) e1.getValue();
                Integer i2 = ( Integer ) e2.getValue();
                return i2.compareTo( i1 );
              }
            }
    );
    // Muestra contenido.
    i = 1;
    System.out.println( "Veces Palabra" );
    System.out.println( "-----------------" );
    for( Map.Entry e : list ) {
      numVeces = ( ( Integer ) e.getValue () ).intValue();
      System.out.println( i + " " + e.getKey() + " " + numVeces );
      i++;
    }
    System.out.println( "-----------------" );
  }
}
class MiHebra1 extends Thread{
  int miId, numHebras;
  String palabraActual;
  Vector<String> vectorLineas;
  HashMap<String, Integer> maCuentaPalabras;
  MiHebra1(int miId, int numHebras, Vector<String> vectorLineas, HashMap<String, Integer> maCuentaPalabras){
    this.miId = miId;
    this.numHebras = numHebras;
    this.vectorLineas = vectorLineas;
    this.maCuentaPalabras = maCuentaPalabras;
  }

  public void run(){
    for (int i = miId; i < vectorLineas.size(); i+=numHebras) {
      String[] palabras = vectorLineas.get(i).split("\\W+");
      for (int j = 0; j < palabras.length; j++) {
        palabraActual = palabras[j].trim();
        if( palabraActual.length() > 0 ) {
          EjemploPalabraMasUsada.contabilizaPalabra( maCuentaPalabras, palabraActual );
        }
      }
    }
  }

}

class MiHebra_2 extends Thread {
  final Vector<String> vector;
  final int id;
  final int numHebras;
  final Hashtable<String, Integer> hashMap;

  public MiHebra_2(Vector<String> vector, int id, Hashtable<String, Integer> hashMap, int numHebras)  {
    this.vector = vector;
    this.id = id;
    this.hashMap = hashMap;
    this.numHebras = numHebras;
  }

  @Override
  public void run() {
    for(int i = id; i < vector.size(); i += numHebras) {
      String[] palabras = vector.get(i).split( "\\W+" );
      for (String palabra : palabras) {
        String palabraActual = palabra.trim(); //quita espacios en blanco
        if (palabraActual.length() > 0) {
          EjemploPalabraMasUsada.contabilizaPalabraParalelo(hashMap, palabraActual);
        }
      }
    }
  }
}


class MiHebra3 extends Thread{
  int miId, numHebras;
  String palabraActual;
  Vector<String> vectorLineas;
  ConcurrentHashMap<String, Integer> chCuentaPalabras;
  MiHebra3(int miId, int numHebras, Vector<String> vectorLineas, ConcurrentHashMap<String, Integer> chCuentaPalabras){
    this.miId = miId;
    this.numHebras = numHebras;
    this.vectorLineas = vectorLineas;
    this.chCuentaPalabras = chCuentaPalabras;
  }

  public void run(){
    for (int i = 0; i < vectorLineas.size(); i+=numHebras) {
      String[] palabras = vectorLineas.get(i).split("\\W+");
      for (int j = 0; j < palabras.length; j++) {
        palabraActual = palabras[j].trim();
        if( palabraActual.length() > 0 ) {
          contabilizaPalabra( chCuentaPalabras, palabraActual );
        }
      }

    }
  }
  public synchronized static void contabilizaPalabra(
          ConcurrentHashMap<String,Integer> cuentaPalabras,
          String palabra ) {
    Integer numVeces = cuentaPalabras.get( palabra );
    if( numVeces != null ) {
      cuentaPalabras.put( palabra, numVeces+1 );
    } else {
      cuentaPalabras.put( palabra, 1 );
    }
  }
}

class MiHebra4 extends Thread{
  int miId, numHebras;
  String palabraActual;
  Vector<String> vectorLineas;
  ConcurrentHashMap<String, Integer> ch2CuentaPalabras;
  MiHebra4(int miId, int numHebras, Vector<String> vectorLineas, ConcurrentHashMap<String, Integer> ch2CuentaPalabras){
    this.miId = miId;
    this.numHebras = numHebras;
    this.vectorLineas = vectorLineas;
    this.ch2CuentaPalabras = ch2CuentaPalabras;
  }

  public void run(){
    for (int i = 0; i < vectorLineas.size(); i+=numHebras) {
      String[] palabras = vectorLineas.get(i).split("\\W+");
      for (int j = 0; j < palabras.length; j++) {
        palabraActual = palabras[j].trim();
        if( palabraActual.length() > 0 ) {
          contabilizaPalabra(palabraActual );
        }
      }

    }
  }

  public  void contabilizaPalabra(String palabra ) {
    ch2CuentaPalabras.merge(palabra,1, Integer::sum);
  }
}
class MiHebra_5 extends Thread {
  final Vector<String> vector;
  final int id;
  final int numHebras;
  final ConcurrentHashMap<String, AtomicInteger> hashMap;

  public MiHebra_5(Vector<String> vector, int id, ConcurrentHashMap<String, AtomicInteger> hashMap, int numHebras)  {
    this.vector = vector;
    this.id = id;
    this.hashMap = hashMap;
    this.numHebras = numHebras;
  }

  @Override
  public void run() {
    for(int i = id; i < vector.size(); i += numHebras) {
      String[] palabras = vector.get(i).split( "\\W+" );
      for (String palabra : palabras) {
        String palabraActual = palabra.trim(); //quita espacios en blanco
        if (palabraActual.length() > 0) {
          contabilizaPalabraAtomic(hashMap, palabraActual);
        }
      }
    }
  }

  public static void contabilizaPalabraAtomic(ConcurrentHashMap<String, AtomicInteger> cuentaPalabras, String palabra ) {
    AtomicInteger a = new AtomicInteger(1);
    boolean sust = false;
    if(cuentaPalabras.putIfAbsent(palabra,a)!=null) {
        AtomicInteger num = cuentaPalabras.get(palabra);
        num.incrementAndGet();
    }
  }
}