/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author saidn
 */
public class Cliente {
    private static final String HOST = "127.0.0.1";
    private static final int PUERTO = 5000;
    public static final byte NIVEL_PRINCIPANTE = 9;
    public static final byte NIVEL_INTERMEDIO = 16;
    public static final byte NIVEL_EXPERTO = 30;

    public static final byte MINAS_NIVEL_PRINCIPANTE = 10;
    public static final byte MINAS_NIVEL_INTERMEDIO = 40;
    public static final byte MINAS_NIVEL_EXPERTO = 99;

    public static List<Casillas> tablero = new ArrayList<>();
    public static byte filas, columnas, intentos, minasDificultad, numeroMinas, f, c;
    public static boolean Ganador = true;
    
    private static int hora;
    private static int min;
    private static int seg;
    private static int tiempoI;
    private static int tiempoF;

    public static void main(String[] args) {
        try {
            intentos = 0;
            Scanner sc = new Scanner(System.in);
            Socket cl = new Socket(HOST, PUERTO);
            DataOutputStream dos = new DataOutputStream(cl.getOutputStream());

            System.out.println("Conexion establecida... Inicio del juego");
            System.out.println("Elije una dificultad(1.-Sencillo//2.-Intermedio//3.-Dificil)");
            byte dificultad = sc.nextByte();
            dos.writeByte(dificultad);

            if (dificultad == 2) {
                columnas = filas = NIVEL_INTERMEDIO;
                minasDificultad = MINAS_NIVEL_INTERMEDIO;
            } else if (dificultad == 3) {
                filas = NIVEL_INTERMEDIO;
                columnas = NIVEL_EXPERTO;
                minasDificultad = MINAS_NIVEL_EXPERTO;
            } else {
                columnas = filas = NIVEL_PRINCIPANTE;
                minasDificultad = MINAS_NIVEL_PRINCIPANTE;
            }
            Casillas matrizTablero[][] = new Casillas[filas][columnas];
            ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
            tablero.clear();
            tablero = (List<Casillas>) ois.readObject();
            dos.writeUTF("OK");
            int z = 0;
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++, z++) {
                    matrizTablero[i][j] = tablero.get(z);
                }
            }
            //Tomar tiempo inicial
            Calendar calendario=Calendar.getInstance(); 
            min=calendario.get(Calendar.MINUTE);
            seg=calendario.get(Calendar.SECOND);
            hora = calendario.get(Calendar.HOUR_OF_DAY);
        
