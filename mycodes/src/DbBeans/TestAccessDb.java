/**
 * 
 */
package DbBeans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * 功能描述：
 * 
 * @author 王世杰
 * @version 1.0, 2015年5月19日 上午9:27:23
 */
public class TestAccessDb {
	public static Connection getJDBCConnection() {
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");// 加载Access驱动
			Properties prop = new Properties();
			prop.put("charSet", "gb2312"); // 设置编码防止中文出现乱码
			/**
			 * 兼容07+的Access
			 * **/
			/*
			 * Connection con1 = DriverManager .getConnection(
			 * "jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ=/home/datou/work/test.accdb"
			 * , prop);
			 */
			/**
			 * 
			 * 兼容03的Access
			 * **/
			Connection con = DriverManager
					.getConnection(
							"jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ=/home/datou/work/test1.mdb",
							prop);
			// System.out.println(con);//打印连接
			return con;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] a) {
		try {
			getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 功能描述：
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	/**
	 * 打印access的前2列数据
	 * */
	public static void getAll() throws Exception {
		Connection con = getJDBCConnection();
		PreparedStatement ps = con.prepareStatement("select *from info ");// 07
		// PreparedStatement ps=con.prepareStatement("select *from testcopy ");
		// 03
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1) + "   " + rs.getString(2));
		}
		rs.close();
		ps.close();
		con.close();
	}

}
