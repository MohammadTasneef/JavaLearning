package com.array.learning;

import java.util.Scanner;

public class MaximumNumber {
	
	public static void main (String args[]) 
	{
		
		int n;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Number of Elements");
		n=sc.nextInt();
		int array[]=new int[10];
		System.out.println("Enter the Elements");
		for(int i=0;i<n;i++)
		{
			array[i]=sc.nextInt();
			
		}
		System.out.println("Number of elements are");

	for(int i=0;i<n;i++)
	{
		System.out.println(array[i]);
	}
	int max=array[0];
	for(int i=0;i<n;i++)
	{
		if(max<array[i])
		{
			max=array[i];
		}
		}
	System.out.println("maximum number is = "+max);
}
}