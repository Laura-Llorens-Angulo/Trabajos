package Laboratorio03.Ejercicio1.apartado6;

import java.util.concurrent.atomic.AtomicInteger;

class MiHebra extends Thread {
// ============================================================================
  int                iters;
  AtomicInteger c = new AtomicInteger();

  // --------------------------------------------------------------------------
  public MiHebra( int iters, AtomicInteger c ) {
    this.iters = iters;
    this.c     = c;
  }

  // --------------------------------------------------------------------------
  public void run() {
    for( int i = 0; i < iters; i++ ) {
      c.incrementAndGet();
    }
  }
}

// ============================================================================
class EjemploCuentaIncrementos {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main( String args[] ) {
    long    t1, t2;
    double  tt;
    int     numHebras, iters;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.err.println( "Uso: java programa <numHebras> <iters>" );
      System.exit( -1 );
    }
    try {
      numHebras = Integer.parseInt( args[ 0 ] );
      iters     = Integer.parseInt( args[ 1 ] );
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      iters     = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }

    System.out.println( "numHebras: " + numHebras );
    System.out.println( "iters    : " + iters );
    
    System.out.println( "Creando y arrancando " + numHebras + " hebras." );
    t1 = System.nanoTime();
    MiHebra v[] = new MiHebra[ numHebras ];
    AtomicInteger c = new AtomicInteger();
    for( int i = 0; i < numHebras; i++ ) {
      v[ i ] = new MiHebra( iters, c );
      v[ i ].start();
    }
    for( int i = 0; i < numHebras; i++ ) {
      try {
        v[ i ].join();
      } catch( InterruptedException ex ) {
        ex.printStackTrace();
      }
    }
    t2 = System.nanoTime();
    tt = ( ( double ) ( t2 - t1 ) ) / 1.0e9;
    System.out.println( "Total de incrementos: " + c.get() );
    System.out.println( "Tiempo transcurrido en segs.: " + tt );
  }
}

