import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Server_Thread extends Thread {
  int pto;
  int cubeta[];

  public Server_Thread(int pto){
    this.pto=pto;
    System.out.print("Recibiendo datos..\n");

  }
  public void run(){
    try {
      ServerSocket ss = new ServerSocket(pto);
      Socket socket = ss.accept();
      DataInputStream dis = new DataInputStream(socket.getInputStream());
      DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
      int elementos = dis.readInt();
      cubeta = new int[elementos];

      for (int i=0;i<elementos ;i++ ) {
        cubeta[i]=dis.readInt();
      }
      
      System.out.println("Ordenando los datos recibidos");
      Arrays.sort(cubeta);
            
      for (int i : cubeta) {
        dos.writeInt(i);
        dos.flush();
      }

      System.out.println("\nEnviando la cubeta ordenada\n");
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
}
