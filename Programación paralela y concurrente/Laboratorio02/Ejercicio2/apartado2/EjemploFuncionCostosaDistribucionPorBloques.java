package Laboratorio02.Ejercicio2.apartado2;

import static java.lang.Math.min;

// ============================================================================
class EjemploFuncionCostosaDistribucionCiclica {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main( String args[] ) {
    int     n, numHebras;
    long    t1, t2;
    double  sumaX, sumaY, ts, tc, tb;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.err.println( "Uso: java programa <numHebras> <tamanyo>" );
      System.exit( -1 );
    }
    try {
      numHebras = Integer.parseInt( args[ 0 ] );
      n         = Integer.parseInt( args[ 1 ] );
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      n         = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }

    // Crea los vectores.
    double vectorX[] = new double[ n ];
    double vectorY[] = new double[ n ];

    //
    // Implementacion secuencial (sin temporizar).
    //
    inicializaVectorX( vectorX );
    inicializaVectorY( vectorY );
    for( int i = 0; i < n; i++ ) {
      vectorY[ i ] = evaluaFuncion( vectorX[ i ] );
    }

    //
    // Implementacion secuencial.
    //
    inicializaVectorX( vectorX );
    inicializaVectorY( vectorY );
    t1 = System.nanoTime();
    for( int i = 0; i < n; i++ ) {
      vectorY[ i ] = evaluaFuncion( vectorX[ i ] );
    }
    t2 = System.nanoTime();
    ts = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Tiempo secuencial (seg.):                    " + ts );
    //// imprimeResultado( vectorX, vectorY );
    // Comprueba el resultado. 
    sumaX = sumaVector( vectorX );
    sumaY = sumaVector( vectorY );
    System.out.println( "Suma del vector X:          " + sumaX );
    System.out.println( "Suma del vector Y:          " + sumaY );

    //
    // Implementacion paralela ciclica.
    //
    inicializaVectorX( vectorX );
    inicializaVectorY( vectorY );
    t1 = System.nanoTime();
    // Gestion de hebras para la implementacion paralela ciclica
    HebraDistCiclica v[] = new HebraDistCiclica[ numHebras ];
    for(int i=0 ; i < numHebras ; i++){
      v[i] = new HebraDistCiclica(i, numHebras, n, vectorX, vectorY);
      v[i].start();
    }
    for(int i=0 ; i < numHebras ; i++){
      try{
        v[i].join();
      }catch (InterruptedException ex){
        ex.printStackTrace();
      }
    }

    t2 = System.nanoTime();
    tc = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Tiempo paralela ciclica (seg.):              " + tc );
    System.out.println( "Incremento paralela ciclica:                 " + (ts/tc) );
    //// imprimeResultado( vectorX, vectorY );
    // Comprueba el resultado.
    sumaX = sumaVector( vectorX );
    sumaY = sumaVector( vectorY );
    System.out.println( "Suma del vector X:          " + sumaX );
    System.out.println( "Suma del vector Y:          " + sumaY );
    //
    // Implementacion paralela por bloques.
    //
    inicializaVectorX( vectorX );
    inicializaVectorY( vectorY );
    t1 = System.nanoTime();
    // Gestion de hebras para la implementacion paralela por bloques
    HebraDistBloques vB[] = new HebraDistBloques[numHebras];
    for(int i=0 ; i < numHebras ; i++){
      vB[i] = new HebraDistBloques(i, numHebras, n, vectorX, vectorY);
      vB[i].start();
    }
    for(int i=0 ; i < numHebras ; i++){
      try{
        vB[i].join();
      }catch (InterruptedException ex){
        ex.printStackTrace();
      }
    }

    t2 = System.nanoTime();
    tb = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Tiempo paralela bloques (seg.):              " + tb );
    System.out.println( "Incremento paralela bloques:                 " + (ts/tb) );
    //// imprimeResultado( vectorX, vectorY );
    // Comprueba el resultado.
    sumaX = sumaVector( vectorX );
    sumaY = sumaVector( vectorY );
    System.out.println( "Suma del vector X:          " + sumaX );
    System.out.println( "Suma del vector Y:          " + sumaY );


    System.out.println( "Fin del programa." );
  }

  // --------------------------------------------------------------------------
  static void inicializaVectorX( double vectorX[] ) {
    if( vectorX.length == 1 ) {
      vectorX[ 0 ] = 0.0;
    } else {
      for( int i = 0; i < vectorX.length; i++ ) {
        vectorX[ i ] = 10.0 * ( double ) i / ( ( double ) vectorX.length - 1 );
      }
    }
  }

  // --------------------------------------------------------------------------
  static void inicializaVectorY( double vectorY[] ) {
    for( int i = 0; i < vectorY.length; i++ ) {
      vectorY[ i ] = 0.0;
    }
  }

  // --------------------------------------------------------------------------
  static double sumaVector( double vector[] ) {
    double  suma = 0.0;
    for( int i = 0; i < vector.length; i++ ) {
      suma += vector[ i ];
    }
    return suma;
  }

  // --------------------------------------------------------------------------
  static double evaluaFuncion( double x ) {
    return Math.sin( Math.exp( -x ) + Math.log10( x + 1 ) );
  }

  // --------------------------------------------------------------------------
  static void imprimeVector( double vector[] ) {
    for( int i = 0; i < vector.length; i++ ) {
      System.out.println( " vector[ " + i + " ] = " + vector[ i ] );
    }
  }

  // --------------------------------------------------------------------------
  static void imprimeResultado( double vectorX[], double vectorY[] ) {
    for( int i = 0; i < min( vectorX.length, vectorY.length ); i++ ) {
      System.out.println( "  i: " + i + 
                          "  x: " + vectorX[ i ] +
                          "  y: " + vectorY[ i ] );
    }
  }

}

class HebraDistCiclica extends Thread{
  int miId, numHebras, n;
  double vectorX[] = new double[ n ];
  double vectorY[] = new double[ n ];

  public HebraDistCiclica(int miId, int numHebras, int n, double vectorX[], double vectorY[]){
    this.miId = miId;
    this.numHebras = numHebras;
    this.n = n;
    this.vectorX = vectorX;
    this.vectorY = vectorY;
  }

  public void run(){
    for(int i = miId; i < n; i += numHebras){
      double x = vectorX[ i ];
      vectorY[ i ] = Math.sin( Math.exp( -x ) + Math.log10( x + 1 ) );
    }
  }
}

class HebraDistBloques extends Thread{
  int miId, numHebras, n;
  double vectorX[] = new double[ n ];
  double vectorY[] = new double[ n ];

  public HebraDistBloques(int miId, int numHebras, int n, double vectorX[], double vectorY[]){
    this.miId = miId;
    this.numHebras = numHebras;
    this.n = n;
    this.vectorX = vectorX;
    this.vectorY = vectorY;
  }

  public void run(){
    int tamanyo = (n + numHebras - 1) / numHebras;
    int inicio = tamanyo * miId;
    int fin = min(inicio+tamanyo,n);
    for(int i = inicio; i < fin; i++){
      double x = vectorX[ i ];
      vectorY[ i ] = Math.sin( Math.exp( -x ) + Math.log10( x + 1 ) );
    }
  }
}



