package com.stringLearning;

public class StringVowels {

	public static void main(String[] args) {
		String s="my name is tasneef";
		int l=s.length();
		String k="" ;
		int flag = 0;
		System.out.println(l);
		
		for(int i=0;i<=l-1;i++)
		{
			char ch=s.charAt(i);
			
			if(ch == 'a' || ch == 'e'||ch == 'i'||ch == 'o'||ch == 'u')
			{
				
				flag=flag+1;
			}
			else
			{
			 k=k+ch;
			}
			}
		System.out.println(k);
		System.out.println(flag);
}
}