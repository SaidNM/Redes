
package memorama;

import java.io.File;
import javax.swing.ImageIcon;


public class Carta implements Comparable{
    private ImageIcon imagen;
    private boolean bocaArriba;
    private int id;
    private int random;
    
    public Carta(ImageIcon imagen, int id, int random) {
        this.imagen = imagen;
        this.id = id;
        this.random = random;
        this.bocaArriba=false;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }



    public boolean isBocaArriba() {
        return bocaArriba;
    }

    public void setBocaArriba(boolean bocaArriba) {
        this.bocaArriba = bocaArriba;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }
    
    
    @Override
    public int compareTo(Object o) {
        return (((Carta)o).getRandom()-this.random );
    }
}
