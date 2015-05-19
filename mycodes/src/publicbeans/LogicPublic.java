package publicbeans;

import java.util.ArrayList;
import java.util.HashMap;

import DbBeans.DbInfo;
import DbBeans.DbManager;

public class LogicPublic {
	public static LogicPublic instance = new LogicPublic();
	public String comma = ",\n";
	public DbManager dberp = new DbManager(DbInfo.dbinfo.ERP);
	public DbManager dbkm = new DbManager(DbInfo.dbinfo.KM);
	public DbManager dbmcs = new DbManager(DbInfo.dbinfo.MCS);
	public DbManager dbsc = new DbManager(DbInfo.dbinfo.SC);
	public DbManager dbmd = new DbManager(DbInfo.dbinfo.MYCODES);
	public String lable = "@datou@";
	public String XmlExtendName = ".xml";
	public String FormatTime = "yyyy-MM-dd HH:mm:ss";

	private LogicPublic() {

	}

	// public void setDbManager(String dbname,String url,String user,String
	// pwd){
	// if(dbname.equals("sc")){
	// dbsc=new DbManager(DbInfo.dbinfo.getDb(url, pwd, user));
	// }
	//
	// }
	public HashMap getchejian_mc() {
		HashMap cj = new HashMap();
		cj.put("31", "һ");
		cj.put("32", "��");
		cj.put("33", "��");
		cj.put("34", "��");
		cj.put("35", "��");
		cj.put("36", "��");
		cj.put("20", "��");
		cj.put("47", "΢");
		cj.put("24", "��");
		cj.put("27", "��");
		cj.put("30", "װ");
		cj.put("23", "��");
		cj.put("50", "ľ");
		cj.put("51", "һ��");
		cj.put("52", "����");
		cj.put("53", "����");
		cj.put("54", "�Ĳ�");
		cj.put("55", "�岿");
		cj.put("56", "����");
		cj.put("57", "�߲�");
		cj.put("58", "�˲�");
		cj.put("59", "�Ų�");
		return cj;
	}

	public ArrayList jiexipartcode(String partcode) {
		ArrayList list = new ArrayList();
		int pos = 0, to_n = 0, from_n = 0;
		String partcode_base = null;
		String partcode_from = null;
		String partcode_to = null;

		pos = partcode.indexOf(".");
		if (pos < 0) {
			return null;
		}
		pos = partcode.indexOf(".", pos + 1);
		if (pos < 0) {
			return null;
		}

		partcode_base = partcode.substring(0, pos + 1);
		int n = partcode.indexOf("/", pos + 1);
		if (n < 0) {
			list.add(partcode);
			return list;
		}
		partcode_from = partcode.substring(pos + 1, n);
		partcode_to = partcode.substring(n + 1);

		if (partcode_from.length() > partcode_to.length() || partcode_from.length() < 3) {
			list.add(partcode);
			return list;
		}

		char[] from = partcode_from.toCharArray();
		char[] to = partcode_to.toCharArray();
		char ch = ' ';
		String mm_s = "";
		for (int i = 0; i < from.length; i++) {
			ch = from[i];
			if (ch < '0' || ch > '9') {
				list.add(partcode);
				return list;
			}
			from_n = 10 * from_n + ch - 48;
		}
		for (int i = 0; i < to.length; i++) {
			ch = to[i];
			if (ch < '0' || ch > '9') {
				list.add(partcode);
				return list;
			}
			to_n = 10 * to_n + ch - 48;
		}
		if (from_n > to_n) {
			list.add(partcode);
			return list;
		}
		for (int mm = from_n; mm <= to_n; mm++) {
			mm_s = mm + "";
			while (partcode_from.length() > mm_s.length()) {
				mm_s = "0" + mm_s;
			}
			partcode = partcode_base + mm_s;
			// tools.print(partcode);
			list.add(partcode);
		}
		return list;
	}

	public String process_gx_id(String oneGXH) {
		int dd = oneGXH.indexOf(".");
		if (dd >= 0) {
			oneGXH = "0" + oneGXH.substring(0, dd) + oneGXH.substring(dd + 1);
		}
		if (oneGXH.length() == 1) {
			oneGXH = "0" + oneGXH + "0";
		} else if (oneGXH.length() == 2) {
			oneGXH = oneGXH + "0";
		} else if (oneGXH.length() != 3) {
			System.out.println(oneGXH);
		}
		return oneGXH;
	}

}
