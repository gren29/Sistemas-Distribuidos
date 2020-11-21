import java.rmi.*;
import java.rmi.server.*;

public class ClienteMultiplicacion{

  public static int N = 4;
  public static void main(String args[]) throws Exception{
      System.out.println("HOLA");
    String url1 = "rmi://23.98.145.157:1099/prueba";
    String url2 = "rmi://23.102.129.26:1099/prueba";
    String url3 = "rmi://104.214.30.219:1099/prueba";
    String url4 = "rmi://23.100.125.249:1099/prueba";

    InterfaceMulultiplicacion r1 = (InterfaceMulultiplicacion)Naming.lookup(url1);
    InterfaceMulultiplicacion r2 = (InterfaceMulultiplicacion)Naming.lookup(url2);
    InterfaceMulultiplicacion r3 = (InterfaceMulultiplicacion)Naming.lookup(url3);
    InterfaceMulultiplicacion r4 = (InterfaceMulultiplicacion)Naming.lookup(url4);

    
    int [][] A = new int [N][N];
    int [][] B = new int [N][N];
    int [][] C = new int [N][N];
        
    for(int i = 0; i < N; i++){
        for(int j = 0; j < N; j++){
            A[i][j]=2 * i - j;
            B[i][j] = 2 * i + j;
        }
    }

    B = transpuestaB(B);
    
    int[][] A1 = parte_matriz(A,0);    
    int[][] A2 = parte_matriz(A,N/2);    
    int[][] B1 = parte_matriz(B,0);    
    int[][] B2 = parte_matriz(B,N/2);

    int[][] C1 = r1.multiplica_matrices(A1,B1);
    int[][] C2 = r2.multiplica_matrices(A1,B2);
    int[][] C3 = r3.multiplica_matrices(A2,B1);
    int[][] C4 = r4.multiplica_matrices(A2,B2);

    C = acomoda_matriz(C,C1,0,0);
    C = acomoda_matriz(C,C2,0,N/2);
    C = acomoda_matriz(C,C3,N/2,0);
    C = acomoda_matriz(C,C4,N/2,N/2);

    if(N==4){
    	mostrarMatriz(A,"A");
    	mostrarMatriz(B,"B");
    	mostrarMatriz(C,"C");
    }
    
    calcularChecksum(C);
  }

  public static int[][] acomoda_matriz(int[][] C,int[][] A,int renglon,int columna) throws RemoteException{
    for (int i = 0; i < N/2; i++)
      for (int j = 0; j < N/2; j++)
        C[i + renglon][j + columna] = A[i][j];

    return C;
  }

  public static void mostrarMatriz(int[][] M, String cad) throws RemoteException{
      System.out.print("\n[MATRIZ] "+cad+"n");
      for(int i = 0; i < N; i++){
          for(int j = 0; j < N; j++){
              System.out.print(M[i][j]+", ");
          }
          System.out.print("\n");
      }
  }

  public static void calcularChecksum(int[][] M) throws RemoteException{
    long checksum = 0;
    for (int i = 0; i < N; i++){
      for (int j = 0; j < N; j++){
        checksum = checksum + M[i][j];
      }
    } 
        
    System.out.println("\n[CHECKSUM] = "+checksum);
  }

  public static int[][] parte_matriz(int[][] A,int inicio) throws RemoteException{
    int[][] M = new int[N/2][N];
    for (int i = 0; i < N/2; i++)
      for (int j = 0; j < N; j++)
        M[i][j] = A[i + inicio][j];
    return M;
  }

  public static int [][] transpuestaB(int [][] B) throws RemoteException{
    for (int i = 0; i < N; i++)
      for (int j = 0; j < i; j++){
        int x = B[i][j];
        B[i][j] = B[j][i];
        B[j][i] = x;
      }
    return B;
  }
}