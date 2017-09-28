
package mergesort;

import java.util.Random;
import java.util.Scanner;

public class MergeSort {
    //sort initialize for create aux[]
    public void sort(Comparable a[],int div){
        Comparable [] aux =  new Comparable[a.length];
	sort(a,aux,0,a.length-1,div);
    }
    //sort
    public void sort(Comparable []a, Comparable[] aux, int low, int hi,int div){
    //break statement
    	if(hi <= low) return ;
    //Get middle element as in Binary Search
	int mid = (hi+low)/div;
    //First apply recursion by left of halves
	sort(a,aux,low,mid,div);
	sort(a,aux,mid+1,hi,div);
    //Finally merge all
	merge(a,aux,low,mid,hi);
    }
    //merge
    public void merge(Comparable []a, Comparable[] aux, int low, int mid, int hi){
    assert isSorted(a,low,mid);
    assert isSorted(a,mid+1,hi);
    //copy to aux
		for(int i = low; i<= hi; i++)
			aux[i] = a[i];
		//i,j pointers for halves
		int i = low;
		int j =  mid+1;
		for(int k=low; k<=hi;k++){
			//Validate one of the 2 halves is fully used
			if     (i > mid) 		     a[k] = aux[j++];
			else if(j > hi) 		     a[k] = aux[i++];
			else if(less(aux[i],aux[j])) a[k] = aux[i++];
			else                         a[k] = aux[j++];
		}
		assert isSorted(aux, low,hi);
	}
	private boolean isSorted(Comparable []a, int lo, int hi){
		for(int i = lo+1; i<=hi; i++ )
			if(less(a[i],a[i-1])) return false;
		return true;
	}
	private boolean less(Comparable a, Comparable b){
		return a.compareTo(b)<0;
	}

	public void exch(Comparable[]a, int i, int j){
		Comparable aux = a[i];
		a[i] = a[j];
		a[j] = aux;
	}
	
        
	public static void main(String [] args){
		Random rdn =  new Random();
		int n; //100 millions in 2 minutes, need more memory for java to continue 
		int div;
                System.out.println("Ingresa tamaño del arreglo");
                Scanner sc = new Scanner(System.in);
                n=sc.nextInt();
                System.out.println("Ingresa tamaño de division");
                Scanner sc1= new Scanner(System.in);
                div=sc1.nextInt();
                Integer [] array = new Integer[n];
		for(int i = 0; i<n;i++){
			array[i] = rdn.nextInt(100);
		}
		System.out.println("filled");
		
		MergeSort merge =  new MergeSort();
		merge.sort(array);
		
		
		System.out.println("Sorted");
	}
}