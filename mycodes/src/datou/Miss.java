package datou;

import java.util.ArrayList;

public class Miss implements Comparable<Miss>{
    private int id;
    private int maxMiss;
    private int lastMiss;
    private double averageMiss; 
	private int totalTimes;
	private ArrayList<Integer> allMiss;
	private int lastIndex;
	public final double PERFECTMISS;
	public ArrayList<Integer> getAllMiss() {
		return allMiss;
	}
	public Miss(int id,double perfectMiss){
		PERFECTMISS=perfectMiss;
		allMiss=new ArrayList<Integer>();
		this.id=id;
	}
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getMaxMiss() {
		return maxMiss;
	}



	public void setMaxMiss(int maxMiss) {
		this.maxMiss = maxMiss;
	}


    public void processForAverageMiss(){
    	int temp=0;
        for(int n=0;n<allMiss.size();n++){
 		   temp+=allMiss.get(n);
 		}
         averageMiss=temp/(totalTimes*1.0); 	
    }
	public double getAverageMiss() {  
		return averageMiss;
	}



	public void setAverageMiss(double averageMiss) {
		
		this.averageMiss = averageMiss;
	}



	public int getTotalTimes() {
		return totalTimes;
	}



	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}





	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	public int getLastMiss() {
		return lastMiss;
	}
	public void setLastMiss(int lastMiss) {
		this.lastMiss = lastMiss;
	}
	@Override
	public int compareTo(Miss o) {	
		return (maxMiss<o.getMaxMiss()?-1:(maxMiss==o.getMaxMiss()?0:1));
	}

}
