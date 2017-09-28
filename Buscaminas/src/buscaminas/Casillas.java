/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.io.Serializable;

/**
 *
 * @author saidn
 */
public class Casillas implements Serializable{
    private byte fila;
    private byte columna;
    private byte minasAlrededor;
    private byte tipo;
    private boolean isMina;
    private byte isShown;
    /*
    Tipo 1 - Mina en una esquina => 3 casillas colindantes
    Tipo 2 - Mina en un borde  => 5 casillas colindantes
    Tipo 3 - Mina interna => 8 casillas colindantes
    */
    public Casillas(byte fila, byte columna, byte tipo){
        this.fila = fila;
        this.columna = columna;
        this.minasAlrededor = 0;
        this.tipo = tipo;
        this.isMina = false;
        this.isShown = 0;
    }
    public void hasMinaVecina(){
        minasAlrededor++;
    }
    public byte getFila() {
        return fila;
    }

    public void setFila(byte fila) {
        this.fila = fila;
    }

    public byte getColumna() {
        return columna;
    }

    public void setColumna(byte columna) {
        this.columna = columna;
    }

    public byte getMinasAlrededor() {
        return minasAlrededor;
    }

    public void setMinasAlrededor(byte minasAlrededor) {
        this.minasAlrededor = minasAlrededor;
    }

    public byte getTipo() {
        return tipo;
    }

    public void setTipo(byte tipo) {
        this.tipo = tipo;
    }

    public boolean getIsMina() {
        return isMina;
    }

    public void setIsMina(boolean isMina) {
        this.isMina = isMina;
    }

    public byte getIsShown() {
        return isShown;
    }

    public void setIsShown(byte isShown) {
        this.isShown = isShown;
    }

    
    
}