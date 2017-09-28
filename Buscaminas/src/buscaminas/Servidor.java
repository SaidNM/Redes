/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author saidn
 */


public class Servidor {
    public static final int PUERTO=5000;
    public static final byte NIVEL_PRINCIPANTE = 9;
    public static final byte NIVEL_INTERMEDIO = 16;
    public static final byte NIVEL_EXPERTO = 30;
    
    public static final byte MINAS_NIVEL_PRINCIPANTE = 10;
    public static final byte MINAS_NIVEL_INTERMEDIO = 40;
    public static final byte MINAS_NIVEL_EXPERTO = 99;
    
    /*public static final byte tipoEsquina = 1;
    public static final byte tipoBorde = 2;
    public static final byte tipoInterna = 3;*/
    public static final List<Casillas> tablero = new ArrayList<>();
    public static byte columnas = 0;
    public static byte filas = 0;
    public static byte numeroMinas = 0;
    
    public static void main(String[] args) {
        try{
            byte dificultad;
            ServerSocket s;
            s = new ServerSocket(PUERTO);
            s.setReuseAddress(true);
            System.out.println("Servidor iniciado...............");
            for(;;){
                System.out.println("Esperando jugador...");
                Socket cl = s.accept();
                //Configuracion juego
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                DataOutputStream dos = new DataOutputStream(new FileOutputStream("Registro.txt"));
                dificultad = dis.readByte();
                if(dificultad==2){
                        columnas = filas = NIVEL_INTERMEDIO;
                        numeroMinas = MINAS_NIVEL_INTERMEDIO;
                }else if(dificultad==3){
                        filas = NIVEL_INTERMEDIO;
                        columnas = NIVEL_EXPERTO;
                        numeroMinas = MINAS_NIVEL_EXPERTO;
                }else{
                        columnas = filas = NIVEL_PRINCIPANTE;
                        numeroMinas = MINAS_NIVEL_PRINCIPANTE;
                }
                //Creación de un tablero para el usuario
                tablero.clear();
                crearTablero();
                //Envío de una tablero
                ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                oos.writeObject(tablero);
                oos.flush();
                //Inicio temporizador
                String tempo = dis.readUTF();

                if(tempo.equals("OK")){
                    Date date = new Date();
                    DateFormat inicio = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    System.out.println(inicio.format(date));
                    
                    String marcador = dis.readUTF();
                    if(marcador.equals("WIN")){
                        String reg = dis.readUTF();
                        dos.writeUTF(reg);
                        System.out.println("Ganó");
                    }else{
                        System.out.println("Perdió");
                    }
                    date = new Date();
                    DateFormat fin = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    System.out.println(fin.format(date)); 
                }
                dis.close();
                oos.close();
                cl.close();
                System.out.println("\n");
            }
        }catch(Exception e){
            e.toString();
        }
    }
    
