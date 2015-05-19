package datou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class myuntils {

	public static void main(String[] args) {

		myuntils m = new myuntils();
		// ArrayList<String> allcodes = m.getAllCodes();
		// System.out.println(allcodes.size());
		// m.outPutAllCodes(allcodes, "e:/CodesDataForAll.txt");
		// m.processAllData("e:/scm.properties","e:/allCodeData.txt");
		// m.insertOldCodes(m.INSERTOLDCODES,"e:/allCodeData.txt",8);
		// m.insertForManyTimes();
//		ArrayList<String> allCodes = new ArrayList<String>();
//		StringBuffer oneCode = new StringBuffer();
//		m.getAllCodesByDFS(1, 33, 6, allCodes, oneCode);
//		System.out.println(allCodes.size());
		//m.updateOldCodes();
		m.updateBLUEOldCodes(m.UPDATEOLDCODESFORBLUE, "e:/new1.txt");
	}

	private final String INSERTOLDCODES = "INSERT INTO mycodes.oldcodes"
			+ "(number, a, b, c, d, e, f, blue)"
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
	private final String INSERTALLCODES = "INSERT INTO mycodes.allcodes"
			+ "( a, b, c, d, e, f, blue)" + "VALUES(?, ?, ?, ?, ?, ?, ?);";
    private final String UPDATEOLDCODES="UPDATE mycodes.allcodes SET isold=1"
    		+ " WHERE isold=0 and a=? and b=?  and c=? and d=? and e=? and f=? and blue=?;";
    
    private final String UPDATEOLDCODESFORBLUE="UPDATE mycodes.oldcodes SET blue=?"
    		+ " WHERE number=?";
    
    private final String GETOLDCODES="select * from mycodes.oldcodes";
    public void updateOldCodes(){
    	ArrayList<HashMap<String,String>> data=LogicPublic.instance.dbmd.getDataByString(GETOLDCODES);
		HashMap<Integer, String> oneupdate = null;
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		ArrayList<String> executeResults = null;
		int[] types={Types.SMALLINT,Types.SMALLINT,Types.SMALLINT,Types.SMALLINT,Types.SMALLINT,Types.SMALLINT,Types.SMALLINT,Types.SMALLINT,};
		for (HashMap<String,String> oneCode: data) {
			oneupdate = new HashMap<Integer, String>();
			oneupdate.put(1, oneCode.get("a"));
			oneupdate.put(2, oneCode.get("b"));
			oneupdate.put(3, oneCode.get("c"));
			oneupdate.put(4, oneCode.get("d"));
			oneupdate.put(5, oneCode.get("e"));
			oneupdate.put(6, oneCode.get("f"));
			oneupdate.put(7, oneCode.get("blue"));
			dbdate.add(oneupdate);
		}
		Tools.print(dbdate.size());
		executeResults = LogicPublic.instance.dbmd.updateData(types,UPDATEOLDCODES, dbdate);
		Tools.print(executeResults);
    	
    	
    }
    
    public void insertForManyTimes() {
		ArrayList<String> data = getAllCodes();
		ArrayList<String> onedata = new ArrayList<String>();
		System.out.println(data.size());
		for (int n = 0, m = data.size(); n < m; n++) {
			onedata.add(data.get(n));
			if (onedata.size() == 100000) {
				insertAllCodes(INSERTALLCODES, onedata, 7);
				onedata.clear();
				System.out.print(onedata.size());
			}
		}
		if (onedata.size() > 0) {
			insertAllCodes(INSERTALLCODES, onedata, 7);
			onedata.clear();
			System.out.print(onedata.size());
		}
	}
    public void updateBLUEOldCodes(String sql, String fileName) {
		ArrayList<String> data = new ArrayList<String>();
		Tools.getDataFromFile(fileName, data);
		HashMap<Integer, String> oneupdate = null;
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		ArrayList<String> executeResults = null;
		int[] types={Types.SMALLINT,Types.VARCHAR};
		String[] codesArray = null;
		for (String oneCode : data) {
			oneCode=oneCode.replaceAll("	+", " ");
			codesArray = oneCode.split(" ");
			oneupdate = new HashMap<Integer, String>();
			if(codesArray[7].indexOf("(")<0)
				continue;
			oneupdate.put(1, codesArray[7].substring(0,2));
			oneupdate.put(2, codesArray[0]);
			dbdate.add(oneupdate);
			//if(dbdate.size()==2)break;
		}
		Tools.print(dbdate.size());
		executeResults = LogicPublic.instance.dbmd.updateData(types,sql, dbdate);
		Tools.print(executeResults);
	}
	public void insertOldCodes(String sql, String fileName, int w) {
		ArrayList<String> data = new ArrayList<String>();
		Tools.getDataFromFile(fileName, data);
		HashMap<Integer, String> oneupdate = null;
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		ArrayList<String> executeResults = null;
		String[] codesArray = null;
		for (String oneCode : data) {
			codesArray = oneCode.split(" ");
			oneupdate = new HashMap<Integer, String>();
			for (int n = 0; n < w; n++) {
				oneupdate.put(n + 1, codesArray[n]);
			}
			dbdate.add(oneupdate);
		}
		Tools.print(dbdate.size());
		executeResults = LogicPublic.instance.dbmd.updateData(sql, dbdate);
		Tools.print(executeResults);
	}

	public void insertAllCodes(String sql, ArrayList<String> data, int w) {
		HashMap<Integer, String> oneupdate = null;
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		ArrayList<String> executeResults = null;
		String[] codesArray = null;
		for (String oneCode : data) {
			codesArray = oneCode.split(" ");
			oneupdate = new HashMap<Integer, String>();
			for (int n = 0; n < w; n++) {
				oneupdate.put(n + 1, codesArray[n]);
			}
			dbdate.add(oneupdate);
		}
		Tools.print(dbdate.size());
		executeResults = LogicPublic.instance.dbmd.updateData(sql, dbdate);
		Tools.print(executeResults);
	}

	public void outPutAllCodes(ArrayList<String> allcodes, String outFileName) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(outFileName);
			for (String code : allcodes) {
				fw.write(code + "\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void processAllData(String fileName, String newFileName) {

		File f = new File(fileName);
		BufferedReader out = null;
		FileWriter fw = null;
		try {
			out = new BufferedReader(new FileReader(f));
			fw = new FileWriter(newFileName);
			while (out.ready()) {
				String a = out.readLine();
				// System.out.println(a);
				a = a.replaceAll("	+", " ");
				System.out.println(a);
				fw.write(a + "\n");
			}
			out.close();
			fw.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /**
     * 采用深度优先算法方案，代码简单，但是用递归实现效率不高。
     * */
	public void getAllCodesByDFS(int start, int end, int times,
			ArrayList<String> allCodes, StringBuffer oneCode) {
		StringBuffer oneCode2 = new StringBuffer();
		for (int n = start; n <= end - (times - 1); n++) {
			oneCode2.append(oneCode);
			oneCode2.append(" ");
			if (n < 10)
				oneCode2.append("0");
			oneCode2.append(n);
			if (times == 1) {
				allCodes.add(oneCode2.toString());
				System.out.println(oneCode2.toString());
			} else {
				getAllCodesByDFS(n + 1, end, times - 1, allCodes, oneCode2);
			}
			oneCode2.setLength(0);

		}
	}

	public ArrayList<String> getAllCodes() {
		ArrayList<String> allCodes = new ArrayList<>(1107568 * 30);
		String split = " ";
		String ns, ms, as, bs, cs, ds, es;
		for (int n = 1; n <= 33 - 5; n++) {
			if (n < 10)
				ns = "0" + n;
			else
				ns = "" + n;
			for (int m = n + 1; m <= 33 - 4; m++) {
				if (m < 10)
					ms = "0" + m;
				else
					ms = "" + m;
				for (int a = m + 1; a <= 33 - 3; a++) {
					if (a < 10)
						as = "0" + a;
					else
						as = "" + a;
					for (int b = a + 1; b <= 33 - 2; b++) {
						if (b < 10)
							bs = "0" + b;
						else
							bs = "" + b;
						for (int c = b + 1; c <= 33 - 1; c++) {
							if (c < 10)
								cs = "0" + c;
							else
								cs = "" + c;
							for (int d = c + 1; d <= 33; d++) {
								if (d < 10)
									ds = "0" + d;
								else
									ds = "" + d;
								for (int e = 1; e < 17; e++) {
									if (e < 10)
										es = "0" + e;
									else
										es = "" + e;
									allCodes.add(ns + split + ms + split + as
											+ split + bs + split + cs + split
											+ ds + split + es);

								}
							}
						}
					}
				}
			}
		}
		return allCodes;
	}
}
