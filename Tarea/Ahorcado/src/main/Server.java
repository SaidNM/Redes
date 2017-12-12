
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try
        {
            ServerSocket s = new ServerSocket(1234); //1024 - 65535
            System.out.println("Servicio iniciado... esperando clientes..");
            
            for(;;){
                Socket cl = s.accept();
                System.out.println("Cliente conectado desde " 
                        + cl.getInetAddress()+ " : "
                        + cl.getPort());
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
                
                for(;;)
                {
                    String msj = br.readLine();
                    if(msj.toLowerCase().contains("salir"))
                    {
                        pw.close();
                        br.close();
                        cl.close();
                        break;
                    }                    
                    else
                    {
                        System.out.println("Mensaje recibido:" + msj);
                        msj = generaFrase(Integer.parseInt(msj));
                        pw.println(msj);
                        pw.flush();
                        continue;
                    }
                } 
            }
                        
        }catch(IOException e){ 
            e.printStackTrace();
        }
    }


    //Genera una frase aleatoria dada la dificultad pedida
    private static String generaFrase(int level){
        
        String s = null;
        
        String easy [] = {
            "Un perro cafe",
            "Ayer fue viernes",
            "Mi mamá me mima",
            "Limonada fria",
            "Esta es una frase",
            "Vamos a comer",
            "Anita lava la tina"           
        };
        
        String medium [] = {
            "Brinca la cuerdita, yo ya la brinque",
            "Que haces besando a la liciada",
            "Dos y dos son cuatro, cuatro y dos son seis",
            "Ya casi llega la primavera",
            "Te observo con asombro"            
        };
        
        String hard [] = {
            "He visto las ciudades de muchos hombres, y he aprendido sus costumbres",
            "Apaciguada, la noche seguia su rumbo hacia el amanecer",
            "Era un día luminoso y frío de abril y los relojes daban las trece horas",
            "Alebresto una vez más al ver que sus derechos eran violados",
            "Determinadas cosas suceden de forma indeterminada",
            "Dividieron el postre en partes iguales"
        };
        
        Random rnd = new Random();
        
        //Determina de que arreglo tomar las frases segun el nivel seleccionado
        String arregloString [] = (level == 1) ? easy : (level == 2) ? medium : hard;
        
        //Elige una frase aleatoria del arreglo elegido, si es la misma que la pasada elegir otra.
//        while(s == anteriorFrase || s == null){    
            s = arregloString [(int) (rnd.nextDouble() * arregloString.length)];
//        }
         
        System.out.println("Frase enviada: "+s+"\n");
        return s;
    }
}
