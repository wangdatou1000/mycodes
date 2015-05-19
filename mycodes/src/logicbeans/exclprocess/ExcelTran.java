package logicbeans.exclprocess;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelTran {
	private Workbook sourceXLS = null;
	private WritableWorkbook destXLS = null;
	private String destFileName = null;
	
	private int currRow = 0;
	
	PreparedStatement psmtQueryBOMDetail = null;
	
	public ExcelTran() {
	}
	
	public static void main(String[] args) {
		ExcelTran instance = new ExcelTran();
		if(args != null){
			String oneArg = null;
			for(int i = 0; i < args.length;i++){
				oneArg = args[i];
				if(oneArg == null)	continue;
//				System.out.println(oneArg);
			}
			instance.Process(args[0],args[1]);
		}
		System.out.println("OVER!");
	}
	
	public void Process(String srcFileName,String srcFileName2){
		final int iCol = 11 - 1 ;
		String oneFlag = null;
		String oneProduceNo = null;
		String oneParentSL = null;
		String sOneChild = null;
		String sOneProduceNo = null;
		String sTmp = null;
		String stmpSL = null;
		int iRow = 0;
		BigDecimal bgParent = null;
		BigDecimal bgResult = null;
		BigDecimal tzResult = null;
		BigDecimal bgTmp = null;
		BigDecimal sTmp1 = null;
		BigDecimal stmpSL1 = null;
		BigDecimal tzstmp =  null;
		BigDecimal bgZero = new BigDecimal("0");

		HashMap<String,HashMap<String,String>>hmProduceNoList = new HashMap<String,HashMap<String,String>>();
		HashMap<String,HashMap<String,String>>hmOneParent = new HashMap<String,HashMap<String,String>>();
		HashMap<String,String>hmOneInfo = null;
		
		Sheet rSheet = null;
		readExcel(srcFileName2);
		rSheet = sourceXLS.getSheet(0);
		for(iRow = 2 - 1;iRow < rSheet.getRows();iRow++){
			sOneProduceNo = rSheet.getCell( 2 - 1, iRow).getContents().trim();
			if(sOneProduceNo.equals(""))	continue;
			hmOneInfo = new HashMap<String,String>();
			hmOneInfo.put("PRODUCENO", sOneProduceNo);
			hmOneInfo.put("STATE", rSheet.getCell( 10 - 1, iRow).getContents().trim());
			hmOneInfo.put("GONGXU", rSheet.getCell( 11 - 1, iRow).getContents().trim());
			hmOneInfo.put("HuiSuoShiJian", rSheet.getCell( 12 - 1, iRow).getContents().trim());
			hmOneInfo.put("FanHuiShiJian", rSheet.getCell( 13 - 1, iRow).getContents().trim());
			hmProduceNoList.put(sOneProduceNo, hmOneInfo);
		}
		readExcel(srcFileName);
		createExcel(srcFileName);
		rSheet = sourceXLS.getSheet(0);
		WritableSheet wSheet = destXLS.getSheet(0);
		
//	棣栧厛鑾峰緱鎵�鏈夌埗椤�
		for(iRow = 1 - 1;iRow < rSheet.getRows();iRow++){
			oneFlag = rSheet.getCell( 1 - 1, iRow).getContents().trim();
			if(oneFlag.equals(""))	continue;
			oneProduceNo = rSheet.getCell( 2 - 1, iRow).getContents().trim();
			if(oneProduceNo.equals(""))	continue;
			if(oneFlag.equals("鐖堕」")){
				oneParentSL	= rSheet.getCell( 6 - 1, iRow).getContents().trim();			
				if(hmOneParent.get(oneProduceNo)!= null){
					System.out.println("鏈夌浉鍚岀殑鐖堕」:\t" + oneProduceNo);
				}else{
					hmOneInfo = new HashMap<String,String>();
					hmOneInfo.put("SL", oneParentSL);
					hmOneInfo.put("ROW", String.valueOf(iRow));
					hmOneParent.put(oneProduceNo, hmOneInfo);
				}
			}else{
				continue;
			}
//			System.out.println("" + oneProduceNo);
		}
		HashMap<String,ArrayList<HashMap<String,String>>>hmOneChild = new HashMap<String,ArrayList<HashMap<String,String>>>();
		for(iRow = 1 - 1;iRow < rSheet.getRows();iRow++){
			oneFlag = rSheet.getCell( 1 - 1, iRow).getContents().trim();
			if(oneFlag.equals(""))	continue;
			oneProduceNo = rSheet.getCell( 2 - 1, iRow).getContents().trim();
			if(oneProduceNo.equals(""))	continue;
			if(oneFlag.equals("鐖堕」")){
//				oneParentSL	= rSheet.getCell( 6 - 1, iRow).getContents().trim();			
//				if(hmOneParent.get(oneProduceNo)!= null){
//					System.out.println("鏈夌浉鍚岀殑鐖堕」:\t" + oneProduceNo);
//				}else{
//					hmOneParent.put(oneProduceNo, oneParentSL);
//				}
			}else if(oneFlag.equals("瀛愰」")){
				hmOneInfo = hmOneParent.get(oneProduceNo);
				if(hmOneInfo != null){
					oneParentSL = hmOneParent.get(oneProduceNo).get("SL");
					bgParent = new BigDecimal(oneParentSL);
					bgResult = bgParent;
					sTmp = rSheet.getCell( 19 - 1, iRow).getContents().trim();
					String[] alOneChild = sTmp.split(",");
					int iStart = 0;
					int iEnd = 0;
					int iCount = alOneChild.length;
					for(int iChild = 0 ; iChild < iCount;iChild++){
						sOneChild = alOneChild[iChild];
						iStart = sOneChild.indexOf("(");
						iEnd = sOneChild.indexOf(")");
						sOneProduceNo = sOneChild.substring(0,iStart);
						if(hmOneParent.get(sOneProduceNo) == null){
//							System.out.println("娌℃湁鐖堕」:\t" + oneProduceNo + "," + sOneProduceNo);
							continue;
						}
						sTmp = sOneChild.substring(iStart + 1, iEnd);
						bgTmp = new BigDecimal(sTmp);
						bgResult = bgResult.subtract(bgTmp);
					}
					tzResult = bgParent.subtract(bgResult);
					setValueToXLS(wSheet,iRow,28 - 1,String.valueOf(tzResult));
					setValueToXLS(wSheet,iRow,29 - 1,String.valueOf(bgResult));
					hmOneInfo.put("AAA", String.valueOf(bgResult));
					hmOneParent.put(oneProduceNo,hmOneInfo );
					hmOneInfo = hmProduceNoList.get(oneProduceNo);
					if(hmOneInfo == null){
						System.out.println("14鎵�浠诲姟鎶ヨ〃涓棤姝や护鍙凤細" + oneProduceNo);
						continue;
					}
					setValueToXLS(wSheet,iRow,30 - 1,hmOneInfo.get("STATE"));
					setValueToXLS(wSheet,iRow,31 - 1,hmOneInfo.get("GONGXU"));
					setValueToXLS(wSheet,iRow,32 - 1,hmOneInfo.get("HuiSuoShiJian"));
					setValueToXLS(wSheet,iRow,33 - 1,hmOneInfo.get("FanHuiShiJian"));
//					setValueToXLS(wSheet,Integer.parseInt(sTmp),27 - 1,String.valueOf(bgResult));
				}
			}
//			System.out.println("" + oneProduceNo);
		}
		
		Iterator itor = hmOneParent.entrySet().iterator();
		while(itor.hasNext()){
			Entry oneEntry = (Entry)itor.next();
			oneProduceNo = (String)(oneEntry.getKey());
			hmOneInfo = (HashMap)(oneEntry.getValue());
//			hmOneInfo = (HashMap<String,String>)itor.next();
			if(hmOneInfo == null)	continue;
			iRow = Integer.parseInt(hmOneInfo.get("ROW"));
			sTmp = hmOneInfo.get("AAA");
			if(sTmp == null){
				sTmp = hmOneInfo.get("SL");
			}
			stmpSL = hmOneInfo.get("SL");
			sTmp1 = new BigDecimal(sTmp);
			stmpSL1 = new BigDecimal(stmpSL);
			tzstmp =  stmpSL1.subtract(sTmp1);
			setValueToXLS(wSheet,iRow-1,28 - 1,"濂楄鏁伴噺");
			setValueToXLS(wSheet,iRow-1,29 - 1,"浜や粯鏁伴噺");
			setValueToXLS(wSheet,iRow,28 - 1,String.valueOf(tzstmp));
			setValueToXLS(wSheet,iRow,29 - 1,String.valueOf(sTmp));
			hmOneInfo = hmProduceNoList.get(oneProduceNo);
			if(hmOneInfo == null){
				System.out.println("14鎵�浠诲姟鎶ヨ〃涓棤姝や护鍙凤細" + oneProduceNo);
				continue;
			}
			setValueToXLS(wSheet,iRow-1,30 - 1,"鐩墠鐘舵��");
			setValueToXLS(wSheet,iRow-1,31 - 1,"鍥炴墍宸ュ簭");
			setValueToXLS(wSheet,iRow-1,32 - 1,"鍥炴墍鏃堕棿");
			setValueToXLS(wSheet,iRow-1,33 - 1,"杩斿洖鏃堕棿");
			setValueToXLS(wSheet,iRow,30 - 1,hmOneInfo.get("STATE"));
			setValueToXLS(wSheet,iRow,31 - 1,hmOneInfo.get("GONGXU"));
			setValueToXLS(wSheet,iRow,32 - 1,hmOneInfo.get("HuiSuoShiJian"));
			setValueToXLS(wSheet,iRow,33 - 1,hmOneInfo.get("FanHuiShiJian"));
		}
		
		outputFile();
		return;
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
	
	private Workbook createDestExcel(String destFileName){
		File destFile = new File(destFileName);
		if(destFile.exists())	destFile.delete();
		try {
			destXLS = Workbook.createWorkbook(destFile,sourceXLS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sourceXLS;
	}

	private WritableWorkbook createExcel(String templateFileName){
		File sourceFile = new File(templateFileName);
		File destFile = new File(templateFileName);
		Workbook srcXLS = null;
		WritableWorkbook dstXLS = null;
//		if(destFile.exists())	destFile.delete();
		try {
			srcXLS = Workbook.getWorkbook(sourceFile);
			destXLS = Workbook.createWorkbook(destFile,srcXLS);
		}catch (BiffException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return destXLS;
	}
	
	
	private Workbook getDestExcel(String fileName){
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
	
    public void addRow(String[] oneRow){
    	WritableSheet wSheet = destXLS.getSheet(0);
    	for(int i = 0 ; i < oneRow.length; i++){
        	setValueToXLS(wSheet,i,currRow,oneRow[i]);
    	}
    	currRow++;
    	
    }

    public void outputFile(){
    	try {
			destXLS.write();
			destXLS.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return;
    }

    private void setValueToXLS(WritableSheet wSheet,int row,int col,String value){
    	if(value == null)	value = "";
    	String tmp = value;
    	
    	Label cell = null;
    	WritableCell tmpCell = wSheet.getWritableCell(col,row);
    	CellType tmpType = tmpCell.getType();
    	if(tmpType == CellType.EMPTY){
    	    cell = new Label(col,row,tmp);
    	    try {
				wSheet.addCell(cell);
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    	    cell.setString(tmp);
    	}else{
    	    cell = (Label)tmpCell;
    	    cell.setString(tmp);
    	}
    	return;
	}

}