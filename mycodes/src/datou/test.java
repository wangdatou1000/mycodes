package datou;






public class test {   
    public void testArray(int[] m){
    	
    	m[0]=100;
    	
    }
    public int testfinally(){
    	int n=0;
    	try{
    		return n;
    	}finally{
    		n=9;
    	}
    }
	public static void main(String[] args) {
//		int[] a={20,200,2};
        test t=new test();
//        t.testArray(a);
//        int hs=t.hashCode();
//        String shs="";
//        for(int m=0;m<32;m++,hs>>=1){	
//        	int bit=hs&1;
//        	shs=String.valueOf(bit)+shs;
//        }
		System.out.println(t.testfinally());
		
			
	}
}	