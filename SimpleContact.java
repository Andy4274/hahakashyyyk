package com.jassoftware.textmeeting;

public class SimpleContact {

	String number;
	String name;
	
	public SimpleContact(){  //this one should not be used
		name="noone";
		number="9195555555";
	}
	
	public SimpleContact(String name, String num){
		this.name = name;
		this.number = num;	
	}
	
	public SimpleContact(String name_number){  //expects a string in the form <name>:<number>
		int sep = 0;  //to hold the position of the seaparator :
		sep = name_number.indexOf(":");
		name = name_number.substring(0, sep);
		number = name_number.substring(sep+1);
	}
	
	public String toString(){
		return this.name + ":" + this.number;
	}
	
	public String getName(){
		return this.name;
	}
	public String getNumber(){
		return this.number;
	}
	
	public boolean equals(SimpleContact sc){
		boolean equal = false;
		if ((this.name.equalsIgnoreCase(sc.name))&&(this.number.equalsIgnoreCase(sc.number))){
			equal = true;
		}
		return equal;
	}
}
