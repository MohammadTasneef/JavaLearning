package com.stringLearning;

public class StringPalindrome {

	public static void main(String[] args) {
		String s="reviver";
		String temp=s;
		int l=s.length();
		System.out.println(l);
		String sum="";
		for(int i=l-1;i>=0;i--)
		{
			char ch=s.charAt(i);
			sum=sum+ch;
	}
      System.out.println(sum);   
      if(temp.equals(sum))
      {
    	  System.out.println("String is Palindrome");
      }
      else
      {
    	  System.out.println("String is not palindrome");
      }
	}
}