#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

// ============================================================================
int main( int argc, char * argv[] ) {
  int  numProcs, miId, dato, suma, aux;
  MPI_Status  s;
  
  // Inicializa MPI.
  MPI_Init( &argc, &argv );
  MPI_Comm_size( MPI_COMM_WORLD, &numProcs );
  MPI_Comm_rank( MPI_COMM_WORLD, &miId );
  
  // --------------------------------------------------------------------------
  dato = numProcs - miId + 1;
  
  aux = (miId %2 == 0) ? dato : 0;
  MPI_Reduce(&aux, &suma, 1, MPI_INT, MPI_SUM, 0 , MPI_COMM_WORLD);

  if(miId == 0){
    printf( "Soy el proceso %d, mi dato es %d y la suma de todos los valores es %d. \n", miId, dato, suma );

  } else {
    printf( "Soy el proceso %d y mi dato es %d \n", miId, dato );
  }
  // --------------------------------------------------------------------------

  // Finalizacion de MPI.
  MPI_Finalize();

  // Fin de programa.
  printf( "Final de programa (%d) \n", miId );
  return 0;
}

