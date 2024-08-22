package com.array.learning;

public class SecondLargestNum {

	public static void main(String[] args) {
		
		int arr[]= {8,5,9,2,6,1,34,51};
		int max1=arr[0];
		int max2=arr[0];
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]>max1)
			{
				max2=max1;
				max1=arr[i];
				}
			else if(arr[i]>max2)
			{
				max2=arr[i];
			}
		}
		System.out.println("Maximum1="+max1+" "+"Maximum2="+max2);
	}

}
