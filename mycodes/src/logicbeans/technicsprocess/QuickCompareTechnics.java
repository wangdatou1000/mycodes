package logicbeans.technicsprocess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class QuickCompareTechnics extends BaseTechnics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QuickCompareTechnics a = new QuickCompareTechnics();
		a.process();
	}

	public String get_km_technics_info = "Select b.partcode as invcode,b.docver as version ,b.workhourdate "
			+ " From Pdm_Part b LEFT JOIN pdm_gong_xu a ON a.docid=b.docid "
			+ "	Where b.workhour=1  "
			+ " and b.workhourdate>to_date('"
			+ Tools.getDateTime(LogicPublic.instance.FormatTime,
					Calendar.DAY_OF_YEAR, -50)
			+ "', 'yyyy-mm-dd hh24:mi:ss') order by workhourdate";
	public String get_erp_technics_info = "Select inv.invcode,nvl(a.version,'000') as version,"
			+ " nvl(a.zdy2,'N') as zdy2"
			+ "	From Bd_Invbasdoc Inv "
			+ " Left Join Pd_Rt a On Inv.Pk_Invbasdoc = a.Wlbmid "
			+ " and a.dr='0' and a.sfmr='Y' and a.version<>'QC'"
			+ " Group By inv.invcode,nvl(a.version,'000'),nvl(a.zdy2,'N')"
			+ " order by inv.invcode";

	public void compareData(ArrayList<String> erperror,
			ArrayList<String> erpshould) {
		String partcode = null;
		ArrayList<String> shouldinport = new ArrayList<String>();
		for (int n = erpshould.size() - 1; n >= 0; n--) {
			partcode = erpshould.get(n);
			if (!erperror.contains(partcode)) {
				shouldinport.add(partcode);
			}
		}
		Tools.print(shouldinport.size());
		Tools.dataToFile("erp遗漏的工艺.txt", shouldinport);

	}

	public ArrayList<String> erperror() {
		ArrayList<String> filedate = new ArrayList<String>();
		Tools.getDataFromFile("inporterror.txt", filedate);
		return filedate;
	}

	public HashMap<String, String> get_ERP_technics_info(ArrayList<HashMap> list) {
		if (list == null) {
			return null;
		}
		HashMap oneRow = null;
		HashMap<String, String> MapData = new HashMap<String, String>();
		String partcode = null;
		String version = null;
		String zdy2 = null;
		for (int n = list.size() - 1; n >= 0; n--) {
			oneRow = list.get(n);
			partcode = String.valueOf(oneRow.get("INVCODE"));
			version = String.valueOf(oneRow.get("VERSION"));
			zdy2 = String.valueOf(oneRow.get("ZDY2"));
			MapData.put(partcode, version.trim() + LogicPublic.instance.lable
					+ zdy2.trim());
		}
		return MapData;
	}

	public ArrayList<String> getCompareResult() {
		HashMap<String, String> ERPdate;
		HashMap<String, String> KMdate;
		KMdate = processData(LogicPublic.instance.dbkm
				.getData(get_km_technics_info));
		ERPdate = get_ERP_technics_info(LogicPublic.instance.dberp
				.getData(get_erp_technics_info));
		return QuickCompareDataInfo(ERPdate, KMdate);
		// compareData(erperror(), QuickCompareDataInfo(ERPdate, KMdate));
	}

	@Override
	public void getData(HashMap MapData, HashMap oneRow, HashMap nextRow,
			String partcode) {

		String time = String.valueOf(oneRow.get("WORKHOURDATE"));
		String version = String.valueOf(oneRow.get("VERSION"));
		// String parameter = partcode + LogicPublic.instance.lable + version
		// + LogicPublic.instance.lable + gxh;
		// String gzzxbm = (String) oneRow.get("CODE_GZZX");
		// String jgsj = String.valueOf(oneRow.get("JGSJ"));
		// ArrayList<HashMap<String, String>> list = null;
		// = (ArrayList<HashMap<String, String>>) MapData.get(parameter);
		// if (list == null) {
		// list = new ArrayList<HashMap<String, String>>();
		MapData.put(partcode, version.trim());
		MapData.put(partcode + "--", time + "---version:" + version);
		// }
		// HashMap<String, String> onegx = new HashMap<String, String>();
		// onegx.put("gzzxbm", gzzxbm);
		// onegx.put("jgsj", jgsj);
		// if (isrepeate.get(parameter + gzzxbm) == null && list.size() < 5) {
		// list.add(onegx);
		// isrepeate.put(parameter + gzzxbm, parameter + gzzxbm);
		// }
	}

	public ArrayList<String> getranpart() {
		HashMap<String, String> ERPdate;
		HashMap<String, String> KMdate;
		KMdate = processData(LogicPublic.instance.dbkm
				.getData(get_km_technics_info));
		ERPdate = get_ERP_technics_info(LogicPublic.instance.dberp
				.getData(get_erp_technics_info));
		return QuickCompareDataInfo(ERPdate, KMdate);
		// compareData(erperror(), QuickCompareDataInfo(ERPdate, KMdate));
	}

	public void process() {
		HashMap<String, String> ERPdate;
		HashMap<String, String> KMdate;
		KMdate = processData(LogicPublic.instance.dbkm
				.getData(get_km_technics_info));
		ERPdate = get_ERP_technics_info(LogicPublic.instance.dberp
				.getData(get_erp_technics_info));
		 QuickCompareDataInfo(ERPdate,KMdate);
		//compareData(erperror(), QuickCompareDataInfo(ERPdate, KMdate));
	}

	public ArrayList<String> QuickCompareDataInfo(HashMap<String, String> ERP,
			HashMap<String, String> KM) {
		Iterator<String> index = KM.keySet().iterator();
		String partcode = null;
		String version = null;
		String erppartcode = null;
		String erpversion = null;
		String[] temp = null;
		String tmp = null;
		ArrayList<String> kmhave_erpno = new ArrayList<String>();
		while (index.hasNext()) {
			partcode = index.next();
			if (partcode.indexOf("--") > 0) {
				continue;
			}
			version = KM.get(partcode);
			tmp = ERP.get(partcode);

			if (tmp == null) {
				// tools.print(partcode + "==null==" + KM.get(partcode + "--"));
				// kmhave_erpno.add(partcode);
			} else {
				temp = tmp.split(LogicPublic.instance.lable);
				erpversion = temp[0];
				if (!version.equals(erpversion)) {
					kmhave_erpno.add(partcode + "===="
							+ KM.get(partcode + "--"));
					Tools.print(partcode + "==notechnics=="
							+ KM.get(partcode + "--"));
					kmhave_erpno.add(partcode);
				} else if (temp[1].equals("N")) {
					Tools.print(partcode + "==noworkhour=="
							+ KM.get(partcode + "--"));
					kmhave_erpno.add(partcode);
				}
			}
		}
		Tools.print(kmhave_erpno.size());
		Tools.dataToFile("erp遗漏含错误的工艺.txt", kmhave_erpno);
		return kmhave_erpno;
	}
}
