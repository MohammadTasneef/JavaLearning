package com.learning;               //Polymorphism can be achieved by two ways i.e Overloading and overriding.

public class MethodOverLoading {   //Concept of polymorphism (A class having multiple methods with same name but different parameter is called function overloading)
	
	public int sum(int a,int b) {
		int add=a+b;
		return add;
	}
    
	public int sum(int a,int b,int c)
    {
      int addition=a+b+c;
      return addition;
      }
    
   public double sum(double a,double b)
   {
	   double addition=a+b;
	   return addition;
   }
	public static void main(String[] args) {
		
		MethodOverLoading obj = new MethodOverLoading();
		System.out.println("Sum is = "+obj.sum(4, 6));
		System.out.println("Sum is = "+obj.sum(4, 6, 10));
		System.out.println("Sum is = "+obj.sum(32.3, 5.4));


	}

}
