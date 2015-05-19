package DbBeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import publicbeans.Tools;

public class DbPool {

    public ArrayList<Connection> ConnectionPool = new ArrayList<Connection>();
    public int MaxConnection = 30;
    public boolean[] isused = new boolean[MaxConnection]; 
    private static DbPool instance = new DbPool();
   
    private DbPool() {

    }

    private void isused_cx() {
        for (int n = MaxConnection - 1; n >= 0; n--) {
            isused[n] = false;
        }
    }

    private void setpool() {
        if (ConnectionPool.size() < MaxConnection) {
            for (int n = 0; n < MaxConnection / 5; n++) {
				// ConnectionPool.add(getOAtestConnection());
                if (ConnectionPool.size() == MaxConnection) {
                    break;
                }
            }
        }
    }

    public Connection getOneConnection() {
        for (int n = ConnectionPool.size() - 1; n >= 0; n--) {
            if (!isused[n]) {
                isused[n] = true;
                Tools.print("getconnection=" + n);
                return ConnectionPool.get(n);
            }
            if (n == 0) {
                setpool();
                n = ConnectionPool.size();
                Tools.print("setpool=" + n);
            }
        }
        return null;
    }


    public static DbPool getInstance() {
        return instance;
    }

    public ResultSet executeQuery(String sql, HashMap parameter, Connection conn) {
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            if (parameter != null) {
                for (int n = parameter.size() - 1; n >= 0; n--) {
                    pst.setString(n + 1, (String) parameter.get(String.valueOf(
                            n + 1)));
                }
            }
            rs = pst.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int executeUpdate(String sql, HashMap parameter, Connection conn) {
        PreparedStatement pst = null;
        int result = 0;
        try {
            pst = conn.prepareStatement(sql);
            if (parameter != null) {
                for (int n = parameter.size() - 1; n >= 0; n--) {
                    pst.setString(n + 1, (String) parameter.get(String.valueOf(
                            n + 1)));
                }
            }
            result = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

	public String ResultToString(ResultSet rs, String columnName) {
		try {
			while (rs.next()) {
				return rs.getString(columnName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


    public Connection release(Connection conn) {
        if (ConnectionPool.contains(conn)) {
            isused[ConnectionPool.indexOf(conn)] = false;
            Tools.print(ConnectionPool.indexOf(conn) + " has released");
        }
        return null;
    }

}
