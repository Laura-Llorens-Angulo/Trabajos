package Laboratorio01.Ejercicio3;

import java.util.Vector;

// ============================================================================
class CuentaIncrementos {
// ============================================================================
  long contador = 0;

  // --------------------------------------------------------------------------
  void incrementaContador() {
    contador++;
  }

  // --------------------------------------------------------------------------
  long dameContador() {
    return( contador );
  }
}


// ============================================================================
class MiHebra extends Thread {
// ============================================================================
  int miId;
  CuentaIncrementos contador;
  // --------------------------------------------------------------------------
  public MiHebra( int miId, CuentaIncrementos contador ) {
    this.miId = miId;
    this.contador = contador;
  }
  // --------------------------------------------------------------------------
  public void run() {
    System.out.println( "Hebra: " + miId + " Comenzando incrementos" );
    for( int i = 0; i < 1000000; i++ ) {
      contador.incrementaContador();
    }
    System.out.println( "Hebra: " + miId + " Terminando incrementos" );
  }
}

// ============================================================================
class EjemploIncrementos {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main(String args[] ) {
    int  numHebras;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 1 ) {
      System.err.println( "Uso: java programa <numHebras>" );
      System.exit( -1 );
    }
    try {
      numHebras = Integer.parseInt( args[ 0 ] );
    } catch( NumberFormatException ex ) {
      numHebras = -1;
      System.out.println( "ERROR: Argumentos numericos incorrectos." );
      System.exit( -1 );
    }
    System.out.println( "numHebras: " + numHebras );
    CuentaIncrementos contador = new CuentaIncrementos();
    System.out.println( "Valor inicial contador: " + contador.dameContador() );

    MiHebra vh [] = new MiHebra [ numHebras ];
    for ( int i = 0; i < numHebras ; i ++ ) {
      vh [ i ] = new MiHebra ( i, contador );
      vh[ i ].start();
    }
    for ( int i = 0; i < numHebras ; i ++ ) {
      try {
        vh [ i ]. join () ;
      } catch ( InterruptedException ex ) {
        ex . printStackTrace () ;
      }
    }
    System.out.println("Valor final contador: " + contador.dameContador());
  }
}

