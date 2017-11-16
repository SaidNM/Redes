/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bucketsort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente extends Thread{

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		Socket so=null;
		System.out.println("Ingrese el puerto");
		int puerto=Integer.parseInt(br.readLine());
		System.out.println("Escribe la direccion del servidor:");
		String host=br.readLine();
		so=new Socket(host,puerto);
		BufferedReader sEntrada = new BufferedReader(new InputStreamReader( so.getInputStream()));
		//System.out.println(sEntrada.readLine());
		PrintWriter sSalida = new PrintWriter(so.getOutputStream(), true);
		System.out.println("Cuantas cubetas quieres? ");
		int ncubetas=Integer.parseInt(br.readLine());
		sSalida.println(ncubetas);
		//ObjectInputStream ois=new ObjectInputStream(so.getInputStream());
		ArrayList<String>numerosordenados=new ArrayList<String>();
		for(int i=0;i<ncubetas;i++){
			ArrayList<String>cubeta=new ArrayList<String>();
			//ArrayList<Integer>cubeta=(ArrayList<Integer>)ois.readObject();
			cubeta.add(sEntrada.readLine());
			numerosordenados.add(cubeta.get(0));
			System.out.println("Cubeta "+(i+1)+"   :"+cubeta);
			//ois.close();
		}
		System.out.println("Arreglo Ordenado");
		System.out.println(numerosordenados);
		//ois.close();
		sSalida.close();
		sEntrada.close();
		so.close();

	}

}
