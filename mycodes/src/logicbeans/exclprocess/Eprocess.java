package logicbeans.exclprocess;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import jxl.CellFormat;
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

public class Eprocess {
	private Workbook sourceXLS = null;
	private WritableWorkbook destXLS = null;
	private String destFileName = null;
	private HashMap<String, Boolean> freedate = new HashMap<String, Boolean>();
	private ArrayList<String> ordername = new ArrayList<String>();

	private String getState(String gotime, String leavetime, boolean isfreedate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 有上班记录无下班记录或者有下班记录无上班记录，一律视为缺勤。
		// 0:缺勤，1:迟到，2:早退，3:加班，12:迟到、早退，13:迟到、加班，4:出勤
		if ((gotime == null || gotime.length() == 0 || leavetime == null || leavetime
				.length() == 0)) {
			return "0";
		}

		try {
			Date d = sdf.parse(gotime);
			int d_hours = d.getHours();
			int d_minutes = d.getMinutes();
			Date l = sdf.parse(leavetime);
			int L_hours = l.getHours();
			int L_minutes = l.getMinutes();

			if (!isfreedate) {
				if ((d_hours == 8 && d_minutes > 40) || (d_hours > 8)) {
					if (L_hours < 17) {
						return "12"; // 迟到、早退
					} else if (L_hours > 19
							|| (L_hours == 19 && L_minutes >= 40)) {
						return "13";// 迟到、加班

					} else {
						return "1";
					}
				}
				if (L_hours < 17) {
					return "2";
				}

				if (L_hours > 19 || (L_hours == 19 && L_minutes >= 40)) {
					return "3";
				}
			} else {
				if ((d_hours == 8 && d_minutes > 50) || (d_hours > 8)) {
					if (L_hours < 16 || (L_hours == 16 && L_minutes < 10)) {
						return "12"; // 迟到、早退
					} else {
						return "1";

					}
				}
				if (L_hours < 16 || (L_hours == 16 && L_minutes < 10)) {
					return "2";
				}
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "4";
	}

	private boolean isfreedate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		int whichdate = 0;
		try {
			d = sdf.parse(date);
			Calendar cr = Calendar.getInstance();
			cr.setTime(d);
			whichdate = cr.get(cr.DAY_OF_WEEK);
			if (whichdate == 1 || whichdate == 7) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return false;
	}

	private String whichdate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		int whichdate = 0;
		try {
			d = sdf.parse(date);
			Calendar cr = Calendar.getInstance();
			cr.setTime(d);
			whichdate = cr.get(cr.DAY_OF_WEEK);
			if (whichdate == 1) {
				return "7";
			} else if (whichdate == 7) {
				return "6";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "no";

	}

	public HashMap<String, ArrayList<oneinfo>> readdata() throws ParseException {
		sourceXLS = readExcel("sichang.xls");
		Sheet rSheet = sourceXLS.getSheet(0);
		oneinfo oneperson = null;
		ArrayList<HashMap<String, oneinfo>> all = new ArrayList<HashMap<String, oneinfo>>();
		HashMap<String, ArrayList<oneinfo>> allperson = new HashMap<String, ArrayList<oneinfo>>();
		ArrayList<oneinfo> alldate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int iRow;
		String gotime = "";
		String leavetime = "";
		Date d = null;
		boolean isfreedate = false;
		for (iRow = 2 - 1; iRow < rSheet.getRows(); iRow++) {
			oneperson = new oneinfo();
			oneperson.setCode(rSheet.getCell(4 - 1, iRow).getContents().trim());
			alldate = allperson.get(oneperson.getCode());
			if (alldate == null) {
				alldate = new ArrayList<oneinfo>();
			}
			oneperson.setName(rSheet.getCell(5 - 1, iRow).getContents().trim());
			oneperson.setDate(rSheet.getCell(8 - 1, iRow).getContents().trim());
			gotime = rSheet.getCell(6 - 1, iRow).getContents().trim();
			leavetime = rSheet.getCell(7 - 1, iRow).getContents().trim();
			isfreedate = isfreedate(oneperson.getDate());
			freedate.put(oneperson.getDate(), isfreedate);
			oneperson.setState(getState(gotime, leavetime, isfreedate));
			alldate.add(oneperson);
			allperson.put(oneperson.getCode(), alldate);
			if (!ordername.contains(oneperson.getCode())) {
				ordername.add(oneperson.getCode());
			}
		}
		return allperson;
	}
    private String getOutState(String state){
    	if(state.equals("0")){
    		return "X";
       	}else if (state.equals("1")){
    		return "≯";
       	}else if (state.equals("12")){
    		return "≯/≮";
       	}else if (state.equals("13")){
    		return "≯/△";
       	}else if (state.equals("2")){
    		return "≮";
       	}else if (state.equals("3")){
    		return "△";
       	}else if (state.equals("4")){
    		return "√";
       	}
    	return null;
    }
	private void createExl() throws ParseException {
		destXLS = createExcel("kqb.xls");
		WritableSheet wSheet = destXLS.getSheet(0);
		HashMap<String, ArrayList<oneinfo>> allperson = readdata();
		HashMap<String, tjb> alltj = new HashMap<String, tjb>();
		ArrayList<oneinfo> alldate = null;
		setValueToXLS(wSheet, 1 - 1, 2 - 1, "日期");
		setValueToXLS(wSheet, 2 - 1, 1 - 1, "工号");
		setValueToXLS(wSheet, 2 - 1, 2 - 1, "姓名");
		int size = ordername.size();
		for (int n = 0; n < size; n++) {
			String name = ordername.get(n);
			alldate = allperson.get(name);
			setValueToXLS(wSheet, 3 - 1 + n, 1 - 1, ordername.get(n));
			setValueToXLS(wSheet, 3 - 1 + n, 2 - 1, alldate.get(0).getName());
			tjb onetj = new tjb();
			onetj.setName(alldate.get(0).getName());
			onetj.setCode(ordername.get(n));
			int jb = 0;
			int z6 = 0;
			int z7 = 0;
			int cd = 0;
			int zt = 0;
			for (int m = 0; m < alldate.size(); m++) {
				String state=alldate.get(m).getState();
				setValueToXLS(wSheet, 3 - 1 + n, 3 - 1 + m, getOutState(state));
				setValueToXLS(wSheet, 1 - 1, 3 - 1 + m, alldate.get(m)
						.getDate());
				setValueToXLS(wSheet, 2 - 1, 3 - 1 + m, m + 1 + "");
				
				if (freedate.get(alldate.get(m).getDate())) {
					setBkColor(wSheet, 1 - 1, 3 - 1 + m);
					setBkColor(wSheet, 2 - 1, 3 - 1 + m);
					setBkColor(wSheet, 3 - 1 + n, 3 - 1 + m);
					if (!state.equals("0")) {
						if (whichdate(alldate.get(m).getDate()).equals("6")) {
							z6 += 1;
						} else if (whichdate(alldate.get(m).getDate()).equals(
								"7")) {
							z7 += 1;
						}
					}else if (state.equals("1")||state.equals("12")) {
					     cd+=1;
					   
					}else if (state.equals("2")||state.equals("12")) {
					     zt+=1;
					   
					}

				} else {
					if (state.equals("3")||state.equals("13")) {
						jb += 1;
					} else if (state.equals("1")||state.equals("13")||state.equals("12")) {
						cd += 1;
					} else if (state.equals("2")||state.equals("12")) {
						zt += 1;
					}
				}
			}
			onetj.setCd(cd);
			onetj.setPsjb(jb);
			onetj.setZ6(z6);
			onetj.setZ7(z7);
			onetj.setZt(zt);
			alltj.put(ordername.get(n), onetj);

		}
		outputFile(destXLS);
		destXLS = createExcel("tjb.xls");
		wSheet = destXLS.getSheet(0);
		setValueToXLS(wSheet, 2 - 1, 7 - 1, "早退（次）");
		setValueToXLS(wSheet, 2 - 1, 6 - 1, "迟到（次）");
		setValueToXLS(wSheet, 2 - 1, 5 - 1, "周日（次）");
		setValueToXLS(wSheet, 2 - 1, 4 - 1, "周六（次）");
		setValueToXLS(wSheet, 2 - 1, 3 - 1, "平时加班（次）");
		setValueToXLS(wSheet, 2 - 1, 2 - 1, "姓名");
		setValueToXLS(wSheet, 2 - 1, 1 - 1, "工号");
		size = ordername.size();
		tjb onerow = null;
		for (int n = 0; n < size; n++) {
			String code = ordername.get(n);
			onerow = alltj.get(code);
			setValueToXLS(wSheet, 3 - 1 + n, 7 - 1, onerow.getZt() + "");
			setValueToXLS(wSheet, 3 - 1 + n, 6 - 1, onerow.getCd() + "");
			setValueToXLS(wSheet, 3 - 1 + n, 5 - 1, onerow.getZ7() + "");
			setValueToXLS(wSheet, 3 - 1 + n, 4 - 1, onerow.getZ6() + "");
			setValueToXLS(wSheet, 3 - 1 + n, 3 - 1, onerow.getPsjb() + "");
			setValueToXLS(wSheet, 3 - 1 + n, 2 - 1, onerow.getName() + "");
			setValueToXLS(wSheet, 3 - 1 + n, 1 - 1, onerow.getCode() + "");
		}
		outputFile(destXLS);
	}

	public void outputFile(WritableWorkbook destXLS) {
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

	private WritableWorkbook createExcel(String templateFileName) {
		File sourceFile = new File(templateFileName);
		File destFile = new File(templateFileName);
		WritableWorkbook destXLS = null;
		Workbook srcXLS = null;
		try {
			srcXLS = Workbook.getWorkbook(sourceFile);
			destXLS = Workbook.createWorkbook(destFile, srcXLS);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destXLS;
	}

	private Workbook readExcel(String fileName) {
		File sourceFile = new File(fileName);
		Workbook sourceXLS = null;
		try {
			sourceXLS = Workbook.getWorkbook(sourceFile);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sourceXLS;
	}

	private Workbook createDestExcel(String destFileName, Workbook sourceXLS) {
		WritableWorkbook destXLS = null;
		File destFile = new File(destFileName);
		if (destFile.exists())
			destFile.delete();
		try {
			destXLS = Workbook.createWorkbook(destFile, sourceXLS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sourceXLS;
	}

	private void setBkColor(WritableSheet wSheet, int row, int col) {
		WritableCell tmpCell = wSheet.getWritableCell(0, 0);
		WritableCell okCell = wSheet.getWritableCell(col, row);
		jxl.format.CellFormat cf = tmpCell.getCellFormat();
		okCell.setCellFormat(cf);
	}

	private void setValueToXLS(WritableSheet wSheet, int row, int col,
			String value) {
		if (value == null)
			value = "";
		String tmp = value;

		Label cell = null;
		WritableCell tmpCell = wSheet.getWritableCell(col, row);
		CellType tmpType = tmpCell.getType();
		if (tmpType == CellType.EMPTY) {
			cell = new Label(col, row, tmp);
			try {
				wSheet.addCell(cell);
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// cell.setString(tmp);
		} else {
			cell = (Label) tmpCell;
			cell.setString(tmp);
		}
		return;
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		Eprocess a = new Eprocess();
		// System.out.println(a.readdata().size());
		a.createExl();
		System.out.println("已完成考勤統計，請按回車鍵退出");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
