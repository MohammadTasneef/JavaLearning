package com.array.learning;

import java.util.Scanner;

public class ReverseCopying {

	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Number");
		int n=sc.nextInt();
		int arr[]=new int[10];
		System.out.println("Enter the elements");
		
		for(int i=0;i<n;i++)
		{
			arr[i]=sc.nextInt();
		}

		for(int i=0;i<n;i++)
		{
			System.out.println("Value of Each Element = "+arr[i]);
		}
		
		//int a[]= {1,2,3,4};
		int b[]= new int[4];
		for(int i=n-1,j=0;i>=0;i--,j++)
		{
			b[j]=arr[i];
		}
		for(int i=0;i<b.length;i++)
		{
			
		
		System.out.println(b[i]);
	}
	}

}
