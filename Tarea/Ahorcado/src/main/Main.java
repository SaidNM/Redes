
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import main.Ahorcado;

   
public class Main 
{
    
 
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {//      
        try{
            BufferedReader br1 = new BufferedReader( new InputStreamReader(System.in));
            
            System.out.println("\nEscribe la direccion del servidor:");
            String host = br1.readLine();
            
            System.out.println("\nEscribe el puerto remoto:");
            int pto = Integer.parseInt(br1.readLine());
            
            //Genera socket cliente
            Socket cl = new Socket(host, pto);
            
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            
            
            System.out.println("\nElige la dificultad del ahorcado");
            System.out.println("1. Facil");
            System.out.println("2. Moderada");
            System.out.println("3. Dificil");
            System.out.println("0. Salir");
            
             
            //System.out.println("\nEscribe un mensaje, <ENTER> para enviar, \"salir\" para terminar");
            
            for(;;)
            {  
                 
                int diff = Integer.parseInt(br1.readLine()); 
 
                if(diff == 0)
                {
                    pw.println(diff);
                    pw.flush();
                    System.out.println("Adios");
                    pw.close();
                    br2.close();
                    br1.close();
                    cl.close();
                    System.exit(0);
                }
                
                else
                {
                    pw.println(diff);
                    pw.flush();
                    String frase = br2.readLine(); 
                    new Ahorcado(frase);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     
}
