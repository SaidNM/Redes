/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bucketsort;
import java.io.IOException;
//import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class ServerThread extends Thread{
	Socket socket=null;
	int divCubeta;
	ArrayList<Integer>numeros=null;
	int rangos;
	//ObjectOutputStream oos=null;
	public ServerThread(Socket so,int divisor,ArrayList<Integer> num,int segme){
		socket=so;
		divCubeta=divisor;
		rangos=segme;
		numeros=num;
		//oos=o;
	}
	public void run(){
		ArrayList<Integer>ncubeta=new ArrayList<Integer>();
		int xnum;
		for(int i=0;i<1000;i++){
			xnum=numeros.get(i);
			if(xnum>=rangos*(divCubeta-1) && xnum<rangos*(divCubeta))
				ncubeta.add(xnum);
		}
		try {
			//ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
			PrintWriter sSalida = new PrintWriter(socket.getOutputStream(), true);
			//ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
			//oos.writeObject(ncubeta);
			ncubeta=ordena(ncubeta);
			sSalida.println(ncubeta);
			//oos.flush();
			
			
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public ArrayList<Integer> ordena(ArrayList<Integer> a){
		ArrayList<Integer>b=new ArrayList<Integer>();
		int min;
		if(a.size()!=0)
			min=a.get(0)+1;
			else
				min=999;
		for(int j=0;j<a.size();j++){
		for(int i=0;i<a.size();i++){
			if(a.get(i)<min)
				min=a.get(i);
				
		}
		b.add(min);
		a.remove(a.indexOf(min));
		if(a.size()!=0)
		min=a.get(0)+1;
		else
			min=999;
		}
		return b;
	}
	

}
