package com.learning;

public class staticMethod {
	
	int x = 5;
	static int y = 4;
	
	public static int add(int a, int b) //user defined static method with return type
	{
		//System.out.println(y);
		int sum=a + b;
		return sum;
		
	}
	
	public static int multiply(int a,int b)
	{
		int multiply=a*b;
		return multiply;
	}
	
	public static void show(int value) //user static method without a return type
	{
		System.out.println("The Value is "+ value);
	
	}

	public static void main(String[] args) {
		
		int sum = add(10,20);
		System.out.println(sum);
		int multiply=multiply(20,10);
		show(sum);
		show(multiply);
		
	}

}
