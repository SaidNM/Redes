
package ui;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javafx.scene.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class uiButton extends JButton implements MouseListener{
    
    public uiButton(){ 
        format();
    }
    
    public uiButton(String s){
        format();
        setText(s);
    }
    
    public uiButton(ImageIcon ii){
        format();
        setIcon(ii);
    }
    
    private void format(){
        setBackground(Color.WHITE);
        setBorder(null);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) { 
        setBackground(new Color(0xF0F0F0));  
    }

    @Override
    public void mousePressed(MouseEvent e) { 
        setBackground(new Color(0xF0F0F0));  
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setBackground(new Color(0xF0F0F0));  
    }

    @Override
    public void mouseEntered(MouseEvent e) { 
        setBackground(new Color(0xFAFAFA));  
    }

    @Override
    public void mouseExited(MouseEvent e) { 
        setBackground(Color.WHITE);
    }
}
