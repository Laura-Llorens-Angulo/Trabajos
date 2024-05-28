package Laboratorio4.Ejercicio2.apartado1;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// ===========================================================================
class ZonaIntercambio {
// ===========================================================================
  volatile long time = 500L;

  // -------------------------------------------------------------------------
  void setTiempo(long time) {
    this.time = time;
  }

  // -------------------------------------------------------------------------
  long getTiempo(  ) {
    return time;
  }
}
// ===========================================================================
class HebraCalculadora extends Thread {
// ===========================================================================
  volatile boolean fin;
  JTextField txfMensajes;
  ZonaIntercambio z;
  long i;

  public HebraCalculadora(JTextField txfMensajes,ZonaIntercambio z) {
    fin = false;
    this.txfMensajes = txfMensajes;
    i = 1L ;
    this.z = z;
  }

  public void TerminaHebra(){
    fin = true;
  }

  public void run(){

    while ( !fin ) {
      if( GUISecuenciaPrimos.esPrimo( i ) ) {
        SwingUtilities.invokeLater(
                () -> txfMensajes.setText(i+" es primo.")
        );
        try {
          sleep(z.getTiempo());
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      i ++;
    }
  }
}
// ===========================================================================
public class GUISecuenciaPrimos {
// ===========================================================================
  JFrame      container;
  JPanel      jpanel;
  JTextField  txfMensajes;
  JButton     btnComienzaSecuencia, btnCancelaSecuencia;
  JSlider     sldEspera;
  HebraCalculadora  t; // Ejercicio 2.2
  ZonaIntercambio   z; // Ejercicio 2.3
  
  // -------------------------------------------------------------------------
  public static void main( String args[] ) {
    GUISecuenciaPrimos gui = new GUISecuenciaPrimos();
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        gui.go();
      }
    });
  }

  // -------------------------------------------------------------------------
  public void go() {
    // Constantes.
    final int valorMaximo = 1000;
    final int valorMedio  = 500;

    // Variables.
    JPanel  tempPanel;

    // Crea el JFrame principal.
    container = new JFrame( "GUI Secuencia de Primos " );

    // Consigue el panel principal del Frame "container".
    jpanel = ( JPanel ) container.getContentPane();
    jpanel.setLayout( new GridLayout( 3, 1 ) );

    // Crea e inserta la etiqueta y el campo de texto para los mensajes.
    txfMensajes = new JTextField( 20 );
    txfMensajes.setEditable( false );
    tempPanel = new JPanel();
    tempPanel.setLayout( new FlowLayout() );
    tempPanel.add( new JLabel( "Secuencia: " ) );
    tempPanel.add( txfMensajes );
    jpanel.add( tempPanel );

    // Crea e inserta los botones de Comienza secuencia y Cancela secuencia.
    btnComienzaSecuencia = new JButton( "Comienza secuencia" );
    btnCancelaSecuencia = new JButton( "Cancela secuencia" );
    tempPanel = new JPanel();
    tempPanel.setLayout( new FlowLayout() );
    tempPanel.add( btnComienzaSecuencia );
    tempPanel.add( btnCancelaSecuencia );
    jpanel.add( tempPanel );

    // Crea e inserta el slider para controlar el tiempo de espera.
    sldEspera = new JSlider( JSlider.HORIZONTAL, 0, valorMaximo , valorMedio );
    tempPanel = new JPanel();
    tempPanel.setLayout( new BorderLayout() );
    tempPanel.add( new JLabel( "Tiempo de espera: " ) );
    tempPanel.add( sldEspera );
    jpanel.add( tempPanel );
    
    // Activa inicialmente los 2 botones.
    btnComienzaSecuencia.setEnabled( true );
    btnCancelaSecuencia.setEnabled( false );

    // Anyade codigo para procesar el evento del boton de Comienza secuencia.
    btnComienzaSecuencia.addActionListener( new ActionListener() {
        public void actionPerformed( ActionEvent e ) {
          btnComienzaSecuencia.setEnabled( false );
          btnCancelaSecuencia.setEnabled( true );
          z = new ZonaIntercambio();
          t = new HebraCalculadora(txfMensajes, z);
          t.start();
        }
    } );

    // Anyade codigo para procesar el evento del boton de Cancela secuencia.
    btnCancelaSecuencia.addActionListener( new ActionListener() {
        public void actionPerformed( ActionEvent e ) {
          btnComienzaSecuencia.setEnabled( true );
          btnCancelaSecuencia.setEnabled( false );
          t.TerminaHebra();
        }
    } );

    // Anyade codigo para procesar el evento del slider " Espera " .
    sldEspera.addChangeListener( new ChangeListener() {
      public void stateChanged( ChangeEvent e ) {
        JSlider sl = ( JSlider ) e.getSource();
        if ( ! sl.getValueIsAdjusting() ) {
          long tiempoMilisegundos = ( long ) sl.getValue();
          System.out.println( "JSlider value = " + tiempoMilisegundos );
          z.setTiempo(tiempoMilisegundos);
        }
      }
    } );

    // Fija caracteristicas del container.
    container.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    container.pack();
    container.setResizable( false );
    container.setVisible( true );

    System.out.println( "% End of routine: go.\n" );
  }

  // -------------------------------------------------------------------------
  static boolean esPrimo( long num ) {
    boolean primo;
    if( num < 2 ) {
      primo = false;
    } else {
      primo = true;
      long i = 2;
      while( ( i < num )&&( primo ) ) {
        primo = ( num % i != 0 );
        i++;
      }
    }
    return( primo );
  }
}
