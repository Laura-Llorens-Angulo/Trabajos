package Laboratorio01.Ejercicio2;

class MiHebra extends Thread {
  int miId;
  int num1;
  int num2;

  public MiHebra( int miId, int num1, int num2 ) {
    this.miId=miId;
    this.num1=num1;
    this.num2=num2;
  }

  public void run() {
    long suma = 0;

    System.out.println( "Hebra Auxiliar " + miId + " , inicia calculo" );
    for( int i = num1; i <= num2 ; i++ ) {
      suma += (long) i;
    }
    System.out.println( "Hebra Auxiliar " + miId + " , suma: " + suma);
  }

}

class EjemploDaemon {
  public static void main( String args[] ) {
    System.out.println( "Hebra Principal inicia" );
    Thread t0 = new Thread(new MiHebra(0,0,1000000));
    Thread t1 = new Thread(new MiHebra(1,0,1000000));
    t0.setDaemon(true);
    t1.setDaemon(true);
    t0.start();
    t1.start();
    try{
     t0.join();
     t1.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println( "Hebra Principal finaliza" );
  }
}

