package com.stringLearning;

public class StringPool {

	public static void main(String[] args) {

		char ch[] = {65,66,67,68,69};
		String str = new String(ch);// this string constructor creates an the string and pool as well it might be created in a heap.
		System.out.println(str);
		
		String str1 = new String(ch,1,2);//1 and 2 represents that 1  means the first index of array and 2 means it will take 2 elements starting from index 1
		System.out.println(str1);
		
		//String s =" My Name Is Tasneef ";
		//s=s.replaceAll("\\s", "");
		//System.out.println(s);
		
	}

}
