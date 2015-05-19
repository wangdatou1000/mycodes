package DbBeans;

public class DbBean {
	private String DBURL;
	private String DBUSER ;
	private String DBUSERPWD;
	
	public String getDBURL(){
		return this.DBURL;
	}
	public String getDBUSER(){
		return this.DBUSER;
	}
	public String getDBUSERPWD(){
		return this.DBUSERPWD;
	}
	
	public void setDBURL(String DBURL){
		 this.DBURL=DBURL;
	}
	public void setDBUSER(String DBUSER){
		 this.DBUSER=DBUSER;
	}
	public void setDBUSERPWD(String DBUSERPWD){
		 this.DBUSERPWD=DBUSERPWD;
	}
}
