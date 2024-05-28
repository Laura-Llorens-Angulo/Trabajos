package Laboratorio4.Ejercicio1.apartado3;


import java.util.concurrent.atomic.DoubleAdder;

// ===========================================================================
class Acumula {
// ===========================================================================
  double  suma;

  // -------------------------------------------------------------------------
  Acumula() {
    suma = 0;
  }

  // -------------------------------------------------------------------------
  void acumulaDato( double dato ) {
    suma += dato;
  }

  // -------------------------------------------------------------------------
  double dameDato() {
    return suma;
  }
}

// ===========================================================================
class MiHebraMultAcumulaciones extends Thread {
// ===========================================================================
  int      miId, numHebras;
  long     numRectangulos;
  Acumula  a;

  // -------------------------------------------------------------------------
  MiHebraMultAcumulaciones( int miId, int numHebras, long numRectangulos, Acumula a ) {
    this.miId = miId;
    this.numHebras = numHebras;
    this.numRectangulos = numRectangulos;
    this.a = a;
  }

  // -------------------------------------------------------------------------
  public void run() {
    double baseRectangulo = 1.0 / ( ( double ) numRectangulos );
    for( int i = miId; i < numRectangulos; i+= numHebras ) {
      a.acumulaDato(EjemploNumeroPI.f( baseRectangulo * (  i  + 0.5 ) ));
    }
  }
}

// ===========================================================================
class MiHebraUnaAcumulacion extends Thread {
// ===========================================================================
  int      miId, numHebras;
  long     numRectangulos;
  Acumula  a;

  // -------------------------------------------------------------------------
  MiHebraUnaAcumulacion( int miId, int numHebras, long numRectangulos, Acumula a ) {
    this.miId = miId;
    this.numHebras = numHebras;
    this.numRectangulos = numRectangulos;
    this.a = a;
  }

  // -------------------------------------------------------------------------
  public void run() {
    double baseRectangulo = 1.0 / ( ( double ) numRectangulos );
    double sumaL = 0;
    for( int i = miId; i < numRectangulos; i+= numHebras ) {
      sumaL += EjemploNumeroPI.f( baseRectangulo * (  i  + 0.5 ) );
    }
    a.acumulaDato(sumaL);
  }
}
// ===========================================================================
class MiHebraMultAcumulacionAtomica extends Thread {
  // ===========================================================================
  int      miId, numHebras;
  long     numRectangulos;
  DoubleAdder adder;

  // -------------------------------------------------------------------------
  MiHebraMultAcumulacionAtomica( int miId, int numHebras, long numRectangulos, DoubleAdder adder) {
    this.miId = miId;
    this.numHebras = numHebras;
    this.numRectangulos = numRectangulos;
    this.adder = adder;
  }

  // -------------------------------------------------------------------------
  public void run() {
    double baseRectangulo = 1.0 / ( ( double ) numRectangulos );
    for( int i = miId; i < numRectangulos; i+= numHebras ) {
      adder.add(EjemploNumeroPI.f( baseRectangulo * (  i  + 0.5 ) ));
    }
  }
}

// ===========================================================================
class MiHebraUnaAcumulacionAtomica extends Thread {
  // ===========================================================================
  int      miId, numHebras;
  long     numRectangulos;
  DoubleAdder adder;

  // -------------------------------------------------------------------------
  MiHebraUnaAcumulacionAtomica( int miId, int numHebras, long numRectangulos, DoubleAdder adder ) {
    this.miId = miId;
    this.numHebras = numHebras;
    this.numRectangulos = numRectangulos;
    this.adder = adder;
  }

  // -------------------------------------------------------------------------
  public void run() {
    double baseRectangulo = 1.0 / ( ( double ) numRectangulos );
    double sumaL = 0;
    for( int i = miId; i < numRectangulos; i+= numHebras ) {
      sumaL += EjemploNumeroPI.f( baseRectangulo * (  i  + 0.5 ) );
    }
    adder.add(sumaL);
  }
}

// ===========================================================================
class EjemploNumeroPI {
// ===========================================================================

  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    long                        numRectangulos;
    double                      baseRectangulo, x, suma, pi;
    int                         numHebras;
    long                        t1, t2;
    double                      tSec, tPar;
    // Acumula                     a;
    // MiHebraMultAcumulaciones  vt[];

