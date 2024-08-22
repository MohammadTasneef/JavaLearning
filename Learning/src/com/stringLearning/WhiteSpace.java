package com.stringLearning;

public class WhiteSpace {

	public static void main(String[] args) {
		String s=" my name is tasneef ";
		//String s1=" my name is maire  ";
		String k="" ;
		//int c=s.compareTo(s1);
		//System.out.println(c);
		int flag = 0;
		int l=s.length();
		System.out.println(l);
		
		for(int i=0;i<=l-1;i++)
		{
			char ch=s.charAt(i);
			
			if(ch == ' ')
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