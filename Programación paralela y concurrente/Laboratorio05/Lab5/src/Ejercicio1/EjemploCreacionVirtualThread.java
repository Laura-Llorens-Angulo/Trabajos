package Ejercicio1;

class MiHebra extends Thread {
  final int miId;
  public MiHebra( int miId ) {
    this.miId = miId;
  }
  public void run() {
    for( int i = 0; i < 1000; i++ ) {
      System.out.println( "Hebra Virtual " + miId );
    }
  }
}

class EjemploCreacionVirtualThread {
  public static void main( String args[] ) {
    System.out.println( "Hebra Principal inicia");
    Thread t0 = Thread.startVirtualThread(new MiHebra( 0 ) );
    Thread t1 = Thread.startVirtualThread(new MiHebra( 1 ) );

    try{
      t0.join();
      t1.join();
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }

    System.out.println( "Hebra Principal finaliza");
  }
}

