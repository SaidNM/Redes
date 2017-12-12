/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat;

import fonts.fontLibrary;
import java.awt.Image;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;


public class HiloRecibeMensajes extends Thread{
    DatagramPacket mensajeEntrada;
    byte[] bufer = new byte[1024];
    String linea;
    MulticastSocket Cliente;
    JTable ListaUsuarios;
    String Nombre;
    String NombrePrivado="";
    String MiNombre;
    static String FilaTabla[] = new String[1];
    ArrayList<Integer> IndicesNumerales = new ArrayList<Integer>();
    JEditorPane AreaEmoticones;
    String LoqueHabia="";
    String QuienEnvia="";
    
    public HiloRecibeMensajes(MulticastSocket Cliente, JTable Usuarios,String YoSoyPrivado,JEditorPane Emoticones){
        
        this.Cliente=Cliente;
        this.ListaUsuarios=Usuarios;
        this.MiNombre=YoSoyPrivado;
        this.AreaEmoticones=Emoticones;        
        AreaEmoticones.setFont(fontLibrary.setRobotoRegular(12));
    }
    
    public void run(){
        while(true){
            try{
                mensajeEntrada = new DatagramPacket(bufer, bufer.length);
                Cliente.receive(mensajeEntrada);
                linea = new String(mensajeEntrada.getData(), 0, mensajeEntrada.getLength());
                if(linea.indexOf("#")!=-1){
                    IndicesNumerales.clear();
                    DefaultTableModel DTMVaciado=(DefaultTableModel) ListaUsuarios.getModel();
                    int Filas=ListaUsuarios.getRowCount();
                    for(int i=0;Filas>i; i++){
                        DTMVaciado.removeRow(0);
                    }
                    for(int i=0;i<linea.length();i++){
                        if(linea.charAt(i)=='#'){
                            if(!IndicesNumerales.contains(i)){
                                IndicesNumerales.add(i);
                            }
                        }
                    }
                    for(int j=0;j<IndicesNumerales.size();j++){
                        if(j==IndicesNumerales.size()-1){
                            Nombre=linea.substring(IndicesNumerales.get(j)+1,linea.length());
                        }
                        else{
                            Nombre=linea.substring(IndicesNumerales.get(j)+1,IndicesNumerales.get(j+1));
                        }
                        DefaultTableModel DTM=(DefaultTableModel) ListaUsuarios.getModel();
                        FilaTabla[0]=Nombre;
                        DTM.addRow(FilaTabla);
                    }
                }
                else{
                    if(linea.indexOf("&")!=-1){
                        QuienEnvia=linea.substring(0,linea.indexOf("&"));
                        NombrePrivado=linea.substring(linea.indexOf("&")+1,linea.indexOf("$"));
                        if(MiNombre.equals(NombrePrivado)){
                            linea = "<font face = \"Roboto\" <i>[Privado]</i> <b>"+QuienEnvia+"</b>: "+linea.substring(linea.indexOf("$")+1, linea.length())+"</font>";
                            //CHECA SI HAY EMOTICONES
                            ReemplazaEmoticones();
                            LoqueHabia+=linea+"<br>";
                            System.out.println(LoqueHabia);
                            AreaEmoticones.setText(LoqueHabia);
                        }
                    }
                    else{
                        //CHECA SI HAY EMOTICONES
                        ReemplazaEmoticones();
                        LoqueHabia+=linea+"<br>";
                        System.out.println(LoqueHabia);
                        AreaEmoticones.setContentType("text/html");
                        AreaEmoticones.setText("<font face = \"Roboto\">"+LoqueHabia+"</font>");
                    }
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,"Ocurrio un problema");
            }
        }
    } 
    
    private void ReemplazaEmoticones() {
        linea=linea.replace(":)", "<img src="+"\""+"file:src\\Imagenes\\Feliz.png"+"\""+" width=\"4px\" height=\"4px\"/>");
        linea=linea.replace(":(", "<img src="+"\""+"file:src\\Imagenes\\Triste.png"+"\""+" width=\"4px\" height=\"4px\"/>");
        linea=linea.replace(":'(","<img src="+"\""+"file:src\\Imagenes\\Llora.png"+"\""+" width=\"4px\" height=\"4px\"/>");
        linea=linea.replace(":*", "<img src="+"\""+"file:src\\Imagenes\\Amor.png"+"\""+" width=\"4px\" height=\"4px\"/>");
        linea=linea.replace(";)", "<img src="+"\""+"file:src\\Imagenes\\Picaro.png"+"\""+" width=\"4px\" height=\"4px\"/>");
        linea=linea.replace("B|", "<img src="+"\""+"file:src\\Imagenes\\Lentes.png"+"\""+" width=\"4px\" height=\"4px\"/>");
        linea=linea.replace(":P", "<img src="+"\""+"file:src\\Imagenes\\Lengua.png"+"\""+" width=\"4px\" height=\"4px\"/>");
        AreaEmoticones.repaint();
    }
}
