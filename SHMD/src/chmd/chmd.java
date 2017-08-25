package chmd;
//implementar el envio de datagramas con un buffer de 10 bytes
import java.net.*;
import java.io.*;

public class chmd {
    public static void main(String[] args) {
        try{
            DatagramSocket cl = new DatagramSocket();
            System.out.println("Escribe la direccion del servidor: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String host = br.readLine();
            String saludo = "Hola servidor como estas";
            
            byte[] b = saludo.getBytes();
            DatagramPacket p = new DatagramPacket(b,b.length,InetAddress.getByName(host),5000);
            cl.send(p);
            
            DatagramPacket p1 = new DatagramPacket(new byte[1500],1500);
            cl.receive(p1);
            
            System.out.println("Saludo desde: "+p1.getSocketAddress()+" pto: "+p1.getPort()+" mensaje: "+new String(p1.getData(),0,p1.getLength()));
            br.close();
            cl.close();
        }catch(Exception e){
        e.printStackTrace();
        }
    }
}
