import java.net.Socket;
import java.net.ServerSocket;
import java.nio.ByteBuffer; 
import java.lang.Thread;
import java.io.DataOutputStream; 
import java.io.DataInputStream;

class MatrizMultiplicacion {
    static int N = 1000;
    static int  ax,bx, numNod;
    static long cx,checksum = 0;
    static int[][] A = new int[N][N];
    static int[][] B = new int[N][N];
    static long[][] C = new long[N][N];
    static int[][] AX = new int[N/2][N];
    static int[][] BX = new int[N/2][N];
    static long[][] CX = new long[N/2][N/2];

    static class Worker extends Thread{
        Socket conexion;
        int node; 
        Worker(Socket conexion,int node){
            this.conexion = conexion;
            this.node = node;
        }

        public void run(){
            try {
                System.out.println("[SERVER]Connected to NODE "+(node+1));
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());

                int node = entrada.readInt();
       
                if (node == 1){
                    System.out.println("NODE 1 INI");
                    // Se envia A1
                    for (int i = 0; i < N/2; i++)
                        for (int j = 0; j < N; j++){
                            ax = A[i][j];
                            salida.writeInt(ax);
                        }
                   // System.out.println("    Matriz A1 ENVIADA...");
                    // Enviar B1  
                    for (int i = 0; i < N/2; i++)
                        for (int j = 0; j < N; j++){
                            bx= B[i][j];
                            salida.writeInt(bx);
                        }
                    //System.out.println("    Matriz B1 ENVIADA...");
                    //Recibe C1
                    for (int i = 0; i < N/2; i++)
                        for (int j = 0; j < N/2; j++){
                            C[i][j] = entrada.readLong();
                        }
                    //System.out.println("    Matriz C1 RECIBIDA...");  
                    System.out.println("NODE 1 FIN");
                }
                else if (node == 2) {
                    System.out.println("NODE 2 INI");
                    // Enviar A1
                    for (int i = 0; i < N/2; i++)
                        for (int j = 0; j < N; j++){
                            ax = A[i][j];
                            salida.writeInt(ax);
                        }
                    //System.out.println("    Matriz A1 ENVIADA...");
                    // Enviar B2  
                    for (int i = N/2; i < N; i++)
                        for (int j = 0; j < N; j++){
                            bx= B[i][j];
                            salida.writeInt(bx);
                        } 
                    //System.out.println("    Matriz B2 ENVIADA...");
                    //Recibe C2
                    for (int i = 0; i < N/2; i++)
                        for (int j = N/2; j < N; j++){
                            C[i][j] = entrada.readLong();
                        } 
                    //System.out.println("    Matriz C2 RECIBIDA...");  
                    System.out.println("NODE 2 FIN");
                }
                else if (node == 3){
                    System.out.println("NODE 3 INI");
                    // Enviar A2
                    for (int i = N/2; i < N; i++)
                        for (int j = 0; j < N; j++){
                            ax= A[i][j];
                            salida.writeInt(ax);
                        }
                    //System.out.println("    Matriz A2 ENVIADA...");
                    // Enviar B1  
                    for (int i = 0; i < N/2; i++)
                        for (int j = 0; j < N; j++){
                            bx= B[i][j];
                            salida.writeInt(bx);
                        }
                   // System.out.println("    Matriz B1 ENVIADA...");
                    //Recibe C3
                    for (int i = N/2; i < N; i++)
                        for (int j = 0; j < N/2; j++){
                            C[i][j] = entrada.readLong();
                        }   
                    //System.out.println("    Matriz C3 RECIBIDA...");  
                    System.out.println("NODE 3 FIN");
                }
                else if (node == 4) {
                    System.out.println("NODE 4 INI");
                    // Enviar A2
                    for (int i = N/2; i < N; i++)
                        for (int j = 0; j < N; j++){
                            ax= A[i][j];
                            salida.writeInt(ax);
                        }
                   // System.out.println("    Matriz A2 ENVIADA...");
                    // Enviar B2  
                    for (int i = N/2; i < N; i++)
                        for (int j = 0; j < N; j++){
                            bx= B[i][j];
                            salida.writeInt(bx);
                        }
                    //System.out.println("    Matriz B2 ENVIADA...");
                    //Recibe C4
                    for (int i = N/2; i < N; i++)
                        for (int j = N/2; j < N; j++){
                            C[i][j] = entrada.readLong();
                        } 
                    //System.out.println("    Matriz C4 RECIBIDA...");  
                    System.out.println("NODE 4 FIN"); 
                }
                salida.close();
                entrada.close();
                conexion.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void nodeServer() throws Exception{
        System.out.println("SERVER ... ");
        //llenado de matrices A,B,C
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++){
                A[i][j] = 2 * i + j;
                B[i][j] = 2 * i - j;
                C[i][j] = 0;
            }
      //trasnpuesta matriz B
      for ( int i = 0; i < N; i++)
        for ( int j = 0; j < i; j++){
          int x = B[i][j];
          B[i][j] = B[j][i];
          B[j][i] = x;
        }

        ServerSocket servidor = new ServerSocket(50000);
        Worker w[] = new Worker[4];

        //Aceptamos nodos
        for (int i = 0; i < 4; ++i){
            w[i] = new Worker(servidor.accept(), i);
            w[i].start();
        }
        // Espera de nodos
        for (int i = 0; i < 4; ++i)w[i].join();

        //Calculamos Checksum e imprimos
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)          
                checksum +=  C[i][j];

      //imprime checksum
      System.out.println("");
      System.out.println("  CHECKSUME : "+checksum);
    
      if(N == 4){
        System.out.print("  Matriz C:\n");
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++)
                System.out.print(" "+C[i][j]);
        System.out.println("");
        }
      }
        servidor.close();
    }

    public static void nodeCliente(int node) throws Exception{
        System.out.println("[NODE] "+node + " Connected ");
        Socket conexion = null;
       for(;;)
            try{
                conexion = new Socket("localhost",50000);
                break;
            }
            catch (Exception e){
                Thread.sleep(100);
            }
        
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());

        salida.writeInt(node);
        //Recibe Ax
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N; j++)
                AX[i][j] = entrada.readInt();
        //Recibe Bx
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N; j++)
                BX[i][j] = entrada.readInt();
        // Multiplica Ax * Bx y guarda en Cx
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++)
                for (int k = 0; k < N; k++)
                    CX[i][j] += AX[i][k] * BX[j][k];
        //Envia Cx
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++){
                cx = CX[i][j];
                salida.writeLong(cx);
        }
        salida.close();
        entrada.close();
        conexion.close();
    }

    public static void main(String [] args)throws Exception{
        if (args.length < 1 || args.length > 2){
            System.err.println("Uso:");
            System.err.println("java MatrizMultiplicacion <nodo>");
            System.exit(0);
        }
        int node = Integer.valueOf(args[0]);

        if(node == 0){
            nodeServer();
        }
        else{
            nodeCliente(node);
        }
    }
}