package com.array.learning;

public class ArrayRotation {

	public static void main(String[] args) {
		
		int arr[]= {8,5,9,2,6,1,34,51};
		for(int i=0;i<arr.length;i++)
		{
			System.out.print(arr[i]+" ");
			System.out.println("");
		}
        int temp=arr[0];
		for(int i=1;i<arr.length;i++)
		{
			arr[i-1]=arr[i];
		}
		arr[arr.length-1]=temp;
		for(int i=0;i<arr.length;i++)
		{
			System.out.print(arr[i]+" ");
		}
		
	}

}
