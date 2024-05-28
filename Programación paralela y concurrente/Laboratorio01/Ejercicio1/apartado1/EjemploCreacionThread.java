package Laboratorio01.Ejercicio1.apartado1;

class MiHebra extends Thread {
  int miId;
  public MiHebra( int miId ) {
    this.miId = miId;
  }
  public void run() {
    for( int i = 0; i < 1000; i++ ) {
      System.out.println( "Hebra: " + miId );
    }
  }
}
class EjemploCreacionThread {
  public static void main( String args[] ) {
    Thread t0 = new Thread(new MiHebra(0));
    Thread t1 = new Thread(new MiHebra(1));
    t0.start();
    t1.start();
  }
}
