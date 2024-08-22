package com.array.learning;
import java.util.Scanner;

public class arraySum {

	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		
		int a []= {1,2,3,4,5};
		int c = 0;
		System.out.println("Enter the Value");
		int k=sc.nextInt();
		for(int i=0;i<a.length;i++)
		{
			c=c+a[i];
			
		}
		System.out.println(c);
		System.out.println(k);
	}

}
