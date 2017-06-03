package com.memory.platform.test.core;

public class GenericsTypeTest {

	public <T> T test1() {
		
		return (T) new String("df");
	}
	
	public <T> void test2() {
		
		System.out.println("dsf");
	}
	
	public <G,T> void test3(T t,Class<T> c,Class<G> g) {
		if(null != t) {
			System.out.println(t.getClass().getName());
		}
		if(null != c) {
			System.out.println(c.getName());
		}
	}
	public static void main(String[] args) {
		GenericsTypeTest gtt = new GenericsTypeTest();
		System.out.println(gtt.test1());
		gtt.test2();
		gtt.test3(gtt.new User(), User.class,new String("S").getClass());
	}
	
	
	public class User {
		public String name;
		public int age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
	}
	
}
