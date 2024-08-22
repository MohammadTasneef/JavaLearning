package com.learning;

public class SuperKeyword extends MethodOverRiding {  // super keyword = to refer parent class instance variable, to invoke immediate parent class method

	public int display(int a) {
		System.out.println(" Child class is displayed " + super.x);
		return a;
	}
	
	public void show()
	{
		super.display(10);
	}

	public static void main(String args[]) {
		
		SuperKeyword obj = new SuperKeyword();
		obj.display(23);                      //passing 23 still due to super the value of parent aka super class is displayed.
        obj.show();
	}
}