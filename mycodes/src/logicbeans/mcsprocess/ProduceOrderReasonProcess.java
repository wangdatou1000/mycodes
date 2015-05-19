package logicbeans.mcsprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import publicbeans.LogicPublic;
import publicbeans.Tools;


public class ProduceOrderReasonProcess {
	private ArrayList<HashMap> list;
	private String SQLQUERY ;
	private final String SQLMCSQUERY = "select PK_MOID from produceorder_reason ";
	private final String tmp = " From Ct_Manage a \n"
			+ "     Left Join Ct_Manage_b b On b.Pk_Ct_Manage = a.Pk_Ct_Manage \n"
			+ " 	Left Join bd_invmandoc m On m.pk_invmandoc=b.invid\n"
			+ " 	Left Join bd_invbasdoc invb On invb.pk_invbasdoc=m.pk_invbasdoc\n"
			+ " 	Left Join ct_type t On t.pk_ct_type=a.pk_ct_type\n"
			+ " 	Left Join bd_cumandoc cm On cm.pk_cumandoc=a.custid\n"
			+ " 	Left Join bd_cubasdoc cb On cb.pk_cubasdoc=cm.pk_cubasdoc\n"
			+ " 	Left Join mm_mo mm On mm.scddh=b.def3\n"
			+ "     Where 1 = 1    And a.Dr = 0\n"
			+ "     And b.Def3 Is Not Null\n"
			+ " 	And t.typename='生产外协'\n"
			+ " 	And b.dr=0"
			+ " 	And mm.dr=0\n"
			+ " 	Group By a.ct_code,b.def3,invb.invcode,cb.custname,mm.pk_moid,invb.invname ";
	private final String SQLINDERT = "insert into produceorder_reason(CONTRACTNO,PRODUCENO,PARTNO,PARTNAME,SUPPLIER,PK_MOID)"
			+ " values(?,?,?,?,?,?)";

	public void WaixieInfo() {
	}

	public ArrayList<String> getMcsData() {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<HashMap> tmp = LogicPublic.instance.dbmcs
				.getData(SQLMCSQUERY);
		for (HashMap mp : tmp) {
			result.add(String.valueOf(mp.get(PK_MOID)));
		}
		return result;
	}

	public HashMap<String, HashMap<String, String>> getDataByPk(
			ArrayList<HashMap> list, ArrayList<String> McsData) {
		HashMap<String, HashMap<String, String>> result = new HashMap<String, HashMap<String, String>>();
		String pk_moid;
		for (HashMap mp : list) {
			pk_moid = String.valueOf(mp.get(PK_MOID));
			if (!McsData.contains(pk_moid)) {
				result.put(pk_moid, mp);
			}
		}
		return result;
	}

	public ArrayList<HashMap> getErpData(String SQL) {
		return LogicPublic.instance.dberp.getData(SQL);
	}

	public void saveData(HashMap<String, HashMap<String, String>> savedata) {
		Iterator<String> updateRang = savedata.keySet().iterator();
		HashMap<Integer, String> oneupdate = null;
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		ArrayList<String> executeResults = null;
		HashMap<String, String> onerow;
		String pk;
		while (updateRang.hasNext()) {
			pk = updateRang.next();
			onerow = savedata.get(pk);
			oneupdate = new HashMap<Integer, String>();
			oneupdate.put(6 , pk);
			oneupdate.put(1, onerow.get(CONTRACTNO));
			oneupdate.put(2, onerow.get(PRODUCENO));
			oneupdate.put(3 , onerow.get(PARTNO));
			oneupdate.put(4 , onerow.get(PARTNAME));
			oneupdate.put(5 , onerow.get(SUPPLIER));
			dbdate.add(oneupdate);
		}
		executeResults = LogicPublic.instance.dbmcs.updateData(SQLINDERT,
				dbdate);
		Tools.print(executeResults.size());
	}

	private String getSQL() {
		String SQLQUERY = "select ";
		SQLQUERY += "a.ct_code As " + CONTRACTNO + LogicPublic.instance.comma;
		SQLQUERY += "b.def3 As " + PRODUCENO + LogicPublic.instance.comma;
		SQLQUERY += "invb.invcode As " + PARTNO + LogicPublic.instance.comma;
		SQLQUERY += "invb.invname As " + PARTNAME + LogicPublic.instance.comma;
		SQLQUERY += "cb.custname As " + SUPPLIER + LogicPublic.instance.comma;
		SQLQUERY += "mm.pk_moid As " + PK_MOID;
		SQLQUERY += tmp;
		Tools.print(SQLQUERY);
		return SQLQUERY;
	}

	public void process() {
		SQLQUERY = getSQL();
		ArrayList<String> McsData=getMcsData();
		saveData(getDataByPk(getErpData(SQLQUERY),McsData));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProduceOrderReasonProcess wx = new ProduceOrderReasonProcess();
		wx.process();// TODO Auto-generated method stub

	}

	private final String PK_MOID = "PK_MOID";
	private final String CONTRACTNO = "CONTRACTNO";
	private final String PARTNO = "PARTNO";
	private final String PARTNAME = "PARTNAME";
	private final String PRODUCENO = "PRODUCENO";
	private final String SUPPLIER = "SUPPLIER";
	
}
