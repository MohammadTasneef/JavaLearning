package com.stringLearning;

public class ReverseCharacter {

	public static void main(String[] args) {
		String s="Name is Tasneef";
		int l=s.length();
		System.out.println(l);
		String sum="";
		for(int i=l-1;i>=0;i--)
		{
			char ch=s.charAt(i);
			sum=ch+sum;

	}
       System.out.println(sum);
	}

}
