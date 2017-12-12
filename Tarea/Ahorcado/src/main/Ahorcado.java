
package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame; 
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import ui.uiButton;

public class Ahorcado extends JFrame implements ActionListener{
    
    JPanel panel;
    JButton doll, level [];
    ImageIcon img [];
    JTextField tf;
    JLabel instruc, letter [];
    Container c;    
    String anteriorFrase = null;
    int state = 0; 
    
    public Ahorcado(String s){
         
        panel   = new JPanel(); 
        level   = new JButton[3];
                
        // Operaciones por default
        setVisible(true);
        setSize(new Dimension(800,400));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        
        
        c = this.getContentPane();
        c.setBackground(Color.WHITE);
        c.setLayout(new GridLayout(2,1));
        
        anteriorFrase = s;
        
         
        play(s);
//        
//        //Iniciliza las instrucciones
//        instruc = new JLabel("Elige la dificultad", SwingConstants.CENTER);
//        
//        //Inicializa los niveles de dificultad
//        level [0] = new uiButton("Facil");
//        level [0].addActionListener(this);
//        level [0].setActionCommand("dificultad_1");
//                
//        level [1] = new uiButton("Moderado");
//        level [1].addActionListener(this);
//        level [1].setActionCommand("dificultad_2");
//        
//        level [2] = new uiButton("Dificil");
//        level [2].addActionListener(this);
//        level [2].setActionCommand("dificultad_3");   
//        
//        menu();        
    }
     
    
    //Muestra los componentes del menu principal
//    public void menu(){
//        
//        //Agrega los componentes a pantalla
//        JPanel levelPanel;
//        levelPanel = new JPanel();
//        levelPanel.setLayout(new GridLayout(1, 3, 30, 30)); 
//        levelPanel.setBackground(Color.WHITE);
//        
//        levelPanel.add(level [0]);
//        levelPanel.add(level [1]);
//        levelPanel.add(level [2]);
//                        
//        c.add(instruc); 
//        c.add(levelPanel); 
//        
//        c.validate();
//    }

    //Acciones ejecutadas tras presionar botones
    
    @Override
    public void actionPerformed(ActionEvent e) {
          
        switch(e.getActionCommand()){
            
//            case "dificultad_1": 
//                diff = 1;
////                play(1);
//                break;
//            case "dificultad_2":
////                diff = 2;
////                play(2);
//                break;
//            case "dificultad_3":
////                diff = 3;
////                play(3);
//                break;
            case "lookUp":
                ArrayList <Integer> lista = verificar(tf.getText().charAt(0));
                tf.setText("");
                if(lista.size() == 0)
                    ahorcar();
                else
                    destapar(lista);
                
                ganar();
                break;
        }       
    }
    
    //Incia el juego
    private void play(String frase){
                   
        letter  = new JLabel[frase.length()];
        
        //Borra los componentes de la pantalla
        c.removeAll();
        
        //Inicializa el panel scrollable 
        panel   = new JPanel(); 
        panel.setLayout(new GridLayout(1, frase.length()));
        panel.setBackground(Color.WHITE);
        
        //Agrega cada letra de la palabra a cada etiqueta.
        int aux = 0;       
        for (char ch: frase.toCharArray()) 
        {                
            //Ocultar la letra en caso de serlo
            if (Character.isLetter(ch)) 
                letter[aux] = new JLabel("_");                 
            else 
                letter[aux] = new JLabel(String.valueOf(ch));
            
            letter[aux].setMinimumSize(new Dimension(100,100));
            panel.add(letter[aux]);
            aux++; 
        }
         
       
        //Inicializa los muñequitos
        img = new ImageIcon[7];
        String src = "src/img/";
        
         
        img[0] = new ImageIcon(src + "1.png");
        img[1] = new ImageIcon(src + "2.png");
        img[2] = new ImageIcon(src + "3.png");
        img[3] = new ImageIcon(src + "4.png");
        img[4] = new ImageIcon(src + "5.png");
        img[5] = new ImageIcon(src + "6.png");
        img[6] = new ImageIcon(src + "7.png");
        
        
        doll = new uiButton(img[0]);        
        
       //Inicializa la caja de texto 
        tf = new JTextField("", SwingConstants.CENTER);
        tf.setMargin(new Insets(10, 10, 10 , 10));
        
        //Permite escribir un solo caracter alfabetico
        tf.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) { 
                if (tf.getText().length() >= 1 || !Character.isLetter(e.getKeyChar())){
                    e.consume(); 
                }                 
            }  
        });
        tf.addActionListener(this);
        tf.setActionCommand("lookUp");
        
        c.setLayout(new BorderLayout());
        c.add(doll, BorderLayout.PAGE_START);
        c.add(panel, BorderLayout.CENTER);
        c.add(tf, BorderLayout.SOUTH);        
        c.validate();
    }
    
     
    //Revisa si la letra se encuentra en la frase
    private ArrayList<Integer> verificar(char s){
        
        ArrayList<Integer> lista = new ArrayList<Integer>();
                
        char   sc = fixString(String.valueOf(s)).charAt(0);
        String fc = fixString(anteriorFrase);   
        
        for(int i = 0; i<fc.length(); i++){            
            
            if(fc.charAt(i) == sc)
                lista.add(i);  
        }        
        return lista;        
    }
    
    
    //Quita acento y pasa a minusculas
    private String fixString(String s){
        
        String output;
        s = s.toLowerCase();        
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii    = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";        
        output = s;       
        
        
        for (int i=0; i < output.length(); i++) {
            
            // Reemplazamos los caracteres especiales.                  
            if(original.contains(""+output.charAt(i))){
                
                int pos = original.indexOf(output.charAt(i));
                output = output.replace(original.charAt(pos), ascii.charAt(pos));
            }

        }
        return output;
    }
    
    
    //Colgar al muñeco
    private void ahorcar(){
         
        state++;       
        if(state >= img.length - 1){
            doll.setIcon(img[state]);
            destaparTodas();
            JOptionPane.showMessageDialog(this, "Has perdido");            
            this.dispose();
        }       
        
        doll.setIcon(img[state]);
    }
    
    private void destaparTodas(){         
        for(int i = 0; i<anteriorFrase.length(); i++){
            letter[i].setText("" + anteriorFrase.charAt(i));
        }
    }
    
    private void destapar(ArrayList<Integer> list){         
        for(int num: list){
            letter[num].setText("" + anteriorFrase.charAt(num));
        }
    }
    
    private void ganar(){
        for(JLabel l: letter){
            if(l.getText().equals("_"))
                return;
        }
        
        JOptionPane.showMessageDialog(this, "Ganaste");
    }
}

