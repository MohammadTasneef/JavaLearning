package com.array.learning;

public class array {

	public static void main(String[] args) {
		int a[]= {1,2,3,4,5};
		int b[]=new int[4];          //It will have default value i.e 0 at all indexes
		//b[0]=15;
		int c=a.length;
		int d=b.length;
		System.out.println(d);
		System.out.println(c);
	    System.out.println(a[0]);
	    		
		for(int i=0;i<c;i++)
		{
			System.out.println("Value of each element is = "+a[i]);
		}
		
		/*for(int x:a)    //for each loop only drawback is that it can go only forward
		{
			System.out.println(x);
		}*/

	}

}
