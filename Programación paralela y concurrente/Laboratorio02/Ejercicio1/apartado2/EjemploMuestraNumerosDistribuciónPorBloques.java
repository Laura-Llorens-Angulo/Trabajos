package Laboratorio02.Ejercicio1.apartado2;

// ============================================================================
class EjemploMuestraNumerosDistribuciónPorBloques {
// ============================================================================

  // --------------------------------------------------------------------------
  public static void main( String args[] ) {
    int  n, numHebras;

    // Comprobacion y extraccion de los argumentos de entrada.
    if( args.length != 2 ) {
      System.err.println( "Uso: java programa <numHebras> <n>" );
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
    HebraDistPorBloques v[] = new HebraDistPorBloques[ numHebras ];
    for(int i=0 ; i < numHebras ; i++){
      v[i] = new HebraDistPorBloques(i, numHebras, n);
      v[i].start();
    }
    for(int i=0 ; i < numHebras ; i++){
      try{
        v[i].join();
      }catch (InterruptedException ex){
        ex.printStackTrace();
      }
    }
  }
}

class HebraDistPorBloques extends Thread{
  int miId, numHebras, n;

  public HebraDistPorBloques(int miId, int numHebras, int n){
    this.miId = miId;
    this.numHebras = numHebras;
    this.n = n;
  }

  public void run(){
    int tamanyo = (n + numHebras - 1) / numHebras;
    int inicio = tamanyo * miId;
    int fin = Math.min(inicio+tamanyo,n);
    for(int i = inicio; i < fin; i++){
      System . out . println ( " Hebra : " + miId + " Mostrando el número " + i ) ;
    }
  }
}