            tiempoI=(hora*60)+min+(seg/60);
            System.out.println("tiempo: "+tiempoI);
            imprimir(matrizTablero);
            while (Ganador) {
                
                System.out.println("Elije una opcion: 1.-Casilla//2.-Bandera");
                if (sc.nextByte() == 1) {
                    System.out.println("Posicion del tiro");
                    System.out.println("Fila: ");
                    f = sc.nextByte();
                    System.out.println("Columna: ");
                    c = sc.nextByte();
                    Ganador = seleccionaMina(matrizTablero, f, c, false);
                } else {
                    System.out.println("Posicion de la bandera: ");
                    System.out.println("Fila: ");
                    f = sc.nextByte();
                    System.out.println("Columna: ");
                    c = sc.nextByte();
                    Ganador = seleccionaMina(matrizTablero, f, c, true);
                }
                imprimir(matrizTablero);
                if (Ganador == false) {
                    System.out.println("Perdiste");
                    imprimirTodo(matrizTablero);
                    dos.writeUTF("LOST");
                    break;
                }
                if (intentos == minasDificultad) {
                    System.out.println("Ganaste");
                    dos.writeUTF("WIN");
                    Calendar c = Calendar.getInstance();
                    min=c.get(Calendar.MINUTE);
                    seg=c.get(Calendar.SECOND);  
                    hora = c.get(Calendar.HOUR_OF_DAY);
            
                    tiempoF= (hora*60)+min+(seg/60);
                    float reg = Math.round(((tiempoF-tiempoI)*100)/100);
                    System.out.println("tiempo: "+tiempoF);
                    dos.flush();
                    dos.writeUTF(Float.toString(reg));
                    dos.flush();
                    break;
                }
                System.out.println("intentos : "+intentos);
            }
            dos.close();
            ois.close();
            cl.close();
        } catch (IOException | ClassNotFoundException e) {
            e.toString();
        }
    }

    public static void imprimirTodo(Casillas[][] matrizTablero) {
        for (int i=0;i<columnas-1;i++){
            System.out.print("C"+i+" ");
        }
        System.out.println("");
        for (int i = 0; i < filas - 1; i++) {
            for (int j = 0; j < columnas - 1; j++) {
                Casillas casilla = matrizTablero[i][j];
                if (casilla.getIsMina() == false) {
                    System.out.print(" " + casilla.getMinasAlrededor() + " ");
                } else {
                    System.out.print(" * ");
                }
            }
               System.out.println("...F"+i);
        }
        System.out.println("");
    }

    public static void imprimir(Casillas[][] matrizTablero) {
        int i = 0;
        int j = 0;
        for(int k=0;k<columnas-1;k++){
            System.out.print("C"+k+" ");
        }
        System.out.println("");
        for (i = 0; i < filas - 1; i++) {
            for (j = 0; j < columnas - 1; j++) {
                Casillas casilla = matrizTablero[i][j];
                if (casilla.getIsShown() == (byte) 0) {
                    System.out.print(" - ");
                } else if (casilla.getIsShown() == (byte) 1) {
                    System.out.print(" " + casilla.getMinasAlrededor() + " ");
                } else {
                    System.out.print("<1>");
                }
            }
            System.out.println("...F"+i);
        }
        System.out.println("");
    }

    public static boolean seleccionaMina(Casillas[][] matrizTablero, byte fila, byte columna, boolean isBandera) {
        try{
            Casillas seleccion = matrizTablero[fila][columna];
            if (!isBandera) {
                //es mina
                if (seleccion.getIsMina()) {
                    return false;
                }
                //destapar
                seleccion.setIsShown((byte) 1);
                //Si la mina es cero avisa a sus hermanos ceros
                if (seleccion.getMinasAlrededor() == 0) {
                    avisarCeros(seleccion, matrizTablero);
                }
            } else {
                //Poner bandera
                seleccion.setIsShown((byte) 2);
                if(seleccion.getIsMina()){
                    intentos++;
                }
            }
        return true;
        }catch(IndexOutOfBoundsException | InputMismatchException e){
            System.out.println("Coordenadas incorrectas");
            return true;
        }   
    }

    public static void avisarCeros(Casillas casillaActual, Casillas[][] tablero) {
        byte i_aux = casillaActual.getFila();
        byte j_aux = casillaActual.getColumna();
        //Se puso una mina, así que avisamos a sus vecinos
        if (casillaActual.getTipo() == 1) {
            if (casillaActual.getFila() == 0) {
                if (casillaActual.getColumna() == 0) { //superior izquierda
                    destaparCero(tablero[0][1], tablero);
                    destaparCero(tablero[1][0], tablero);
                    destaparCero(tablero[1][1], tablero);
                } else if (casillaActual.getColumna() == (columnas - 1)) { //superior derecha
                    destaparCero(tablero[0][(j_aux) - 2], tablero);
                    destaparCero(tablero[1][(j_aux) - 1], tablero);
                    destaparCero(tablero[1][(j_aux) - 2], tablero);
                }
            } else { //casillaActual.getFila()==(filas-1)
                if (casillaActual.getColumna() == 0) { //inferior izquierda
                    destaparCero(tablero[(i_aux) - 1][1], tablero);
                    destaparCero(tablero[(i_aux) - 2][0], tablero);
                    destaparCero(tablero[(i_aux) - 2][1], tablero);
                } else {//inferior derecha
                    destaparCero(tablero[(i_aux) - 1][(j_aux) - 2], tablero);
                    destaparCero(tablero[(i_aux) - 1][(j_aux) - 2], tablero);
                    destaparCero(tablero[(i_aux) - 2][(j_aux) - 1], tablero);
                }
            }
        } else if (casillaActual.getTipo() == 2) {
            if (i_aux == 0) {//superiores
                destaparCero(tablero[0][(j_aux) - 1], tablero);
                destaparCero(tablero[0][(j_aux) + 1], tablero);
                destaparCero(tablero[1][(j_aux) - 1], tablero);
                destaparCero(tablero[1][(j_aux) + 1], tablero);
                destaparCero(tablero[1][j_aux], tablero);
            } else if (i_aux == (filas - 1)) {//inferiores
                destaparCero(tablero[(filas - 1)][(j_aux) - 1], tablero);
                destaparCero(tablero[(filas - 1)][(j_aux) + 1], tablero);
                destaparCero(tablero[(filas - 2)][(j_aux) - 1], tablero);
                destaparCero(tablero[(filas - 2)][(j_aux) + 1], tablero);
                destaparCero(tablero[(filas - 2)][(j_aux)], tablero);
            } else if (j_aux == 0) {//izquierda
                destaparCero(tablero[(i_aux) - 1][0], tablero);
                destaparCero(tablero[(i_aux) + 1][0], tablero);
                destaparCero(tablero[(i_aux) - 1][1], tablero);
                destaparCero(tablero[(i_aux) + 1][1], tablero);
                destaparCero(tablero[i_aux][1], tablero);
            } else if (j_aux == (columnas - 1)) {//derecha
                destaparCero(tablero[(i_aux) - 1][j_aux], tablero);
                destaparCero(tablero[(i_aux) + 1][j_aux], tablero);
                destaparCero(tablero[(i_aux) - 1][(j_aux) - 1], tablero);
                destaparCero(tablero[(i_aux) + 1][(j_aux) - 1], tablero);
                destaparCero(tablero[i_aux][(j_aux) - 1], tablero);
            }
        } else {
            destaparCero(tablero[i_aux][(j_aux) - 1], tablero);
            destaparCero(tablero[i_aux][(j_aux) + 1], tablero);
            destaparCero(tablero[(i_aux) - 1][(j_aux) - 1], tablero);
            destaparCero(tablero[(i_aux) - 1][j_aux], tablero);
            destaparCero(tablero[(i_aux) - 1][(j_aux) + 1], tablero);
            destaparCero(tablero[(i_aux) + 1][(j_aux) - 1], tablero);
            destaparCero(tablero[(i_aux) + 1][j_aux], tablero);
            destaparCero(tablero[(i_aux) + 1][(j_aux) + 1], tablero);
        }
    }

    public static void destaparCero(Casillas casilla, Casillas conteo[][]) {
        if(casilla.getIsShown()!=0){
            return;
        }
        if (casilla.getMinasAlrededor() == 0) {
            casilla.setIsShown((byte) 1);
            avisarCeros(casilla, conteo);
        }
        else{
            if(!casilla.getIsMina()){
                casilla.setIsShown((byte)1);
                //avisarCeros(casilla,conteo);
            }
        }
    }
}