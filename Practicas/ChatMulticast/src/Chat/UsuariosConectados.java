/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.JOptionPane;


public class UsuariosConectados implements Serializable{
    static MulticastSocket Servidor;
    static InetAddress Grupo;
    static byte[] bufer = new byte[1024];
    static DatagramPacket mensajeEntrada;
    static String linea;
    static String Conectados="";
    
    public static void EscribeMensaje(String msg) throws IOException{
        byte[] m = msg.getBytes();
        DatagramPacket mensajeSalida = new DatagramPacket(m, m.length, Grupo, 4000);
        Servidor.send(mensajeSalida);
        System.out.println("Actualizan lista todos...");
    }
    
    public static void Conectate() throws IOException{
        Servidor = new MulticastSocket(4000);
        Grupo = InetAddress.getByName("230.1.1.1");
        Servidor.joinGroup(Grupo);
        System.out.println("Unido al grupo");
        while(true){
            try{
                mensajeEntrada = new DatagramPacket(bufer, bufer.length);
                Servidor.receive(mensajeEntrada);
                linea = new String(mensajeEntrada.getData(), 0, mensajeEntrada.getLength());
                if(linea.indexOf("Inicio sesión")!=-1){
                    linea="#"+linea.substring(linea.indexOf("[")+1, linea.indexOf("]"));
                    Conectados+=linea;
                    EscribeMensaje(Conectados);
                }
                else if(linea.indexOf("Se ha desconectado")!=-1){
                    linea=linea.substring(linea.indexOf("[")+1, linea.indexOf("]"));
                    Conectados=Conectados.replaceFirst("#"+linea, "");
                    EscribeMensaje(Conectados);
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,"Ocurrio un problema con los usuarios [Conectados/Desconectados]");
            }
        }
    }
    
    public static void main(String args[]) throws IOException{
        Conectate();
    }
}