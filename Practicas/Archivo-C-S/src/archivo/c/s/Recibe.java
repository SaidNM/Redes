
package archivo.c.s;

import java.net.*;
import java.io.*;

public class Recibe {
    public static void main(String[] args) {
        try{
            int pto=8000,n;
            ServerSocket s = new ServerSocket(pto);
            s.setReuseAddress(true);
            System.out.println("Servicio Iniciado");
            for(;;){
                Socket cl=s.accept();
                System.out.println("Cliente conectado desde "+cl.getInetAddress());
                DataInputStream d = new DataInputStream(cl.getInputStream());
                String nombre = d.readUTF();
                long tam = d.readLong();
                DataOutputStream a = new DataOutputStream(new FileOutputStream(nombre));
                long recibidos =0;
                while(recibidos<tam){
                    byte[] b = new byte[1500];
                    n= d.read(b);
                    a.write(b,0,n);
                    a.flush();
                    recibidos=recibidos+n;
                }
                System.out.println("Archivo Recibido");
                a.close();
                d.close();
                cl.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
