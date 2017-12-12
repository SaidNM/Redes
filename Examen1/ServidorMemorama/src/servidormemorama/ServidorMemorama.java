/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidormemorama;

import java.io.*;
import java.net.*;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author saidn
 */
public class ServidorMemorama {
    private static final int PUERTO = 7805;
    
    public static void main(String[] args) throws IOException {
        File imagenes = new File("C:/Users/saidn/Documents/Redes/ServidorMemorama/imagenes");
        ServerSocket ss = new ServerSocket(PUERTO);
        ss.setReuseAddress(true);
        System.out.println("Servidor iniciado");
        boolean enviado=false;

        //Obtener imagenes y enviarlas
        for(;;){
        if(!enviado){
            Socket cl=ss.accept();
                System.out.println("Cliente conectado");
                if(imagenes.isDirectory()){
                    for(File img: imagenes.listFiles()){
                        System.out.println("Comienza envio de archivos");
                        DataOutputStream dos=new DataOutputStream(cl.getOutputStream());
                        DataInputStream dis = new DataInputStream(new FileInputStream(img.getAbsolutePath()));
                        long tam=img.length();
                        String nombre = img.getName();
                        int enviados=0,n;
                        dos.writeUTF(nombre);
                        dos.flush();
                        dos.writeLong(tam);
                        dos.flush();
                        while(enviados<tam){
                            byte[] b = new byte[1500];
                            n= dis.read(b);
                            dos.write(b,0,n);
                            dos.flush();
                            enviados = enviados+n;
                            //System.out.println("Porcentaje enviado.."+((enviados/tam)*100));
                        }
                        enviado=true;
                        System.out.println("Archivos enviado...");
                    }
                cl.close();
            }
        }else{
            Socket cl1 = ss.accept();
                System.out.println("Cliente conectadp");
            File nuevo = new File("registros");
            if(!nuevo.exists()){
                try{
                    if(nuevo.mkdir())  
                        System.out.println("Carpeta Creada");
                    else
                        System.out.println("No se pudo crear la carpeta");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            DataInputStream dis1 = new DataInputStream(cl1.getInputStream());
            DataOutputStream dos2 = new DataOutputStream(new FileOutputStream("registros/registro.txt"));
            String mensaje = dis1.readUTF();
            dos2.writeUTF(mensaje);
            dis1.close();
            dos2.close();
            }
            //Recibir registros
        }
    }
}