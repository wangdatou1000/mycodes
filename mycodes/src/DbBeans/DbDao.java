package DbBeans;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

public class DbDao {

	private static DbDao instance = new DbDao();

	/**
	 * ȡ��һ��DBbeanʵ������
	 */
	public static DbDao getInstance() {
		return instance;
	}

	/**
	 * ����ʼ����������Ϊ˽�У���ֹ������ʵ�������࣬ �Ա�ֻ֤��һ��ʵ�������󣬽�Լ��Դ��
	 */
	private DbDao() {
		try {
			Class.forName(DbInfo.MYSQLDriver).newInstance();
			//Class.forName(DbInfo.DbDriverStr2).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ִ��Sql��䲢���ؽ������
	 */
	public ResultSet executeQuery(String SQL, Connection conn) {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public boolean execute(String SQL, Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			return stmt.execute(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public ResultSet executeQuery(String sql, HashMap parameter, Connection conn) {
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			if (parameter != null) {
				for (int n = parameter.size() - 1; n >= 0; n--) {
					pst.setString(n + 1,
							(String) parameter.get(String.valueOf(n + 1)));
				}
			}
			rs = pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * �������ݿ⡣
	 */
	public String executeUpdate(HashMap<Integer, String> parameter,
			PreparedStatement mypreparestatement) {
		String tmp = null;
		try {
			if (parameter != null) {
				for (int n = parameter.size() - 1; n >= 0; n--) {
					mypreparestatement.setString(n + 1, parameter.get(n + 1));
				}
			}
			tmp = mypreparestatement.executeUpdate() + "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmp;
	}

	/**
	 * �������ݿ�,�����ݿ��ֶ��ж�������ʱʹ�ø÷�����
	 * ����type�����ṩ�ֶ�������Ϣ
	 * 
	 */
	public String executeUpdate(int[] type, HashMap<Integer, String> parameter,
			PreparedStatement mypreparestatement) {
		String tmp = null;
		Timestamp ts = null;
		try {
			if (parameter != null) {
				for (int n = parameter.size() - 1; n >= 0; n--) {
					if (type[n] == Types.VARCHAR) {
						mypreparestatement.setString(n + 1,
								parameter.get(n + 1));
					} else if (type[n] == Types.DECIMAL) {
						tmp = parameter.get(n + 1);
						if (tmp == null)
							tmp = "0";
						mypreparestatement.setBigDecimal(n + 1, new BigDecimal(
								tmp));
					} else if (type[n] == Types.DATE) {
						mypreparestatement.setDate(n + 1,
								Date.valueOf(parameter.get(n + 1)));
					} else if (type[n] == Types.TIMESTAMP) {
						//Tools.print(parameter.get(n + 1));
						ts = Timestamp.valueOf(parameter.get(n + 1));
						mypreparestatement.setTimestamp(n + 1, ts);
					} else if (type[n] == Types.DOUBLE) {
						mypreparestatement.setDouble(n + 1,
								Double.valueOf(parameter.get(n + 1)));
					} else if (type[n] == Types.SMALLINT) {
					  mypreparestatement.setInt(n + 1, Integer.valueOf(parameter.get(n + 1)));
					}
				}
			}
			tmp = mypreparestatement.executeUpdate() + "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmp;
	}

	/**
	 * ȡ��һ�����ݿ����ӡ�
	 */
	public Connection getConnection(String Url, String User, String pwd) {
		Connection c = null;
		try {
			c = DriverManager.getConnection(Url, User, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * ȡ��һ�����ݿ����ӡ�
	 */
	public Connection getConnection(String dbname,boolean IsAutoCommit) {
		DbBean db = DbInfo.dbinfo.getDb(dbname);
		Connection c = null;
		try {
			c = DriverManager.getConnection(db.getDBURL(), db.getDBUSER(),
					db.getDBUSERPWD());
			if(!IsAutoCommit)c.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return c;
	}

	/**
	 * ���Ԥ��ִ����䡣
	 */
	public PreparedStatement getPreparedStatement(String sql, Connection c) {
		PreparedStatement mypreparestatement = null;
		try {
			mypreparestatement = c.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mypreparestatement;
	}

	/**
	 * �����ݿ�Ĳ�ѯ���ת��Ϊ ArrayList<HashMap>���ڴ���
	 */
	public ArrayList<HashMap> resultSetToList(ResultSet rs) {
		ArrayList<HashMap> list = null;
		if (rs == null) {
			return list;
		}
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			HashMap oners = null;
			list = new ArrayList<HashMap>();
			while (rs.next()) {
				oners = new HashMap(n);
				for (int i = 1; i < n + 1; i++) {
					oners.put(rsmd.getColumnName(i), rs.getObject(i));
				}

				list.add(oners);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * �����ݿ�Ĳ�ѯ���ת��Ϊ ArrayList<HashMap>���ڴ���
	 */
	public ArrayList<HashMap<String, String>> resultSetToListByString(
			ResultSet rs) {
		ArrayList<HashMap<String, String>> list = null;
		if (rs == null) {
			return list;
		}
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			HashMap<String, String> oners = null;
			list = new ArrayList<HashMap<String, String>>();
			while (rs.next()) {
				oners = new HashMap<String, String>(n);
				for (int i = 1; i < n + 1; i++) {
					oners.put(rsmd.getColumnName(i), rs.getString(i));
				}

				list.add(oners);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
