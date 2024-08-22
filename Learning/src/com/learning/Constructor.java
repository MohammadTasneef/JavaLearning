package com.learning;

public class Constructor {

	
	int num=49;
	
    public Constructor()    //constructor gets executed first whenever the object instance is created and a memory is allocated to it as well , constructor is a special type of method with same name as that of class name
        {
		System.out.println("Constructor is executed");  
	}
	public static void main(String[] args) {
		
		Constructor obj= new Constructor();
		int b=obj.num;
		System.out.println(b);

	}

}
