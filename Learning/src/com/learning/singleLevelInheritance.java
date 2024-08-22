package com.learning;

import com.stringLearning.DuplicateCharacter;

public class singleLevelInheritance extends nonStaticMethod {

       public  int display(int id)	{
    	   System.out.println("The ID number is = "+id);
    	   return id;
       }
	
	
	public static void main(String[] args) {
		
		singleLevelInheritance obj=new singleLevelInheritance();
		int a=obj.add(10, 20);                              // user defined from nonStaticMethod class
		int b=obj.multiply(5, 20);                          // user defined from nonStaticMethod class
		System.out.println("Sum is = "+a);
		System.out.println("Multiplication is = "+b);
		obj.show(a);                                       // user defined from nonStaticMethod class
		obj.display(b);                                   // user defined from singleLevelInheritance class
		nonStaticMethod.add1(2, 3);                      // static method from nonStaticMethod class
		

	}

}
