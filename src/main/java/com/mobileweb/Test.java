package com.mobileweb;

public class Test {
	
	public static String reverseString(String source) {
	    int i, len = source.length();
	    StringBuffer dest = new StringBuffer(len);
	    for (i = (len - 1); i >= 0; i--)
	      dest.append(source.charAt(i));
	    return dest.toString();
	  }
	
	public static void main(String[] args) {
		xyz abc = new xyz();
		try{
			abc.trythrow();	
			System.out.println("PASS");
		}catch(Exception e){
			System.out.println("Exception in parent");
			e.printStackTrace();
		}
		
	}
}

class xyz {
	public void trythrow() throws Exception{
		try{
			int a = 10/0;	
			System.out.println("PASS In Child");
		}catch(Exception e){
			System.out.println("Exception in children");
			e.printStackTrace();
			throw e;
		}
	}
	public void notthrow(){
		int a = 10/0;
	}
}
