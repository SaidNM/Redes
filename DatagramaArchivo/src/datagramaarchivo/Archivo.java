
package datagramaarchivo;

import java.io.Serializable;

/**
 *
 * @author saidn
 */
public class Archivo implements Serializable{
    private int i;
    private String nombre;
    private String ruta;
    private long tam;
    private byte[] datos;
    private int bytesEnviados;

    public Archivo(String nombre, String ruta, long tam) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.tam = tam;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

   
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public long getTam() {
        return tam;
    }

    public void setTam(long tam) {
        this.tam = tam;
    }

    public byte[] getDatos() {
        return datos;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }

    public int getBytesEnviados() {
        return bytesEnviados;
    }

    public void setBytesEnviados(int bytesEnviados) {
        this.bytesEnviados = bytesEnviados;
    }
    
    
}
