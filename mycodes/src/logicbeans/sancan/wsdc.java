package logicbeans.sancan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import publicbeans.LogicPublic;
import publicbeans.Tools;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;

public class wsdc {
	private Workbook sourceXLS = null;
	private WritableWorkbook destXLS = null;
	private String destFileName = null;
	
	private String creat_table_sql= "creat table tmp_lmsgz (personid varchar(50) not null)";
	private String insert_personid_sql="intsert into tmp_lmsgz values(?) ";
	
	private String update_cardlist_sql="update CardList  set useCategory='黑名单卡' ,useStatus='3' " +
			" where personid not in (select personid from tmp_lmsgz)";
	private String update_person_sql="update person  set cardernumber=null " +
	        " where personid not in (select personid from tmp_lmsgz)";
	private String drop_table_sql="drop table tmp_lmsgz ";
	
	public void process(String filename){
		 ArrayList<String> allgh=getgh(filename);
		CreateTable();
		insertData(allgh);
		update_cardlist_Data();
		update_person_Data();
		drop_table();
		
	}
	public ArrayList<String> getgh(String srcFileName2){
		Sheet rSheet = null;
		readExcel(srcFileName2);
		rSheet = sourceXLS.getSheet(0);
	    String gh="";
	    ArrayList<String> allgh=new  ArrayList<String>();
		for(int iRow = 6 - 1;iRow < rSheet.getRows();iRow++){
			 gh= rSheet.getCell( 3 - 1, iRow).getContents().trim();
			 if(gh.length()!=0){
			 allgh.add(gh);
			 System.out.println(gh);
			 }
			 
		}
		return allgh;
	}
	
	public void CreateTable(){
		LogicPublic.instance.dbmcs.execute(creat_table_sql);	
	}
	
	public void insertData(ArrayList<String> savedata) {
		HashMap<Integer, String> oneupdate = null;
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		ArrayList<String> executeResults = null;
		String onerow;
		for(int n=savedata.size()-1;n>=0;n--){
			onerow = savedata.get(n);
			oneupdate = new HashMap<Integer, String>();
			oneupdate.put(1 , onerow);
			dbdate.add(oneupdate);
		}
		executeResults = LogicPublic.instance.dbmcs.updateData(insert_personid_sql,
				dbdate);
		Tools.print(executeResults.size());
	}
	public void update_cardlist_Data(){
		LogicPublic.instance.dbmcs.execute(update_cardlist_sql);	
	}
	public void update_person_Data(){
		LogicPublic.instance.dbmcs.execute(update_person_sql);	
	}
	public void drop_table(){
		LogicPublic.instance.dbmcs.execute(drop_table_sql);	
	}
	
	private Workbook readExcel(String fileName){
		File sourceFile = new File(fileName);
		try {
			sourceXLS = Workbook.getWorkbook(sourceFile);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sourceXLS;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		wsdc a=new wsdc();
		a.process(args[0]);
		System.out.println("处理成功，请按回车键退出！！！");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
