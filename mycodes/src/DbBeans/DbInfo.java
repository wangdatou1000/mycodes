package DbBeans;

import java.util.ArrayList;
import java.util.HashMap;

import publicbeans.Tools;
  
public class DbInfo {
	final static String DbDriverStr1 = "oracle.jdbc.driver.OracleDriver";
	final static String DbDriverStr2 = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	final static String MYSQLDriver = "com.mysql.jdbc.Driver";

	public static DbInfo dbinfo = new DbInfo();
	public final String DBURL = "DBURL";
	public final String DBUSER = "DBUSER";
	public final String DBUSERPWD = "DBUSERPWD";
	public final String ERP = "ERP";

	// ˫ɫ����Ŀ���ݿ�
	public final String MYCODES = "MYCODES";
	final String MDPWD = "datou";
	final String MDURL = "jdbc:mysql://localhost:3306/mycodes";
	final String MDUSER = "datou";
	// ERP��ʽ���ݿ�
	final String ERPUrl = "jdbc:oracle:thin:@172.16.10.13:1521:erp2";
	final String ERPUser = "ncv502";
	final String ERPPwd = "rtdsedcm01";
	// kmϵ�y�Y�ώ�
	public final String KM = "KM";
	final String KMPDMPwd = "gyb700share";
	final String KMPDMUrl = "jdbc:oracle:thin:@172.16.5.1:1521:deptdb";
	final String KMPDMUser = "gybshare";

	// ���йܿ�ϵ�y�yԇ������
	public final String MCS = "MCS";
	final String MCSPwd = "mcs";
	final String MCSUrl = "jdbc:oracle:thin:@172.16.34.6:1521:mcs";
	final String MCSUser = "mcs";
	// ���϶������ݿ�
	public final String SC = "SC";
	final static String it_resource_url = "jdbc:microsoft:sqlserver://127.0.0.1:1433;DatabaseName=CCS_OffLine;SelectMethod=direct";
	final static String it_resource_user = "sa";
	final static String it_resource_pwd = "21189";

	private DbInfo() {

	}

	public DbBean getDb(String url, String pwd, String user) {
		DbBean db = new DbBean();
		db.setDBURL(url);
		db.setDBUSER(user);
		db.setDBUSERPWD(pwd);
		return db;
	}

	public DbBean getDB(String filename) {
		ArrayList<String> info = new ArrayList<String>();
		Tools.getDataFromFile(filename, info);
		HashMap<String, String> info_map = new HashMap<String, String>();
		for (int n = info.size() - 1; n >= 0; n--) {
			String tmp = info.get(n);
			String[] t = tmp.split("=");
			info_map.put(t[0], t[1]);
			System.out.println(tmp);
		}
		DbBean db = new DbBean();
		db.setDBURL(info_map.get("URL"));
		db.setDBURL(info_map.get("PWD"));
		db.setDBURL(info_map.get("USER"));
		return db;
	}

	public DbBean getDb(String dbname) {
		DbBean db = new DbBean();
		String url = null;
		String user = null;
		String pwd = null;
		if (dbname.equals(ERP)) {
			url = ERPUrl;
			user = ERPUser;
			pwd = ERPPwd;
		} else if (dbname.equals(KM)) {
			url = KMPDMUrl;
			user = KMPDMUser;
			pwd = KMPDMPwd;
		} else if (dbname.equals(MYCODES)) {
			url =MDURL ;
			user = MDUSER;
			pwd = MDPWD;
		} else if (dbname.equals(MCS)) {
			url = MCSUrl;
			user = MCSUser;
			pwd = MCSPwd;
		} else if (dbname.equals(SC)) {

			return getDB("info.txt");
		}
		db.setDBURL(url);
		db.setDBUSER(user);
		db.setDBUSERPWD(pwd);
		return db;
	}

}
