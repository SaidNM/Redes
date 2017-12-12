package chmd;
//implementar el envio de datagramas con un buffer de 10 bytes
import java.net.*;
import java.io.*;

public class chmd {
    public static void main(String[] args) {
        try{
            DatagramSocket cl = new DatagramSocket();
            System.out.println("Escribe un mensaje: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String saludo =br.readLine();
            
            byte[] bm = saludo.getBytes();
            ByteArrayInputStream bais = new ByteArrayInputStream(bm);
            
            if(bm.length>=10){
                int n=0;
                byte [] b = new byte[10];
                while((n=bais.read(b))!=-1){ 
                    DatagramPacket p = new DatagramPacket(b,n,InetAddress.getByName("localhost"),5000);
                    cl.send(p);

                    DatagramPacket p1 = new DatagramPacket(new byte[1500],1500);
                    cl.receive(p1);
                    
                    System.out.println("Saludo desde: "+p1.getSocketAddress()+" pto: "+p1.getPort()+" mensaje: "+new String(p1.getData(),0,p1.getLength()));
                }    
            }
            br.close();
            cl.close();
        }catch(Exception e){
        e.printStackTrace();
        }
    }
}