    // Comprobacion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.out.println( "ERROR: numero de argumentos incorrecto.");
      System.out.println( "Uso: java programa <numHebras> <numRectangulos>" );
      System.exit( -1 );
    }
    try {
      numHebras      = Integer.parseInt( args[ 0 ] );
      numRectangulos = Long.parseLong( args[ 1 ] );
    } catch( NumberFormatException ex ) {
      numHebras      = -1;
      numRectangulos = -1;
      System.out.println( "ERROR: Numeros de entrada incorrectos." );
      System.exit( -1 );
    }

    System.out.println();
    System.out.println( "Calculo del numero PI mediante integracion." );

    //
    // Calculo del numero PI de forma secuencial.
    //
    System.out.println();
    System.out.println( "Comienzo del calculo secuencial." );
    t1 = System.nanoTime();
    baseRectangulo = 1.0 / ( ( double ) numRectangulos );
    suma           = 0.0;
    for( long i = 0; i < numRectangulos; i++ ) {
      x = baseRectangulo * ( ( ( double ) i ) + 0.5 );
      suma += f( x );
    }
    pi = baseRectangulo * suma;
    t2 = System.nanoTime();
    tSec = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Version secuencial. Numero PI: " + pi );
    System.out.println( "Tiempo secuencial (s.):        " + tSec );

    //
    // Calculo del numero PI de forma paralela: 
    // Multiples acumulaciones por hebra.
    //
    System.out.println();
    System.out.print( "Comienzo del calculo paralelo: " );
    System.out.println( "Multiples acumulaciones por hebra." );
    t1 = System.nanoTime();
    Acumula ama = new Acumula();
    MiHebraMultAcumulaciones vma[] = new MiHebraMultAcumulaciones[ numHebras ];
    for(int i=0 ; i < numHebras ; i++){
      vma[i] = new MiHebraMultAcumulaciones(i,numHebras, numRectangulos ,ama);
      vma[i].start();
    }
    for(int i=0 ; i < numHebras ; i++){
      try{
        vma[i].join();
      }catch (InterruptedException ex){
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tPar = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Calculo del numero PI:   " + pi );
    System.out.println( "Tiempo ejecucion (s.):   " + tPar );
    System.out.println( "Incremento velocidad :   " + tSec/tPar );


    //
    // Calculo del numero PI de forma paralela: 
    // Una acumulacion por hebra.
    //
    System.out.println();
    System.out.print( "Comienzo del calculo paralelo: " );
    System.out.println( "Una acumulacion por hebra." );
    t1 = System.nanoTime();
    Acumula aua = new Acumula();
    MiHebraUnaAcumulacion vua[] = new MiHebraUnaAcumulacion[ numHebras ];
    for(int i=0 ; i < numHebras ; i++){
      vua[i] = new MiHebraUnaAcumulacion(i,numHebras, numRectangulos ,aua);
      vua[i].start();
    }
    for(int i=0 ; i < numHebras ; i++){
      try{
        vua[i].join();
      }catch (InterruptedException ex){
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tPar = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Calculo del numero PI:   " + pi );
    System.out.println( "Tiempo ejecucion (s.):   " + tPar );
    System.out.println( "Incremento velocidad :   " + tSec/tPar );



    //
    // Calculo del numero PI de forma paralela: 
    // Multiples acumulaciones por hebra (Atomica)
    //
    System.out.println();
    System.out.print( "Comienzo del calculo paralelo: " );
    System.out.println( "Multiples acumulaciones por hebra (At)." );
    t1 = System.nanoTime();
    DoubleAdder atma = new DoubleAdder();
    MiHebraMultAcumulacionAtomica vtma[] = new MiHebraMultAcumulacionAtomica[ numHebras ];
    for(int i=0 ; i < numHebras ; i++){
      vtma[i] = new MiHebraMultAcumulacionAtomica(i,numHebras, numRectangulos ,atma);
      vtma[i].start();
    }
    for(int i=0 ; i < numHebras ; i++){
      try{
        vtma[i].join();
      }catch (InterruptedException ex){
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tPar = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Calculo del numero PI:   " + pi );
    System.out.println( "Tiempo ejecucion (s.):   " + tPar );
    System.out.println( "Incremento velocidad :   " + tSec/tPar );

    //
    // Calculo del numero PI de forma paralela: 
    // Una acumulacion por hebra (Atomica).
    //
    System.out.println();
    System.out.print( "Comienzo del calculo paralelo: " );
    System.out.println( "Una acumulacion por hebra (At)." );
    t1 = System.nanoTime();
    DoubleAdder atua = new DoubleAdder();
    MiHebraUnaAcumulacionAtomica vtua[] = new MiHebraUnaAcumulacionAtomica[ numHebras ];
    for(int i=0 ; i < numHebras ; i++){
      vtua[i] = new MiHebraUnaAcumulacionAtomica(i,numHebras, numRectangulos ,atua);
      vtua[i].start();
    }
    for(int i=0 ; i < numHebras ; i++){
      try{
        vtua[i].join();
      }catch (InterruptedException ex){
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tPar = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Calculo del numero PI:   " + pi );
    System.out.println( "Tiempo ejecucion (s.):   " + tPar );
    System.out.println( "Incremento velocidad :   " + tSec/tPar );

    System.out.println();
    System.out.println( "Fin de programa." );
  }

  // -------------------------------------------------------------------------
  static double f( double x ) {
    return ( 4.0/( 1.0 + x*x ) );
  }
}

