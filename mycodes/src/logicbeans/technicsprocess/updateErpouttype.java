package logicbeans.technicsprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class updateErpouttype extends BaseTechnics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		updateErpouttype a = new updateErpouttype();
		a.process();

	}

	String get_km_backratio = "Select partcode as invcode,parttype ,assisttype,assist2type From pdm_part_info_view "
			+ " where partcode like 'AL%' ";
	String get_erp_partcode = "Select a.def2,b.invcode From bd_produce a "
			+ " Left Join Bd_Invbasdoc b On b.pk_invbasdoc=a.pk_invbasdoc And b.dr=0 and b.invcode like 'AL%'"
			+ " Where a.dr=0 and a.pk_invbasdoc is not null And b.invcode Is Not Null and b.invcode like 'AL%'";
	String update_erp_backratio = " Update bd_produce Set def2=?"
			+ " Where Pk_Invbasdoc=(Select Pk_Invbasdoc From Bd_Invbasdoc Where invcode=? and dr=0)";

	public HashMap<String, String> get_ERP_info(ArrayList<HashMap> list) {
		if (list == null) {
			return null;
		}
		HashMap oneRow = null;
		HashMap<String, String> MapData = new HashMap<String, String>();
		String partcode = null;
		String outtype = null;
		for (int n = list.size() - 1; n >= 0; n--) {
			oneRow = list.get(n);
			partcode = String.valueOf(oneRow.get("INVCODE"));
			outtype = String.valueOf(oneRow.get("DEF2"));
			MapData.put(partcode, outtype);
		}
		Tools.print(MapData.size());
		return MapData;
	}

	@Override
	public void getData(HashMap MapData, HashMap oneRow, HashMap nextRow,
			String partcode) {
		String parttype = String.valueOf(oneRow.get("PARTTYPE"));
		String assisttype = String.valueOf(oneRow.get("ASSISTTYPE"));
		String assist2type = String.valueOf(oneRow.get("ASSIST2TYPE"));
		String parameter = parttype + assisttype + assist2type;
		MapData.put(partcode, processpm(parameter));
		Tools.print(partcode + "\t" + processpm(parameter));
	}

	public String processpm(String parameter) {
		if (parameter.equals("000")) {
			return "0";
		} else if (parameter.equals("111")|| parameter.equals("001")) {
			return "1";
		} else if (parameter.equals("122") || parameter.equals("132")|| parameter.equals("002")) {
			return "2";
		} else
			return null;

	}

	public void process() {
		HashMap<String, String> ERPdate;
		HashMap<String, String> KMdate;
		KMdate = processData(LogicPublic.instance.dbkm
				.getData(get_km_backratio));
		ERPdate = get_ERP_info(LogicPublic.instance.dberp
				.getData(get_erp_partcode));
		update(update_erp_backratio, ERPdate, KMdate);
	}

	public HashMap<String, String> getKMbakdata() {
		HashMap<String, String> KMdata;
		KMdata = processData(LogicPublic.instance.dbkm
				.getData(get_km_backratio));
		return KMdata;
	}

	public void update(String sql, HashMap<String, String> erp,
			HashMap<String, String> km) {
		Iterator<String> updateRang = erp.keySet().iterator();
		String partcode = null;
		String version = null;
		String sfwx = null;
		HashMap<Integer, String> oneupdate = null;
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		ArrayList<String> executeResults = null;
		String[] tmp = null;
		String s = null;
		while (updateRang.hasNext()) {
			partcode = updateRang.next();
			s = km.get(partcode);
			if (s == null) {
				Tools.print(partcode+"null");
				continue;
			} else if (s.equals(erp.get(partcode))) {
				Tools.print(partcode+"same");
				continue;
			}

			// tmp = s.split(LogicPublic.instance.lable);
			// version = tmp[0];
			// sfwx = tmp[1];
			oneupdate = new HashMap<Integer, String>();
			oneupdate.put(2 , partcode);
			// oneupdate.put(3 + "", version);
			oneupdate.put(1 , s);
			dbdate.add(oneupdate);
		}
		 Tools.print(dbdate.size());
		executeResults = LogicPublic.instance.dberp.updateData(sql, dbdate);
		Tools.print(executeResults);
	}

}
