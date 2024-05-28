#include <stdio.h> // Definicion de rutinas para E/S
#include <mpi.h>   // Definicion de rutinas de MPI

// Programa principal
int main(int argc, char *argv[])
{
  // Declaracion de variables
  int miId, numProcs,n;

  // Inicializacion de MPI
  MPI_Init(&argc, &argv);

  // Obtiene el numero de procesos en ejecucion
  MPI_Comm_size(MPI_COMM_WORLD, &numProcs); // Obtiene el numero de procesos en ejecucion
  // Obtiene el identificador del proceso
  MPI_Comm_rank(MPI_COMM_WORLD, &miId); // Obtiene el identificador del proceso

  // ------ PARTE CENTRAL DEL CODIGO (INICIO) ---------------------------------

  if(miId == 0){
    printf("Ingrese un número: \n");
    for(i = 1; i < numProcs; i++){
        MPI_Send(n, 1, MPI_INT, MPI_ANY_SOURCE, 88, MPI_COMM_WORLD)
    }

  }else{
    MPI_Status s;
    MPI_Recv(n, 1, MPI_INT, 0, 88, MPI_COMM_WORLD, &s);
  }

  printf("Proceso %d con n = %d \n", miId,n);
  // ------ PARTE CENTRAL DEL CODIGO (FINAL) ----------------------------------

  // Finalizacion de MPI
  MPI_Finalize();

  return 0;
}
