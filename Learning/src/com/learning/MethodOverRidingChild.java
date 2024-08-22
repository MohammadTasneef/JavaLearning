package com.learning;

public class MethodOverRidingChild extends MethodOverRiding{ // If the sub class have the same method as that in parent class, that is known as method overriding
	  int a=26;
	public int display(int a)      //Overriding (This method is overriding the parent method that is present in parent class).
	{
	System.out.println(" Child class is displayed "+a);
	return a;
	}

	public static void main(String[] args) {
		
		MethodOverRidingChild obj = new MethodOverRidingChild();
		obj.display(obj.a);
		obj.display(24);
	}

}