    private static void crearTablero(){ 
        Casillas[][]conteo = new Casillas[filas][columnas];
        for(byte i = 0; i < filas;i++){
            for(byte j = 0; j < columnas; j++){
                if(i == 0 || i==(filas-1)){//Tipo esquina
                    if(j == 0 || j==(columnas-1)){   //Tipo esquina
                        Casillas aux = new Casillas(i,j,(byte)1);
                        tablero.add(aux);
                        conteo[i][j] = aux;
                    }else{ //Tipo borde
                        Casillas aux = new Casillas(i,j,(byte)2);
                        tablero.add(aux);
                        conteo[i][j] = aux;
                    }
                }else{
                    if(j==0||j==(columnas-1)){ //Tipo Borde
                        Casillas aux = new Casillas(i,j,(byte)2);
                        tablero.add(aux);
                        conteo[i][j] = aux;
                    }else{                  // Tipo interna
                        Casillas aux = new Casillas(i,j,(byte)3);
                        tablero.add(aux);
                        conteo[i][j] = aux;
                    }    
                }
            } //for
        }//for
        //LLenado de minas
        byte minasPuestas = 0;
        double ponerMina;
        for(int z = 0; z<tablero.size(); z=z+2 ){
            Casillas casillaActual = tablero.get(z);
            if(minasPuestas < numeroMinas){ //aun faltan minas
                if(z == tablero.size()-1){ //faltan pero recorri el tableo
                   z = 0;
                }
                if(!casillaActual.getIsMina()){ 
                //Estoy en una casilla sin mina
                    ponerMina = Math.random();
                    if(ponerMina<0.3){ //Random
                        casillaActual.setIsMina(true);
                        minasPuestas++;
                        byte i_aux = casillaActual.getFila();
                        byte j_aux = casillaActual.getColumna();
                        //Se puso una mina, así que avisamos a sus vecinos
                        if(casillaActual.getTipo() == 1){
                            if(casillaActual.getFila()==0){ 
                               if(casillaActual.getColumna()==0){ //superior izquierda
                                   conteo[0][1].hasMinaVecina();
                                   conteo[1][0].hasMinaVecina();
                                   conteo[1][1].hasMinaVecina();
                               }else if(casillaActual.getColumna()==(columnas-1)){ //superior derecha
                                   conteo[0][(j_aux)-2].hasMinaVecina();
                                   conteo[1][(j_aux)-1].hasMinaVecina();
                                   conteo[1][(j_aux)-2].hasMinaVecina();
                               }
                           }else{
                               if(casillaActual.getColumna()==0){ //inferior izquierda
                                   conteo[(i_aux)-1][1].hasMinaVecina();
                                   conteo[(i_aux)-2][0].hasMinaVecina();
                                   conteo[(i_aux)-2][1].hasMinaVecina();
                               }else{//inferior derecha
                                   conteo[(i_aux)-1][(j_aux)-2].hasMinaVecina();
                                   conteo[(i_aux)-1][(j_aux)-2].hasMinaVecina();
                                   conteo[(i_aux)-2][(j_aux)-1].hasMinaVecina();
                               }
                           }
                        }else if(casillaActual.getTipo() == 2){
                            if(i_aux==0){//superiores
                                conteo[0][(j_aux)-1].hasMinaVecina();
                                conteo[0][(j_aux)+1].hasMinaVecina();
                                conteo[1][(j_aux)-1].hasMinaVecina();
                                conteo[1][(j_aux)+1].hasMinaVecina();
                                conteo[1][j_aux].hasMinaVecina();
                            }else if(i_aux==(filas-1)){//inferiores
                                conteo[(filas-1)][(j_aux)-1].hasMinaVecina();
                                conteo[(filas-1)][(j_aux)+1].hasMinaVecina();
                                conteo[(filas-2)][(j_aux)-1].hasMinaVecina();
                                conteo[(filas-2)][(j_aux)+1].hasMinaVecina();
                                conteo[(filas-2)][(j_aux)].hasMinaVecina();
                            }else if(j_aux==0){//izquierda
                                conteo[(i_aux)-1][0].hasMinaVecina();
                                conteo[(i_aux)+1][0].hasMinaVecina();
                                conteo[(i_aux)-1][1].hasMinaVecina();
                                conteo[(i_aux)+1][1].hasMinaVecina();
                                conteo[i_aux][1].hasMinaVecina();
                            }else if(j_aux==(columnas-1)){//derecha
                                conteo[(i_aux)-1][j_aux].hasMinaVecina();
                                conteo[(i_aux)+1][j_aux].hasMinaVecina();
                                conteo[(i_aux)-1][(j_aux)-1].hasMinaVecina();
                                conteo[(i_aux)+1][(j_aux)-1].hasMinaVecina();
                                conteo[i_aux][(j_aux)-1].hasMinaVecina();
                            }
                        }else{
                            conteo[i_aux][(j_aux)-1].hasMinaVecina();
                            conteo[i_aux][(j_aux)+1].hasMinaVecina();
                            conteo[(i_aux)-1][(j_aux)-1].hasMinaVecina();
                            conteo[(i_aux)-1][j_aux].hasMinaVecina();
                            conteo[(i_aux)-1][(j_aux)+1].hasMinaVecina();
                            conteo[(i_aux)+1][(j_aux)-1].hasMinaVecina();
                            conteo[(i_aux)+1][j_aux].hasMinaVecina();
                            conteo[(i_aux)+1][(j_aux)+1].hasMinaVecina();
                        }
                   }//mina puesta
               }
            }
        }//for minas
        //mostrando tablero
        for(int i=0;i<columnas-1;i++){
            System.out.print("C"+i+" ");
        }
        System.out.println("");
        for(int i=0; i<filas-1;i++){
            for(int j = 0; j < columnas-1; j++){
                Casillas casilla = conteo[i][j];
                if(casilla.getIsMina()==false){
                        System.out.print(" "+casilla.getMinasAlrededor()+" ");
                }else{
                    System.out.print(" * ");
                }
            }
            System.out.println(".."+i);
        }
        System.out.println("");
    }
}