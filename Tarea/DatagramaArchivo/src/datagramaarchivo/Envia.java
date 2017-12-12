/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datagramaarchivo;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import javax.swing.JFileChooser;

/**
 *
 * @author saidn
 */
public class Envia {

    public static void main(String[] args) {
        
        try{
            DatagramSocket cl = new DatagramSocket();
            JFileChooser jf = new JFileChooser();
            int r = jf.showOpenDialog(null);
            if(r==JFileChooser.APPROVE_OPTION){
                File f = jf.getSelectedFile();
                DataInputStream dis=new DataInputStream(new FileInputStream(f.getAbsolutePath()));
                long tam = dis.available();
                long enviado=0;
                int i=0,n=0;
                cl.setSoTimeout(3000);
                while(enviado<tam){
                    Archivo file = new Archivo(f.getName(),f.getAbsolutePath(),f.length());
                    file.setI(++i);
                    byte[] buf = new byte[7000];
                    n=dis.read(buf);
                    file.setDatos(buf);
                    file.setBytesEnviados(n);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(file);
                    oos.flush();
                    byte[] msj = baos.toByteArray();
                    DatagramPacket p = new DatagramPacket(msj,msj.length,InetAddress.getByName("localhost"),5000);
                    cl.send(p);
                    enviado+=n;
                    if(i>=(int)((tam/7000)+1)){
                        System.out.println("Archivo enviado");
                    }
                }
            }
         
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
