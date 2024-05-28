package Laboratorio01.Ejercicio1.apartado2;

class MiRun implements Runnable {
  int miId;
  public MiRun( int miId ) {
    this.miId=miId;
  }
  public void run() {
    for( int i = 0; i < 1000; i++ ) {
      System.out.println( "Hebra: " + miId );
    }
  }
}
class EjemploCreacionRunnable {
  public static void main( String args[] ) {
    Thread t0 = new Thread(new MiRun(0));
    Thread t1 = new Thread(new MiRun(1));
    t0.run();
    t1.run();
  }
}
