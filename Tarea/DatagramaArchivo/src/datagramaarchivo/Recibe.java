
package datagramaarchivo;
import java.net.*;
import java.io.*;

/**
 *
 * @author saidn
 */
public class Recibe {
        public static void main(String[] args) {
        try{
            int pto=5000;
            int i=1,contador=0;
            DatagramSocket s= new DatagramSocket(pto);
            DataOutputStream dos=null;
            System.out.println("Esperando datagramas....");
            for(;;){
                DatagramPacket p = new DatagramPacket(new byte[65535],65535);
                s.receive(p);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(p.getData())); 
                Archivo a = (Archivo) ois.readObject();
                ois.close();
                if(contador==0){
                    dos=new DataOutputStream(new FileOutputStream(a.getNombre()));
                    System.out.println("Entre al if");
                    contador++;
                } 
                if(a.getI()==i){
                    dos.write(a.getDatos(),0,a.getBytesEnviados());
                    System.out.println("Nombre "+a.getNombre());
                    System.out.println("Tamanio: "+a.getTam());
                    System.out.println("Bytes recibidos: "+a.getBytesEnviados());
                    System.out.println("Parte recibida: "+a.getI());
                    i++;
                    contador=i;
                }
                if(i==(a.getTam()/7000)+1 && dos.size()<a.getTam()){
                    i=contador;
                }
                if(dos.size()>=a.getTam()){
                    System.out.println("Archivo recibido completo");
                    dos.flush();
                    dos.close();
                    //contador=0;
                }
                s.send(p);
            }
                //System.out.println("Datagrama recibido desde: "+p.getAddress()+" pto: "+p.getPort()+" mensaje: "+new String(p.getData(),0,p.getLength()));
            
                //p.setLength(6500);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
