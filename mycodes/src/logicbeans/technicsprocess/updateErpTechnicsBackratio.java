package logicbeans.technicsprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class updateErpTechnicsBackratio extends BaseTechnics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		updateErpTechnicsBackratio a = new updateErpTechnicsBackratio();
		a.process();

	}

	String get_km_backratio = "Select partcode as invcode,bakratio,bakmaxnum From pdm_part_info_view "
			+ " where bakratio<>0 and bakmaxnum<>0 ";
	String get_erp_partcode = "Select a.Invcode, a.Invname,b.artbfnum,b.zdbfsl "
			+ " From Bd_Invbasdoc a, Pd_Rt b "
			+ " Where a.Pk_Invbasdoc = b.Wlbmid and b.version<>'QC' and b.sfmr='Y'"
			+ " and (nvl(b.artbfnum,0)=0 or nvl(b.zdbfsl,0)=0)";
	String update_erp_backratio = " Update Pd_Rt Set artbfnum=?, zdbfsl=? "
			+ " Where Wlbmid=(Select Pk_Invbasdoc From Bd_Invbasdoc Where invcode=?) and version<>'QC'";

	public HashMap<String, String> get_ERP_info(ArrayList<HashMap> list) {
		if (list == null) {
			return null;
		}
		HashMap oneRow = null;
		HashMap<String, String> MapData = new HashMap<String, String>();
		String partcode = null;
		String version = null;
		for (int n = list.size() - 1; n >= 0; n--) {
			oneRow = list.get(n);
			partcode = String.valueOf(oneRow.get("INVCODE"));
			MapData.put(partcode, partcode);
		}
		Tools.print(MapData.size());
		return MapData;
	}

	@Override
	public void getData(HashMap MapData, HashMap oneRow, HashMap nextRow,
			String partcode) {
		String bakratio = String.valueOf(oneRow.get("BAKRATIO"));
		String bakmaxnum = String.valueOf(oneRow.get("BAKMAXNUM"));
		String parameter = bakratio + LogicPublic.instance.lable + bakmaxnum;
		MapData.put(partcode, parameter);
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
		String bakratio = null;
		String bakmaxnum = null;
		HashMap<Integer, String> oneupdate = null;
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		ArrayList<String> executeResults = null;
		String[] tmp = null;
		String s = null;
		while (updateRang.hasNext()) {
			partcode = updateRang.next();
			s = km.get(partcode);
			if (s == null) {
				// tools.print(partcode);
				continue;
			}
			tmp = s.split(LogicPublic.instance.lable);
			bakratio = tmp[0];
			bakmaxnum = tmp[1];
			oneupdate = new HashMap<Integer, String>();
			oneupdate.put(3 , partcode);
			oneupdate.put(2 , bakmaxnum);
			oneupdate.put(1 , bakratio);
			dbdate.add(oneupdate);
		}
		// tools.print(dbdate);
		executeResults = LogicPublic.instance.dberp.updateData(sql, dbdate);
		Tools.print(executeResults);
	}

}
