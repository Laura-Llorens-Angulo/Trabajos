#include <stdio.h>
#include <stdlib.h>
#include "mpi.h"
#include "math.h"

//#define IMPRIME 1
//#define COSTOSA 1

// ============================================================================

double evaluaFuncion( double x ) {
#ifdef COSTOSA
  return sin( exp( -x ) + log10( 1 + x ) );
#else
  return 2.5 * x;
#endif
}

void inicializaVectorX ( double vectorX [ ], int dim ) {
  int i;
  if( dim == 1 ) {
    vectorX[ 0 ] = 0.0;
  } else {
    for( i = 0; i < dim; i++ ) {
      vectorX[ i ] = 10.0 * ( double ) i / ( ( double ) dim - 1 );
    }
  }
}

// ============================================================================
int main( int argc, char *argv[] ) {
  int     dimVectorInicial, dimVectorLocal, i, prc;
  int     miId, numProcs;
  double  *vectorInicial = NULL, *vectorLocal = NULL;
  double  sumaInicial, sumaLocal, sumaFinal;
  double  t1, t2, tSec, tDis, tPar;
  MPI_Status st;


  // Inicializa MPI.
  MPI_Init( & argc, & argv );
  MPI_Comm_size( MPI_COMM_WORLD, & numProcs );
  MPI_Comm_rank( MPI_COMM_WORLD, & miId );

  // En primer lugar se comprueba si el numero de parametros es valido
  if( argc != 2 ) {
    if ( miId == 0 ) {
      fprintf( stderr, "\n" );
      fprintf( stderr, "Uso: a.out dimension\n");
      fprintf( stderr, "\n" );
    }
    MPI_Finalize();
    return( -1 );
  }

  // Todos los procesos deben comprobar que la dimension de vectorInicial "n"
  // es multiplo del numero de procesos.
  dimVectorInicial = atoi(argv[1]);
  if( ( dimVectorInicial % numProcs ) != 0 ) {
    if( miId == 0 ) {
      fprintf( stderr, "\n" );
      fprintf( stderr,
          "ERROR: La dimension %d no es multiplo del numero de procesos: %d\n",
          dimVectorInicial, numProcs );
      fprintf( stderr, "\n" );
    }
    MPI_Finalize();
    exit( -1 );
  }

  // El proceso 0 crea e inicializa "vectorInicial".
  if( miId == 0 ) {
    vectorInicial = ( double * ) malloc( dimVectorInicial * sizeof( double ) );
    inicializaVectorX ( vectorInicial, dimVectorInicial );
  }

#ifdef IMPRIME
  // El proceso 0 imprime el contenido de "vectorInicial".
  if( miId == 0 ) {
    for( i = 0; i < dimVectorInicial; i++ ) {
      printf( "Proc: %d.  vectorInicial[ %3d ] = %lf\n",
              miId, i, vectorInicial[ i ] );
    }
  }
#endif

  // El proceso 0 suma todos los elementos de vectorInicial
  if( miId == 0 ) {
    // Calculo en secuencial de la reduccion sin temporizacion
    sumaInicial = 0;
    for( i = 0; i < dimVectorInicial; i++ ) {
      sumaInicial += evaluaFuncion( vectorInicial[ i ] );
    }

    // Inicio del calculo de la reduccion en secuencial
    t1 = MPI_Wtime() ; // ... (A)
    sumaInicial = 0;
    for( i = 0; i < dimVectorInicial; i++ ) {
      sumaInicial += evaluaFuncion( vectorInicial[ i ] );
    }
    // Finalizacion de la reduccion y calculo de su coste
    t2 = MPI_Wtime() ; // ... (B)
    tSec = t2 - t1;
  }

  // Todos los procesos crean e inicializan "vectorLocal".
  // La siguiente linea no es correcta. Debes arreglarla.
  dimVectorLocal = dimVectorInicial/numProcs; // ... (C)
  vectorLocal = ( double * ) malloc( dimVectorLocal * sizeof( double ) );
  for( i = 0; i < dimVectorLocal; i++ ) {
    vectorLocal[ i ] = -1.0;
  }

  MPI_Barrier( MPI_COMM_WORLD );
  // Distribucion por bloques de "vectorInicial" y calculo de su coste (tDis).
  // Al final de esta fase, cada proceso debe tener sus correspondientes datos
  // propios en su "vectorLocal".
  // ... (D)
  t1 = MPI_Wtime ();
  MPI_Scatter ( vectorInicial, dimVectorLocal, MPI_DOUBLE, vectorLocal, dimVectorLocal, MPI_DOUBLE, 0, MPI_COMM_WORLD);
  t2 = MPI_Wtime ();
  tDis = t2 - t1;

#ifdef IMPRIME
  // Todos los procesos imprimen su vector local.
  for( i = 0; i < dimVectorLocal; i++ ) {
    printf( "Proc: %d.  vectorLocal[ %3d ] = %lf\n",
            miId, i, vectorLocal[ i ] );
  }
#endif

  MPI_Barrier( MPI_COMM_WORLD );
  // Inicio del calculo de la reduccion en paralelo y su coste (tPar).
  // ... (E)
  t1 = MPI_Wtime();
  // Cada proceso suma la aplicacion de la funcion sobre los elementos de vectorLocal
  // ... (F)
  sumaLocal=0;
  for(i=0; i<dimVectorLocal; i++){
    sumaLocal+=evaluaFuncion(vectorLocal[i]);
  }
  // Se acumulan las sumas locales de cada procesador en sumaFinal sobre el proceso 0
  // ... (G)
  MPI_Gather ( &sumaLocal, 1, MPI_DOUBLE, vectorLocal, 1, MPI_DOUBLE, 0, MPI_COMM_WORLD );
  for(i=0; i<numProcs; i++){
    sumaFinal+=vectorLocal[i];
  }

  // Finalizacion del calculo de la reduccion en paralelo y su coste (tPar).
  // ... (H)
  t2 = MPI_Wtime ();
  tPar = t2 - t1;

  // El proceso 0 imprime la sumas, los costes y los incrementos
  if ( miId == 0) {
    // Imprimir Sumas(sumaInicial, sumaFinal, diferencia)
    printf( "Proc: %d , sumaInicial = %lf , sumaFinal = %lf , diff = %lf\n",
                miId, sumaInicial, sumaFinal, sumaInicial - sumaFinal);
    printf( "Proc: %d , tSec = %lf , tPar = %lf , tDis = %lf\n",
                miId, tSec, tPar, tDis);
    // Imprimir Incrementos(tSec vs tPar , tSec vs (tDis+tPar) )
    // ... (I)
    printf( "Proc: %d , speedup = %f , speedup(con distribucion y paralelo) = %f\n",
                miId, tSec/tPar, tSec/(tPar+tDis));
  }

  // El proceso 0 borra el vector inicial.
  if( miId == 0 ) {
    free( vectorInicial );
  }

  // Todos los procesos borran su vector local.
  free( vectorLocal );

  // Finalizacion de MPI.
  MPI_Finalize();

  // Fin de programa.
  printf( "Proc: %d Final de programa\n", miId );
  return 0;
}
