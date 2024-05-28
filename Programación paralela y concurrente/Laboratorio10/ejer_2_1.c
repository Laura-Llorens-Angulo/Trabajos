#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

// ============================================================================
int main( int argc, char * argv[] ) {
  int  numProcs, miId, dato;
  MPI_Status  s;
  
  // Inicializa MPI.
  MPI_Init( &argc, &argv );
  MPI_Comm_size( MPI_COMM_WORLD, &numProcs );
  MPI_Comm_rank( MPI_COMM_WORLD, &miId );
  
  // --------------------------------------------------------------------------
  dato = numProcs - miId + 1;
  if(miId == 0){
    int aux;
    int suma = dato;

    for(int i = 2; i < numProcs ; i+=2){
      MPI_Recv(&aux, 1, MPI_INT, MPI_ANY_SOURCE, 88, MPI_COMM_WORLD,&s);
      suma += aux;
    }

    printf( "Soy el proceso %d, mi dato es %d y la suma de todos los valores es %d. \n", miId, dato, suma );

  } else {
    printf( "Soy el proceso %d y mi dato es %d \n", miId, dato );
    if (miId % 2 == 0){
    MPI_Send(&dato, 1, MPI_INT, 0, 88, MPI_COMM_WORLD);
    }
  }
  // --------------------------------------------------------------------------

  // Finalizacion de MPI.
  MPI_Finalize();

  // Fin de programa.
  printf( "Final de programa (%d) \n", miId );
  return 0;
}

