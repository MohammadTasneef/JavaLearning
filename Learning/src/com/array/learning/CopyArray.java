package com.array.learning;

public class CopyArray {

	public static void main(String[] args) {
		int a[]= {1,2,3,4,5};
		int b[]=new int[5];
		
		for(int i=0;i<a.length;i++)
		{
			System.out.print(b[i]=a[i]);
			
		}
		System.out.println("");
		for(int i=0;i<b.length;i++)
		{
			System.out.print(b[i]);
		}
	}

}
