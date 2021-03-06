/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chat;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import fonts.*;

public class VentanaCliente extends javax.swing.JFrame implements Accent {

    Cliente Acciones;
    String NombreUsuario;
    String Privado;
    
    
    
    public void AlCerrar(){
            try{
                this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                addWindowListener(new WindowAdapter(){
                        public void windowClosing(WindowEvent e){
                            try {
                                ConfirmarSalida();
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null,"Error al confirmar la salida");
                            }
                        }
                });
                this.setVisible(true);
            }
            catch(Exception e){
                    e.printStackTrace();
            }
    }

    public void ConfirmarSalida() throws IOException{
            int valor=JOptionPane.showConfirmDialog(this,"¿Desea salir del grupo?","Confirmar salida",JOptionPane.YES_NO_OPTION);
            if(valor==JOptionPane.YES_OPTION){
                    Acciones.EscribeMensaje("["+NombreUsuario+"] Se ha desconectado");
                    Acciones.Desconectar();
                    System.exit(0);
            }
    }
    
    /**
     * Creates new form VentanaCliente
     */
    public VentanaCliente() throws IOException {
        Acciones=new Cliente();
        initComponents();
        AlCerrar();
        IniciarCliente();
        getContentPane().setBackground(Color.WHITE);
        titleLabel.setFont(fontLibrary.setRobotoBlack(14));
        setAlwaysOnTop(false);
        Mensaje.setFont(fontLibrary.setRobotoRegular(13));
//        Emoticones.setFont(fontLibrary.setRobotoRegular(13));
        ListaUsuarios.setBorder(null);
        ListaUsuarios.setBackground(null); 
    }
    
    public void IniciarCliente(){
        try {
            NombreUsuario= JOptionPane.showInputDialog("Nombre de usuario: ");
            titleLabel.setText(NombreUsuario);
            Acciones.Conectate(ListaUsuarios,NombreUsuario,Emoticones);
            Acciones.EscribeMensaje("<b>["+NombreUsuario+"]</b> Inicio sesión");
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Error al conectarme");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Mensaje = new javax.swing.JTextField();
        EnviarMensaje = new javax.swing.JButton();
        ScrollTabla = new javax.swing.JScrollPane();
        ListaUsuarios = new javax.swing.JTable();
        ScrollArea = new javax.swing.JScrollPane();
        Emoticones = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setName("Ventana"); // NOI18N
        setResizable(false);

        EnviarMensaje.setText("Enviar");
        EnviarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnviarMensajeActionPerformed(evt);
            }
        });

        ListaUsuarios.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        ListaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuarios"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ListaUsuarios.setAutoscrolls(false);
        ListaUsuarios.setFocusable(false);
        ListaUsuarios.setGridColor(new java.awt.Color(255, 255, 255));
        ListaUsuarios.setShowHorizontalLines(false);
        ListaUsuarios.setShowVerticalLines(false);
        ScrollTabla.setViewportView(ListaUsuarios);
        if (ListaUsuarios.getColumnModel().getColumnCount() > 0) {
            ListaUsuarios.getColumnModel().getColumn(0).setResizable(false);
        }
        ListaUsuarios.setFont(fontLibrary.setRobotoRegular(13));

        Emoticones.setEditable(false);
        Emoticones.setBorder(null);
        Emoticones.setContentType("text/html"); // NOI18N
        Emoticones.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Emoticones.setText("<html>\r   <head>\r \r   </head>\r   <body>\r     <p style=\"margin-top: 10px\" font-face=\"Roboto\">\r       \r     </p>\r   </body>\r </html>\r ");
        Emoticones.setAutoscrolls(false);
        ScrollArea.setViewportView(Emoticones);
        Emoticones.setFont(fontLibrary.setRobotoRegular(14));
        ScrollArea.setBorder(null);

        jPanel1.setBackground(accent);

        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("CHAT MULTICAST");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(titleLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(ScrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EnviarMensaje))
                    .addComponent(ScrollArea))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ScrollArea, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Mensaje)
                            .addComponent(EnviarMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)))
                    .addComponent(ScrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EnviarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarMensajeActionPerformed
        try {
            // TODO add your handling code here:
            
                        
            if(ListaUsuarios.getSelectedRow()==-1){
                Acciones.EscribeMensaje("<b>"+NombreUsuario+"</b>: "+Mensaje.getText());
                Mensaje.setText("");
            }
            else{
                DefaultTableModel DTMPrivado = (DefaultTableModel) ListaUsuarios.getModel();
                TableModel TMPrivado = ListaUsuarios.getModel(); 
                Privado = TMPrivado.getValueAt(ListaUsuarios.getSelectedRow(),0).toString();
                Privado = NombreUsuario+"&"+Privado+"$"+Mensaje.getText();
                Acciones.EscribeMensaje(Privado);
                ListaUsuarios.clearSelection();
                Mensaje.setText("");
                
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"Error al mandar mensaje");
        }
    }//GEN-LAST:event_EnviarMensajeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        
         try {
            // Set System L&F
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException e) {
           // handle exception
        }
        catch (ClassNotFoundException e) {
           // handle exception
        }
        catch (InstantiationException e) {
           // handle exception
        }
        catch (IllegalAccessException e) {
           // handle exception
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VentanaCliente().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane Emoticones;
    private javax.swing.JButton EnviarMensaje;
    private javax.swing.JTable ListaUsuarios;
    private javax.swing.JTextField Mensaje;
    private javax.swing.JScrollPane ScrollArea;
    private javax.swing.JScrollPane ScrollTabla;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
