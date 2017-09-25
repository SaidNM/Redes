/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memorama;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Cliente {
    private static final String HOST = "127.0.0.1";
    private static final int PUERTO = 7805;
            
    public void conectarse() throws IOException {
        Socket cl = new Socket(HOST,PUERTO);
        System.out.println("Conexion establecida");
        File nuevo = new File("imagenes");
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
        for(int i=0; i<10;i++){    
            DataInputStream d = new DataInputStream(cl.getInputStream());
            String nombre = d.readUTF();
            int n;
            long tam = d.readLong();
            DataOutputStream a = new DataOutputStream(new FileOutputStream("imagenes/"+nombre));
            long recibidos =0;
            while(recibidos<tam){
                byte[] b = new byte[1500];
                n= d.read(b);
                a.write(b,0,n);
                a.flush();
                recibidos=recibidos+n;
            }
            System.out.println("Archivo recibido");
        }
    }
    public void enviar_registro(ArrayList<Float> registro) throws IOException{
        String datos="Tiempos: ";
        for(Float dato: registro){
            datos+=(Float.toString(dato))+"-----";
        }
        Socket cl = new Socket(HOST,PUERTO);
        System.out.println("Conexion establecida");
        DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
        dos.writeUTF(datos);
        dos.flush();
        cl.close();
    }
}
