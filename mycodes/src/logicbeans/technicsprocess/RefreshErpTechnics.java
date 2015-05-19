package logicbeans.technicsprocess;

import java.util.ArrayList;
import java.util.HashMap;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class RefreshErpTechnics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RefreshErpTechnics rfs = new RefreshErpTechnics();
		rfs.RefreshERP();
	}

	String[] refreshtype = { "updateErp", "setErpdr", "deleteErpTechnics" };

	String SqldeleteErptechinics = " Update Pd_Rt a Set a.ts='2010-01-27 77:99:88',a.sfmr='Y'"
			+ "  Where a.wlbmid=(Select pk_invbasdoc From bd_invbasdoc Where invcode=?)"
			+ "  And a.version=? And a.sfmr='N' and a.dr='0' ";
	String SqlupdateErpdr = " Update Pd_Rt_b a Set a.dr=1,a.ts='2010-01-18 99:99:88' "
			+ "  Where a.pk_rtid =(Select pk_rtid From pd_rt Where wlbmid="
			+ "  (Select pk_invbasdoc From bd_invbasdoc Where invcode=?)"
			+ " And version=? And sfmr='Y') And a.gxh=?";

	String SqlupdateErpgy = " Update Pd_Rt_b a Set a.gzzxbmid=?,a.gyms=? "
			+ "  Where a.pk_rtid =(Select pk_rtid From pd_rt Where wlbmid="
			+ "  (Select pk_invbasdoc From bd_invbasdoc Where invcode=?)"
			+ " And version=? And sfmr='Y') And a.gxh=?";

	String whichrefreshtype = "";

	public void deleteErrorTechnics(String sql, ArrayList<String> date) {
		String partcode = null;
		String version = null;
		HashMap<Integer, String> onedelete = null;
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		ArrayList<String> executeResults = null;
		String[] tmp = null;
		for (String s : date) {
			tmp = s.split(LogicPublic.instance.lable);
			partcode = tmp[0];
			version = tmp[1];
			onedelete = new HashMap<Integer, String>();
			onedelete.put(1 , partcode);
			onedelete.put(2 , version);
			dbdate.add(onedelete);
		}
		executeResults = LogicPublic.instance.dberp.updateData(sql, dbdate);
		Tools.print(executeResults);
	}

	public void deleteMoreWorkingProcedure(String sql, ArrayList<String> date) {
		String partcode = null;
		String version = null;
		String gxh = null;
		HashMap<Integer, String> onedelete = null;
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		ArrayList<String> executeResults = null;
		String[] tmp = null;
		for (String s : date) {
			tmp = s.split(LogicPublic.instance.lable);
			partcode = tmp[0];
			version = tmp[1];
			gxh = tmp[2];
			onedelete = new HashMap<Integer, String>();
			onedelete.put(1 , partcode);
			onedelete.put(2, version);
			onedelete.put(3, gxh);
			dbdate.add(onedelete);
		}
		executeResults = LogicPublic.instance.dberp.updateData(sql, dbdate);
		Tools.print(executeResults);
	}

	public HashMap<String, String> getWorkCerterMap() {
		String sql = "Select Pk_Wkid,gzzxbm From Pd_Wk where dr='0'";
		ArrayList<HashMap> workcenter = LogicPublic.instance.dberp.getData(sql);
		String workcenterID, pk_wkid;
		HashMap<String, String> workcentermap = new HashMap<String, String>();
		for (int n = workcenter.size() - 1; n >= 0; n--) {
			workcenterID = (String) workcenter.get(n).get("GZZXBM");
			pk_wkid = (String) workcenter.get(n).get("PK_WKID");
			workcentermap.put(workcenterID, pk_wkid);
		}
		// tools.print(workcentermap);
		return workcentermap;
	}

	public void RefreshERP() {
		ArrayList<String> data = new ArrayList<String>();
		if (whichrefreshtype.equals(refreshtype[2])) {
			Tools.getDataFromFile("erp与km工序数量不同(more),但包含km：", data);
			deleteMoreWorkingProcedure(SqlupdateErpdr, data);
		} else if (whichrefreshtype.equals(refreshtype[0])) {
			data = new ArrayList<String>();
			Tools.getDataFromFile("erp与km工序内容不同：", data);
			updateWorkingProcedure(SqlupdateErpgy, data);
		} else if (whichrefreshtype.equals(refreshtype[1])) {
			data = new ArrayList<String>();
			Tools.getDataFromFile("erp与km版本不同：", data);
			deleteErrorTechnics(SqldeleteErptechinics, data);
		}
	}

	public void updateWorkingProcedure(String sql, ArrayList<String> date) {
		String partcode = null;
		String version = null;
		String gxh = null;
		String workcenterID = null;
		String technicsdescribe = null;
		HashMap<Integer, String> onedelete = null;
		HashMap<String, String> workcertermap = getWorkCerterMap();
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		ArrayList<String> executeResults = null;
		String[] tmp = null;
		for (String s : date) {
			tmp = s.split(LogicPublic.instance.lable);
			partcode = tmp[0];
			version = tmp[1];
			gxh = tmp[2];
			workcenterID = workcertermap.get(tmp[3]);
			technicsdescribe = tmp[4];
			onedelete = new HashMap<Integer, String>();
			onedelete.put(3 , partcode);
			onedelete.put(4 , version);
			onedelete.put(5, gxh);
			onedelete.put(1, workcenterID);
			onedelete.put(2, technicsdescribe);
			dbdate.add(onedelete);
		}
		executeResults = LogicPublic.instance.dberp.updateData(sql, dbdate);
		Tools.print(executeResults);
	}
}
