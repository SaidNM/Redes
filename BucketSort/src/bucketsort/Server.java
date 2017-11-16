/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bucketsort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
//import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;


public class Server{
	public static final int PUERTOFLUJO=8976;
	
	public static void main(String[] args) throws InterruptedException {
		ArrayList<Integer>numeros=new ArrayList<Integer>();
		Random ran=new Random();
		try{
		ServerSocket sss=new ServerSocket(PUERTOFLUJO);
		Socket so=sss.accept();
		System.out.println("Cliente conectado desde "+so.getPort());
		BufferedReader sEntrada = new BufferedReader(new InputStreamReader( so.getInputStream()));
		//System.out.println(sEntrada.readLine());
		//PrintWriter sSalida = new PrintWriter(so.getOutputStream(), true);
		int nCubetas=Integer.parseInt(sEntrada.readLine());
		for(int j=0;j<1000;j++)
			numeros.add(ran.nextInt(1000));
		System.out.println("Numeros desordenados");
		System.out.println(numeros);
		int segmento;
		int pentera;
		if(nCubetas<1000){
		segmento=1000/nCubetas;
		double restos=1000/nCubetas;
		pentera=(int)restos;
		pentera=1000-pentera*nCubetas;
		}
		else{
			segmento=1000;
			pentera=0;
		}
		//ObjectOutputStream oos=new ObjectOutputStream(so.getOutputStream());
		for(int i=0;i<nCubetas;i++){
			if(i==nCubetas-1){
			ServerThread a=new ServerThread(so,i+1,numeros,segmento+pentera);
			//a.setPriority(i+1);
			//new serverThread(so,i+1,numeros,segmento+pentera).start();
			a.start();
			a.join();
			}
			else{
				ServerThread b=new ServerThread(so,i+1,numeros,segmento);
				b.start();
				b.join();
				
			}
		}
		sss.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

}