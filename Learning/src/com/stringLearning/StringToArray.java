package com.stringLearning;

public class StringToArray {

	public static void main(String[] args) {
		String s="My Name Is Tasneef";
		
		char s1[]= s.toCharArray();
		int l=s1.length;//        its not a method its a property of an array
		for(int i=0;i<l;i++)
		{
			//s1[i]=s.charAt(i);
		
				System.out.println(s1[i]);
				
		
	}

	}
}
