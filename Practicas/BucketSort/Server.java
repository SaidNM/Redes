import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server  {
  int []numbers;
  int cubetas, pto = 8000;
  ServerSocket ssmain;
  int i;

  public Server(){
    numbers = new int [3000];
    mainConnection(ssmain);
  }
  public void mainConnection(ServerSocket ss){
    try{
      ss = new ServerSocket(5000);
      ss.setReuseAddress(true);
      System.out.println("Servidor iniciado....");
      Socket socket = ss.accept();
      System.out.println("Cliente conectado a traves de "+ socket);
      DataInputStream dis = new DataInputStream(socket.getInputStream());
      DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
      cubetas = dis.readInt();
      System.out.println("Numero de servidores a ser utilizados "+ cubetas);
      Connection(cubetas);
    }catch (EOFException e) {
      e.printStackTrace();
    }catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  public void Connection(int n){
    for (i=0; i<n; i++, pto++) {
      new Server_Thread(pto).start();
    }
  }
  public static void main(String[] args) {
    Server s = new Server();
  }
}
