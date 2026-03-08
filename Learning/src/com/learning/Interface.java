package com.learning;

interface demo{
	void disp(String a);
	void greet(int b);
	abstract void a();
}

 class demoShow implements demo{

	public void disp(String a) {
    System.out.println("Value is displayed="+a);
	}
	
	public void greet(int b) {
		System.out.println("Hello="+b);
	}
	public void a() {
		System.out.println("");
	}
	
	void example() {
		System.out.println("We can have non abstract method as well in this concrete class");
	}
	
}

public class Interface
{
	public static void main(String args[])
	{
		demoShow obj= new demoShow();
		obj.disp("tasneef");
		obj.greet(7);
		obj.example();
		
	}
}
