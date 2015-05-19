package logicbeans.technicsprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class CompareTechnics {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CompareTechnics instance = new CompareTechnics();
		instance.process();
	}

	String[] comparetype = { "erphave_kmno", "versionisdf", "gxssameisdf",
			"moreworkingprocedure", "onlystatistical" };

	String erppartrang = " like 'AL%.%.%'";

	String kmpartrang = " like 'AL%.%.%'";

	String Sql = "Select e.Partcode as invcode, e.Docver as version, t.Docid, Count(t.Gb_Id) As numb"
			+ "  From Pdm_Part e"
			+ " Left Join Pdm_Gong_Xu t On t.Docid = e.Docid And t.Gb_Id = '-0'"
			+ " Where t.Docid Is Not Null "
			+ " Group By e.Partcode, e.Docver, t.Docid"
			+ " order by e.partcode";

	String SqlErp = "Select inv.invcode,a.version,Count(rt.Pk_Rtid) as numb"
			+ "	From Pd_Rt a"
			+ " Left Join Pd_Rt_b Rt On Rt.Pk_Rtid = a.pk_rtid And rt.dr='0'"
			+ " Left Join Bd_Invbasdoc Inv On Inv.Pk_Invbasdoc = a.Wlbmid"
			+ " Where inv.invcode Is Not Null And a.dr='0' "
			+ " And a.sfmr='Y' and a.version<>'QC' Group By inv.invcode,a.version"
			+ " order by inv.invcode";

	String SqlErpdails = "Select inv.invcode,a.version,rt.gxh, inv.invname,rt.gyms ,"
			+ " (Select gzzxbm From Pd_Wk  Where Pk_Wkid=gzzxbmid) As gzzxbm"
			+ "  From Pd_Rt a Left Join Pd_Rt_b Rt On Rt.Pk_Rtid = a.pk_rtid And rt.dr='0'"
			+ "  Left Join Bd_Invbasdoc Inv On Inv.Pk_Invbasdoc = a.Wlbmid"
			+ " Where inv.invcode Is Not Null And a.dr='0'  And a.sfmr='Y'"
			+ " and inv.invcode " + erppartrang + " and a.version<>'QC'";

	String SqlKmdails = "Select e.Partcode as invcode, e.Docver as version, "
			+ "  t.Docid,t.gx_id as gxh,t.code_gzzx as gzzxbm,"
			+ "  e.partname as invname,t.gx_content as gyms"
			+ "  From Pdm_Part e"
			+ "  Left Join Pdm_Gong_Xu t On t.Docid = e.Docid and t.Gb_Id = '-0'"
			+ " Where t.Docid Is Not Null and e.partcode " + kmpartrang;

	String whichcompare = "";

	public CompareTechnics() {
		// TODO Auto-generated constructor stub
	}

	public void Compare(HashMap<String, String> erp, HashMap<String, String> km) {
		String partcode = null;
		String erpversion = null;
		String erpnumb = null;
		String tmp = null;
		String[] erptmp = null;
		String[] kmtmp = null;
		Iterator<String> index = erp.keySet().iterator();
		ArrayList<String> erphave_kmno = new ArrayList<String>();
		ArrayList<String> versionisdf = new ArrayList<String>();
		ArrayList<String> gxsiddf = new ArrayList<String>();
		ArrayList<String> same = new ArrayList<String>();
		while (index.hasNext()) {
			partcode = index.next();
			tmp = erp.get(partcode);
			erptmp = tmp.split(LogicPublic.instance.comma);
			erpversion = erptmp[1];
			erpnumb = erptmp[2];
			if (!km.containsKey(partcode)) {
				erphave_kmno.add(tmp);
				continue;
			}
			kmtmp = km.get(partcode).split(LogicPublic.instance.comma);
			if (!erpversion.equals(kmtmp[1])) {
				versionisdf.add(tmp + LogicPublic.instance.comma + kmtmp[1]);
				continue;
			}
			if (!erpnumb.equals(kmtmp[2])) {
				gxsiddf.add(tmp + LogicPublic.instance.comma + kmtmp[2]);
				continue;
			}
			same.add(tmp);
		}
		Tools.print("erp有而km无：" + erphave_kmno.size());
		Tools.print("erp与km版本不同：" + versionisdf.size());
		Tools.print("erp与km工序数量不同：" + gxsiddf.size());
		Tools.print("erp与km工序数量相同：" + same.size());
		Tools.dataToFile("erp有而km无：", erphave_kmno);
		Tools.dataToFile("erp与km版本不同：", versionisdf);
		Tools.dataToFile("erp与km工序数量不同：", gxsiddf);
	}

	public ArrayList<String> CompareTetailsTechnics(
			HashMap<String, ArrayList<String>> erp,
			HashMap<String, ArrayList<String>> km) {

		String partcode = null;
		int erpnumb = 0;
		int kmnumb = 0;
		String tmperp = null;
		String tmpkm = null;
		String[] erptmp = null;
		String[] kmtmp = null;

		ArrayList<String> onegyerp = null;
		ArrayList<String> onegykm = null;

		ArrayList<String> erphave_kmno = new ArrayList<String>();
		ArrayList<String> versionisdf = new ArrayList<String>();
		ArrayList<String> gxsiddf = new ArrayList<String>();
		ArrayList<String> gxsmore = new ArrayList<String>();
		ArrayList<String> gxsless = new ArrayList<String>();
		ArrayList<String> gxssame = new ArrayList<String>();
		ArrayList<String> moreworkingprocedure = new ArrayList<String>();
		ArrayList<String> gxssameisdf = new ArrayList<String>();

		Iterator<String> index = erp.keySet().iterator();

		while (index.hasNext()) {
			partcode = index.next();
			onegyerp = erp.get(partcode);
			erpnumb = onegyerp.size();
			tmperp = onegyerp.get(0);
			erptmp = tmperp.split(LogicPublic.instance.lable);

			if (!km.containsKey(partcode)) {
				erphave_kmno.add(tmperp);
				continue;
			}

			onegykm = km.get(partcode);
			tmpkm = onegykm.get(0);
			kmtmp = tmpkm.split(LogicPublic.instance.lable);

			// 如果km中没有该图号说明km中没有该图号的工艺，记录并比对下一个图号
			if (!kmtmp[1].equals(erptmp[1])) {
				versionisdf.add(tmperp + LogicPublic.instance.comma + kmtmp[1]);
				continue;
			}

			kmnumb = onegykm.size();
			if (erpnumb > kmnumb) {
				gxsmore.add(partcode + LogicPublic.instance.comma + erptmp[1]
						+ LogicPublic.instance.comma + erpnumb
						+ LogicPublic.instance.comma + kmnumb);
				moreworkingprocedure.addAll(CompareWorkingProcedure(onegyerp,
						onegykm).get(1));
				continue;
			} else if (erpnumb < kmnumb) {
				gxsless.add(partcode + LogicPublic.instance.comma + erptmp[1]
						+ LogicPublic.instance.comma + erpnumb
						+ LogicPublic.instance.comma + kmnumb);
				continue;
			}
			gxssame.add(partcode);
			gxssameisdf.addAll(CompareWorkingProcedure(onegyerp, onegykm)
					.get(0));
		}

		Tools.print("erp有而km无：" + erphave_kmno.size());
		Tools.print("erp与km版本不同：" + versionisdf.size());
		Tools.print("erp与km工序数量不同(more)：" + gxsmore.size());
		Tools.print("erp与km工序数量不同(less)：" + gxsless.size());
		Tools.print("erp与km工序数量不同(more),但包含km：" + moreworkingprocedure.size());
		Tools.print("erp与km工序数量相同：" + gxssame.size());
		Tools.print("erp与km工序内容不同：" + gxssameisdf.size());

		Tools.dataToFile("erp有而km无：", erphave_kmno);
		Tools.dataToFile("erp与km版本不同：", versionisdf);
		Tools.dataToFile("erp与km工序数量不同(more)：", gxsmore);
		Tools.dataToFile("erp与km工序数量不同(more),但包含km：", moreworkingprocedure);
		Tools.dataToFile("erp与km工序数量不同(less)：", gxsless);
		Tools.dataToFile("erp与km工序数量相同：", gxssame);
		Tools.dataToFile("erp与km工序内容不同：", gxssameisdf);

		if (whichcompare.equals("erphave_kmno")) {
			return erphave_kmno;
		} else if (whichcompare.equals("versionisdf")) {
			return versionisdf;
		} else if (whichcompare.equals("gxssame")) {
			return gxssame;
		} else if (whichcompare.equals("gxsiddf")) {
			return gxsiddf;
		} else if (whichcompare.equals("gxssameisdf")) {
			return gxssameisdf;
		} else if (whichcompare.equals("moreworkingprocedure")) {
			return moreworkingprocedure;
		}
		return null;

	}

	public ArrayList<ArrayList<String>> CompareWorkingProcedure(
			ArrayList<String> onegyerp, ArrayList<String> onegykm) {
		ArrayList<String> gxssameisdf = new ArrayList<String>();
		ArrayList<String> moreworkingprocedure = new ArrayList<String>();
		ArrayList<ArrayList<String>> CompareResults = new ArrayList<ArrayList<String>>();
		int erpnumb = onegyerp.size();
		int kmnumb = onegykm.size();
		int rightnumb = 0;
		boolean isok = false;
		String tmperp, tmpkm;
		for (int m = erpnumb - 1; m >= 0; m--) {
			tmperp = onegyerp.get(m);
			if (erpnumb <= kmnumb) {
				tmpkm = onegykm.get(m);
			} else {
				tmpkm = "";
			}
			isok = false;
			if (!tmperp.equals(tmpkm)) {
				for (int e = kmnumb - 1; e >= 0; e--) {
					isok = false;
					tmpkm = onegykm.get(e);
					if (tmperp.equals(tmpkm)) {
						rightnumb++;
						isok = true;
						break;
					}
					if (tmperp.split(LogicPublic.instance.lable)[2]
							.equals(tmpkm.split(LogicPublic.instance.lable)[2])) {
						rightnumb++;
						isok = true;
						if (!tmpkm.split(LogicPublic.instance.lable)[3]
								.equals("000000")
								&& erpnumb == kmnumb) {
							gxssameisdf.add(tmpkm + LogicPublic.instance.lable
									+ tmperp);
						}
						break;
					}
				}
				if (!isok && erpnumb > kmnumb) {
					moreworkingprocedure.add(tmperp);
				}
			} else {
				rightnumb++;
			}

		}
		if (erpnumb > rightnumb && erpnumb == kmnumb) {
			Tools.print(erpnumb + "--" + rightnumb);
			Tools.print(onegyerp);
			Tools.print(onegykm);
		} else if (erpnumb < rightnumb && erpnumb == kmnumb) {
			Tools.print(erpnumb + "===" + rightnumb);
			Tools.print(onegyerp);
			Tools.print(onegykm);
		}

		if (erpnumb > kmnumb && rightnumb == kmnumb) {
		} else if (moreworkingprocedure.size() != 0) {
			moreworkingprocedure = new ArrayList<String>();
		}
		CompareResults.add(gxssameisdf);
		CompareResults.add(moreworkingprocedure);
		return CompareResults;
	}

	public void process() {
		// // Compare(processData(dberp.getData(SqlErp)), processData(dbkm
		// // .getData(Sql)));

		CompareTetailsTechnics(processTechnicsData(LogicPublic.instance.dberp
				.getData(SqlErpdails)),
				processTechnicsData(LogicPublic.instance.dbkm
						.getData(SqlKmdails)));
	}

	public HashMap processData(ArrayList<HashMap> list) {
		if (list == null) {
			return null;
		}
		HashMap<String, String> MapData = new HashMap<String, String>();
		HashMap oneRow = null;
		String partcode = null;
		String version = null;
		String numb = null;
		ArrayList<String> part = null;

		for (int n = list.size() - 1; n >= 0; n--) {
			oneRow = list.get(n);
			partcode = String.valueOf(oneRow.get("INVCODE"));
			version = String.valueOf(oneRow.get("VERSION"));
			numb = String.valueOf(oneRow.get("NUMB"));
			Tools.print(oneRow);
			if (partcode == null) {
				continue;
			}
			if (partcode.indexOf("/") > 3) {
				part = LogicPublic.instance.jiexipartcode(partcode);
				if (part == null) {
					continue;
				}
				for (int m = part.size() - 1; m >= 0; m--) {
					partcode = part.get(m);
					MapData.put(partcode, partcode + LogicPublic.instance.comma
							+ version + LogicPublic.instance.comma + numb);
				}
			} else {
				MapData.put(partcode, partcode + LogicPublic.instance.comma
						+ version + LogicPublic.instance.comma + numb);
			}

		}
		Tools.print(MapData.size());
		return MapData;
	}

	public HashMap processTechnicsData(ArrayList<HashMap> list) {
		if (list == null) {
			return null;
		}
		boolean iskm = false;
		HashMap<String, ArrayList<String>> MapData = new HashMap<String, ArrayList<String>>();
		HashMap oneRow = null;
		String partcode = null;
		String version = null;
		String gxh = null;
		String gzzxbm = null;
		String gyms = null;
		String tmp = null;
		ArrayList<String> part = null;
		ArrayList<String> onegy = null;
		for (int n = list.size() - 1; n >= 0; n--) {
			oneRow = list.get(n);
			partcode = String.valueOf(oneRow.get("INVCODE"));
			version = String.valueOf(oneRow.get("VERSION"));
			gxh = String.valueOf(oneRow.get("GXH"));
			if (gxh.length() != 3) {
				iskm = true;
				gxh = LogicPublic.instance.process_gx_id(gxh);
			} else {
				iskm = false;
			}
			gzzxbm = String.valueOf(oneRow.get("GZZXBM"));
			tmp = String.valueOf(oneRow.get("GYMS"));
			if (tmp != null) {
				tmp = tmp.trim();
				tmp = tmp.replaceAll("", "");
				tmp = tmp.replaceAll("", "");
				tmp = tmp.replaceAll("", "");
				tmp = tmp.replaceAll("", "");
				if (tmp.getBytes().length > 145) {
					tmp = tmp.substring(0, 70);
				}
			}
			gyms = tmp;
			// tools.print(oneRow);
			if (partcode == null) {
				continue;
			}
			if (partcode.indexOf("/") > 3 && iskm) {
				part = LogicPublic.instance.jiexipartcode(partcode);
				if (part == null || part.size() > 500) {
					continue;
				}
				for (int m = part.size() - 1; m >= 0; m--) {
					partcode = part.get(m);
					if (!MapData.containsKey(partcode)) {
						MapData.put(partcode, new ArrayList<String>());
					}
					onegy = MapData.get(partcode);
					onegy.add(partcode + LogicPublic.instance.lable + version
							+ LogicPublic.instance.lable + gxh
							+ LogicPublic.instance.lable + gzzxbm
							+ LogicPublic.instance.lable + gyms);
				}
			} else {
				if (!MapData.containsKey(partcode)) {
					MapData.put(partcode, new ArrayList<String>());
				}
				onegy = MapData.get(partcode);
				onegy.add(partcode + LogicPublic.instance.lable + version
						+ LogicPublic.instance.lable + gxh
						+ LogicPublic.instance.lable + gzzxbm
						+ LogicPublic.instance.lable + gyms);
			}
		}
		Tools.print(MapData.size());
		return MapData;
	}

}
