package com.stringLearning;

public class DuplicateCharacter {

	public static void main(String args[]) {
		String s = "my name is tasneef";
		int flag=0;
		char s1[] = s.toCharArray();
		int l = s.length();
		for (int i = 0; i<l; i++) {	
	    for(int j=i+1;j<l;j++)
	    {
          if(s1[i]==s1[j] && s1[i]!=' ')
          {
        	System.out.println(s1[i]+" ");
        	  flag =flag+1;
          }
		}
  }
		System.out.println(flag);
	}
}