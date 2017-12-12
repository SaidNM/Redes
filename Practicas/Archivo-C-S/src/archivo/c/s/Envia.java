package archivo.c.s;
import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;
import java.util.List;

public class Envia {
private String ruta;
private String nombre;
private long tam;
private long enviados;

    public Envia() {
        this.ruta = "";
        this.nombre = "";
        this.tam = 0;
        this.enviados = 0;
    }
     
    public void enviar_archivo(File f[]){
        for(int i=0;i<f.length;i++){
        try{
        String host="127.0.0.1";
                int pto=8000,porcentaje=0,n;
                Socket cl=new Socket(host,pto);
                System.out.println("Conexion establecida, comienza envio del archivo");
                DataOutputStream dos=new DataOutputStream(cl.getOutputStream());
                DataInputStream dis=new DataInputStream(new FileInputStream(f[i].getAbsolutePath()));
                nombre=f[i].getName();
                dos.writeUTF(nombre);
                dos.flush();
                dos.writeLong(f[i].length());
                dos.flush();
                enviados=0;
                while(enviados<f[i].length()){
                    byte[] b = new byte[1500];
                    n= dis.read(b);
                    dos.write(b,0,n);
                    dos.flush();
                    enviados = enviados+n;
                    porcentaje=(int)((enviados*100)/f[i].length());
                    System.out.println("Se ha transmitido el "+porcentaje+"%");
                }
                System.out.println("Archivo enviado...");
            }
            catch(Exception e){
            e.printStackTrace();
        }
        }
    }
    public void enviar_archivo(List<File> f){
        for(File file:f){
            if (file.isDirectory()){
                enviar_archivo(file.listFiles());
                System.out.println("Soy carpeta no se que hacer jaja ");
            }else { // Manda un solo archivo
                try{
                    String host="127.0.0.1";
                    int pto=8000,porcentaje=0,n;
                    Socket cl=new Socket(host,pto);
                    System.out.println("Conexion establecida, comienza envio del archivo");
                    DataOutputStream dos=new DataOutputStream(cl.getOutputStream());
                    DataInputStream dis=new DataInputStream(new FileInputStream(file.getAbsolutePath()));
                    nombre=file.getName();
                    dos.writeUTF(nombre);
                    dos.flush();
                    dos.writeLong(file.length());
                    dos.flush();
                    enviados=0;
                    while(enviados<file.length()){
                        byte[] b = new byte[1500];
                        n= dis.read(b);
                        dos.write(b,0,n);
                        dos.flush();
                        enviados = enviados+n;
                        porcentaje=(int)((enviados*100)/file.length());
                        System.out.println("Se ha transmitido el "+porcentaje+"%");
                    }
                    System.out.println("Archivo enviado...");
                }
                catch(Exception e){
                e.printStackTrace();
                }
            }
        }
    }
}