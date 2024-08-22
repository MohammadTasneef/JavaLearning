package com.stringLearning;

public class StringReverse {

	public static void main(String[] args) {
		String s="Name is Tasneef";
		int l=s.length();
		System.out.println(l);
		String sum="";
		for(int i=l-1;i>=0;i--)
		{
			char ch=s.charAt(i);
			sum=sum+ch;

	}
       System.out.println(sum);
}
}