/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.JTextArea;


class Cliente {
    
    static MulticastSocket Cliente;
    static InetAddress Grupo;
    static DatagramPacket mensajeEntrada;
    static String linea;
    static HiloRecibeMensajes Receptor;
    
    public void Conectate(JTable ListaUsuarios,String YoSoy, JEditorPane Emoticones) throws IOException{
        Cliente = new MulticastSocket(4000);
        Grupo = InetAddress.getByName("230.1.1.1");
        Cliente.joinGroup(Grupo);
        Receptor=new HiloRecibeMensajes(Cliente,ListaUsuarios,YoSoy,Emoticones);
        Receptor.start();
        System.out.println("Unido al grupo");
    }
    
    public void EscribeMensaje(String msg) throws IOException{
        byte[] m = msg.getBytes();
        DatagramPacket mensajeSalida = new DatagramPacket(m, m.length, Grupo, 4000);
        Cliente.send(mensajeSalida);
        System.out.println("Mensaje enviado...");
    }

    void Desconectar() throws IOException {
        Cliente.leaveGroup(Grupo);
        Receptor.stop();
    }
}
