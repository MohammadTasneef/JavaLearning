package com.learning;

public class nonStaticMethod {
	
	int x = 5;
	static int y = 4;
	
	public int add(int a, int b) //user defined instance method or non static method with return type
	{  
		//System.out.println(x);
		int sum=a + b;
		return sum;
		
	}
	
	public int multiply(int a,int b)
	{
		int multiply=a*b;
		return multiply;
	}
	
	public void show(int value) //user defined instance method or non static method without a return type
	{
		System.out.println("The Value is = "+ value);
	
	}
	
	public static int add1(int a, int b) //user defined static method with return type
	{
		System.out.println(y);
		int sum=a + b;
		return sum;
	}
	public static void main(String args[]) 
	{
		nonStaticMethod obj = new nonStaticMethod(); // clasname objectname = new class name();
		int add=obj.add(10, 20);
		System.out.println(add);
		int multiply=obj.multiply(30, 40);
		obj.show(add);
		obj.show(multiply);
		int c=add1(2,3);
		System.out.println(c);
		
}}
