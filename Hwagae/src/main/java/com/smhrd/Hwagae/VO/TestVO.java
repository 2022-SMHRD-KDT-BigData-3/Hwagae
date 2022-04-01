package com.smhrd.Hwagae.VO;

public class TestVO {

	String name;
	int age;
	String address;
	
	public TestVO() {
		
	}
	
	public TestVO(String name, int age, String address) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getAddress() {
		return address;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
	
}
