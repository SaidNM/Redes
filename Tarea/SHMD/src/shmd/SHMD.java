package shmd;
import java.net.*;
import java.io.*;

public class SHMD {
    public static void main(String[] args) {
        try{
            int pto=5000;
            DatagramSocket s= new DatagramSocket(pto);
            System.out.println("Esperando datagramas....");
            while(true){
                DatagramPacket p = new DatagramPacket(new byte[1500],1500);
                s.receive(p);
                System.out.println("Datagrama recibido desde: "+p.getAddress()+" pto: "+p.getPort()+" mensaje: "+new String(p.getData(),0,p.getLength()));
                System.out.println("Se devuelve saludo");
                s.send(p);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
