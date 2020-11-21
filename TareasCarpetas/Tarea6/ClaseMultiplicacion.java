import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClaseMultiplicacion extends UnicastRemoteObject implements InterfaceMulultiplicacion{
  // es necesario que el contructor ClaseRMI() invoque el constructor de la superclase
  public int N = 4;
  public ClaseMultiplicacion() throws RemoteException{
    super( );
  }
  
  public int[][] multiplica_matrices(int[][] A,int[][] B) throws RemoteException{
    int[][] C = new int[N/2][N/2];
    for (int i = 0; i < N/2; i++)
      for (int j = 0; j < N/2; j++)
        for (int k = 0; k < N; k++)
          C[i][j] += A[i][k] * B[j][k];
    return C;      
  }
}