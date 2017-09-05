/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import archivo.c.s.Envia;
import java.util.List;
import java.awt.datatransfer.*;
import java.io.File;
import javax.swing.*;

public class ListaTransfer extends TransferHandler{
    public boolean canImport(TransferHandler.TransferSupport info){
        if(!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){ //Si no es del tipo File
            System.out.println("No es archivo");
            return false;
        }
        JList.DropLocation dl = (JList.DropLocation)info.getDropLocation(); //Si esta fuera del JList
        if(dl.getIndex()==-1){
            System.out.println("Estas fuera del JList");
            return false;
        }
        return true;
    }
    public boolean importData(TransferHandler.TransferSupport info){
    if(!info.isDrop()){
        System.out.println("No es Drop");
        return false;
    }
    if(!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
        System.out.println("No es un archivo");
        return false;
    }
    JList lista = (JList)info.getComponent();
    DefaultListModel listModel = new DefaultListModel();
    lista.setModel(listModel);
    List <File> droppedFiles =null;
    
    try{
        droppedFiles = (List<File>) info.getTransferable()
                    .getTransferData(DataFlavor.javaFileListFlavor);
        Envia en=new Envia();
        en.enviar_archivo(droppedFiles);
        // Manda las carpetas recursivamente
                //if (file.isDirectory()) en.enviar_archivo(file);
               // else en.enviar_archivo(file); // Manda un solo archivo
       
        for (File file : droppedFiles) {
                listModel.addElement(file.getName());
        }     
    }catch(Exception e){
        return false;
    }
    

    return true;
    }    
}
