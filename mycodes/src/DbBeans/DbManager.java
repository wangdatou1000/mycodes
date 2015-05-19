package DbBeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DbManager {
	private final DbDao dbDao = DbDao.getInstance();
	private Connection c = null;

	public DbManager(Connection c) {
		this.c = c;
	}
	public DbManager(String dbname) {
		c = dbDao.getConnection(dbname,false);
	}
	public ArrayList<HashMap> getData(String sql) {
		return dbDao.resultSetToList(dbDao.executeQuery(sql, c));
	}
	public ArrayList<HashMap<String,String>> getDataByString(String sql) {
		return dbDao.resultSetToListByString((dbDao.executeQuery(sql, c)));
	}
	public boolean execute(String sql){
		try {
			c.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbDao.execute(sql, c);
	}
	public ArrayList<String> updateData(String sql,
			ArrayList<HashMap<Integer, String>> parameter) {
		PreparedStatement mypreparestatement = dbDao.getPreparedStatement(sql, c);
		HashMap<Integer, String> oneUpdate = new HashMap<Integer, String>();
		ArrayList<String> executeResults = new ArrayList<String>();
		String tmp = null;
		if (parameter == null) {
			return null;
		}
		int b = 0;
		int all = parameter.size();
		int newtimes = all / 2000;
		for (int n = all - 1; n >= 0; n--, b++) {
			oneUpdate = parameter.get(n);

			tmp = dbDao.executeUpdate(oneUpdate, mypreparestatement);

			// �����µ�ִ����䣬��Ϊִ����2000�κ�����
			if (b == 2000 && newtimes > 0) {
				try {
					 if(!c.getAutoCommit())
						 c.commit();
					 
					 } catch (SQLException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
					 }
				mypreparestatement = dbDao.getPreparedStatement(sql, c);
				newtimes--;
				b = 0;
			}
			executeResults.add(oneUpdate.toString() + "=" + tmp + "\n");
		}
		 try {
		 if(!c.getAutoCommit())
			 c.commit();
		 
		 } catch (SQLException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		return executeResults;
	}
	public ArrayList<String> updateData(int[] type ,String sql,
			ArrayList<HashMap<Integer, String>> parameter) {
		PreparedStatement mypreparestatement = dbDao.getPreparedStatement(sql, c);
		HashMap<Integer, String> oneUpdate = new HashMap<Integer, String>();
		ArrayList<String> executeResults = new ArrayList<String>();
		String tmp = null;
		if (parameter == null) {
			return null;
		}
		int b = 0;
		int all = parameter.size();
		int newtimes = all / 2000;
		for (int n = all - 1; n >= 0; n--, b++) {
			oneUpdate = parameter.get(n);

			tmp = dbDao.executeUpdate(type,oneUpdate, mypreparestatement);

			// �����µ�ִ����䣬��Ϊִ����2000�κ�����
			if (b == 2000 && newtimes > 0) {
				try {
					 if(!c.getAutoCommit())
						 c.commit();
					 
					 } catch (SQLException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
					 }
				mypreparestatement = dbDao.getPreparedStatement(sql, c);
				newtimes--;
				b = 0;
			}
			executeResults.add(oneUpdate.toString() + "=" + tmp + "\n");
		}
		 try {
			 if(!c.getAutoCommit())
				 c.commit();
			 
			 } catch (SQLException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			 }
		return executeResults;
	}
}
