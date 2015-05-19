package logicbeans.technicsprocess;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class getKmTechnicsData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getKmTechnicsData a = new getKmTechnicsData();
		QuickCompareTechnics rang = new QuickCompareTechnics();
		long startTime = System.currentTimeMillis();
	    a.process(rang.getCompareResult());
		//a.process();
		long endTime = System.currentTimeMillis();
		System.out.print("the last time is :" + (endTime - startTime));
	}

	public HashMap<String, String> KMdata = new updateErpTechnicsBackratio()
			.getKMbakdata();
	public HashMap TechnicsData = new HashMap();
	private final String FormatTime = "yyyy-MM-dd HH:mm:ss";
	private String PartRang = getPartRang("AL");//,AL7.821.5286,5287
	private final String orderby = " order by b.partcode,gxh";
	private final String workhour = " and b.workhour='1' ";
	private final boolean isworkhourdone = true;
	private final HashMap<String, String> workspace = LogicPublic.instance
			.getchejian_mc();
	private String time = getTime(Calendar.DAY_OF_YEAR, -50);
	private String SQL_GET_KM_TECHNICS = "select b.partcode as invcode,b.docver as version ,"
			+ " b.partname as invname,b.docpath as docpath ,b.workhour as workhour,"
			+ "(Case When Length(a.Gx_Id) = 1 Then '0' || a.Gx_Id || '0'"
			+ " Else a.Gx_Id || '0' End) As gxh,"
			+ " a.gx_content as gyms ,a.KEYGX as keygx,a.code_gzzx as code_gzzx,"
			+ " a.gb_id ,a.zbsj,a.jgsj,a.gdzqpl,b.docid ,b.wanquanwaixie"
			+ " from pdm_part b left join  pdm_gong_xu a on b.docid =a.docid "
			+ " where a.docid is not null and a.gb_id='-0'";

	public getKmTechnicsData() {
		SQL_GET_KM_TECHNICS += PartRang;
		if (isworkhourdone) {
			SQL_GET_KM_TECHNICS += workhour;
		}
		SQL_GET_KM_TECHNICS += time + orderby;
		Tools.print("工艺数据导出条件（时间段和Part范围）为：\n" + time + PartRang);
	}

	public getKmTechnicsData(String partrang, int formattime, int value) {
		PartRang = getPartRang(partrang);
		time = getTime(formattime, value);
		Tools.print("工艺数据导出条件（时间段和Part范围）为：\n" + time + PartRang);
		SQL_GET_KM_TECHNICS += PartRang;
		if (isworkhourdone) {
			SQL_GET_KM_TECHNICS += workhour;
		}
		SQL_GET_KM_TECHNICS += time + orderby;

	}

	private final String getGxjjd(String onecodeGzzx, String nextcodeGzzx) {
		if (nextcodeGzzx == null) {
			return "N";
		}
		if (onecodeGzzx.substring(0, 2).equals(nextcodeGzzx.substring(0, 2))) {
			return "N";
		} else {
			return "Y";
		}
	}

	private final String getPartRang(String partrang) {
		return " and b.partcode like '%" + partrang + "%' ";
	}

	private final HashMap getTechnicsBodyDetails(HashMap oneRow,
			HashMap nextRow, String partcode) {
		HashMap onegx = new HashMap();
		String oneGXH = null;
		String oneGZZXBM = null;
		String oneGZZXMC = null;
		String oneCODEGONGZHONG = null;
		String oneKEYGX = null;
		String onewaittime = null;
		String tmp = null;
		String gb_id = null;
		String checkmode = null;
		String onecode_gzzx = null;
		String zbsj = null;
		String jgsj = null;
		String gdzqpl = null;
		String zdy4 = null;
		String nextcode_gzzx = null;
		String gxjjd = null;
		String nextDOCID = null;
		String DOCID = null;
		onecode_gzzx = ((String) oneRow.get("CODE_GZZX")).trim();
		if (nextRow != null) {
			DOCID = String.valueOf(oneRow.get("DOCID"));
			nextDOCID = String.valueOf(nextRow.get("DOCID"));
			nextcode_gzzx = ((String) nextRow.get("CODE_GZZX")).trim();
			if (!DOCID.equals(nextDOCID)) {
				nextcode_gzzx = null;
				// tools.print(DOCID + "==" + nextDOCID);
			}

		}
		gxjjd = getGxjjd(onecode_gzzx, nextcode_gzzx);
		if (onecode_gzzx.equals("000000")) {
			return null;
		}
		oneGXH = ((String) oneRow.get("GXH")).trim();
		gb_id = (String) oneRow.get("GB_ID");
		if (gb_id.equals("-0")) {
			checkmode = "33";
			onewaittime = "4";
		} else {
		}
		oneGZZXBM = onecode_gzzx.substring(0, 2);
		oneCODEGONGZHONG = onecode_gzzx.substring(2);
		oneGZZXMC = workspace.get(oneGZZXBM);
		if (oneGZZXMC == null) {
			Tools.print(oneGZZXMC + "dqqqqdd");
			return null;
		} else {
			oneGZZXMC = oneGZZXMC.trim();
		}
		if (oneGZZXBM == null) {
			return null;
		}
		oneKEYGX = (String) oneRow.get("KEYGX");
		if (oneKEYGX.equals("1")) {
			oneKEYGX = "Y";
		} else {
			oneKEYGX = "N";
		}
		tmp = StringProcess((String) oneRow.get("GYMS"));
		double jgsjnumb = ((BigDecimal) oneRow.get("JGSJ")).doubleValue();
		double gdzqplnumb = ((BigDecimal) oneRow.get("GDZQPL")).doubleValue();
		zbsj = String.valueOf(oneRow.get("ZBSJ"));
		if (gdzqplnumb != 0) {
			zdy4 = "" + jgsjnumb * gdzqplnumb;
		} else {
			zdy4 = "" + jgsjnumb;
		}
		jgsj = String.valueOf(jgsjnumb);
		gdzqpl = String.valueOf(gdzqplnumb);
		onegx.put(XMLProcessTechnicsWay.GXH, oneGXH);
		onegx.put(XMLProcessTechnicsWay.GYMS, tmp);
		onegx.put(XMLProcessTechnicsWay.GZZXBM, oneGZZXBM);
		onegx.put(XMLProcessTechnicsWay.GZZXMC, oneGZZXMC);
		onegx.put(XMLProcessTechnicsWay.WORKTYPE, oneCODEGONGZHONG);
		onegx.put(XMLProcessTechnicsWay.PRIMARYTYPE, oneKEYGX);
		onegx.put(XMLProcessTechnicsWay.CHECKMODE, checkmode);
		// onegx.put(XMLProcessTechnicsWay.CHECKMODE, sfzx);
		onegx.put(XMLProcessTechnicsWay.sfgxjjd, gxjjd);
		onegx.put(XMLProcessTechnicsWay.ZDY1, onewaittime);
		onegx.put(XMLProcessTechnicsWay.JGSJ, jgsj);
		onegx.put(XMLProcessTechnicsWay.ZBSJ, zbsj);
		onegx.put(XMLProcessTechnicsWay.gdzqpl, gdzqpl);
		onegx.put(XMLProcessTechnicsWay.ZDY4, zdy4);
		return onegx;
	}

	private final void getTechnicsDetailsData(HashMap MapTechnicsData,
			HashMap oneRow, HashMap nextRow, String partcode) {
		HashMap onepart = (HashMap) MapTechnicsData.get(partcode);
		ArrayList onebody = null;
		HashMap onehead = null;
		HashMap onegx = null;
		if (onepart == null) {
			onepart = new HashMap();
			MapTechnicsData.put(partcode, onepart);
		} else if (onepart.size() == 0) {
			return;
		}
		onehead = (HashMap) onepart.get("head");
		if (onehead == null) {
			onehead = getTechnicsHeadDetails(oneRow, partcode);
			onepart.put("head", onehead);
			onebody = new ArrayList();
			onepart.put("body", onebody);
		}
		onebody = (ArrayList) onepart.get("body");
		onegx = getTechnicsBodyDetails(oneRow, nextRow, partcode);
		if (onegx == null) {
			MapTechnicsData.put(partcode, new HashMap());
			return;
		} else {
			onebody.add(onegx);
		}
	}

	private final HashMap getTechnicsHeadDetails(HashMap oneRow, String partcode) {
		HashMap onehead = new HashMap();
		onehead.put(XMLProcessTechnicsWay.WLBM, partcode);
		String name = (String) oneRow.get("INVNAME");

		if (name != null) {
			name = name.replaceAll("", "");
		}
		onehead.put(XMLProcessTechnicsWay.WLMC, name);
		onehead.put(XMLProcessTechnicsWay.VERSION, String.valueOf(oneRow
				.get("VERSION")));
		onehead
				.put(XMLProcessTechnicsWay.DOCNAME, oneRow.get("DOCID")
						+ ".gxk");
		onehead.put(XMLProcessTechnicsWay.DOCPATH, oneRow.get("DOCPATH"));
		String workhour = (String) oneRow.get("WORKHOUR");
		if (workhour.equals("0")) {
			workhour = "N";
		} else {
			workhour = "Y";
		}
		onehead.put(XMLProcessTechnicsWay.ZDY2, workhour);
		setTechnicsBakdata(onehead, partcode);
		String sfwqwx=(String) oneRow.get("WANQUANWAIXIE");
		//System.out.println(sfwqwx);
		onehead.put(XMLProcessTechnicsWay.SFWQWX, sfwqwx);
		return onehead;
	}

	private final String getTime(int formattime, int value) {
		String time = " and b.workhourdate>to_date('"
				+ Tools.getDateTime(FormatTime, formattime, value)
				+ "', 'yyyy-mm-dd hh24:mi:ss')"
				+ " and b.workhourdate<to_date('"
				+ Tools.getDateTime(FormatTime, Calendar.DAY_OF_YEAR, 0)
				+ "', 'yyyy-mm-dd hh24:mi:ss') ";
		return time;
	}

	public void process() {
		// tools.print(time + PartRang);
		// QuickCompareTechnics rangpart = new QuickCompareTechnics();
		getCooprateWorkProcedure c = new getCooprateWorkProcedure(time
				+ PartRang);

		TechnicsData = processTechnicsData(LogicPublic.instance.dbkm
				.getData(SQL_GET_KM_TECHNICS));
		updateDataMap(TechnicsData, c.processData(LogicPublic.instance.dbkm
				.getData(c.get_km_coopratetechnics)));
		TechnicsDataToXml(TechnicsData);
		Tools.print("总共导出了" + TechnicsData.size() + "个图号的工艺！");
	}

	public void process(ArrayList<String> rangpart) {
		getCooprateWorkProcedure c = new getCooprateWorkProcedure(time
				+ PartRang);
		TechnicsData = processTechnicsData(LogicPublic.instance.dbkm
				.getData(SQL_GET_KM_TECHNICS), rangpart);
		updateDataMap(TechnicsData, c.processData(LogicPublic.instance.dbkm
				.getData(c.get_km_coopratetechnics)));
		TechnicsDataToXml(TechnicsData);
		Tools.print("总共导出了" + TechnicsData.size() + "个图号的工艺！");
	}

	public void process(String check) {
		Tools.print(time + PartRang);
		QuickCompareTechnics rangpart = new QuickCompareTechnics();

		getCooprateWorkProcedure c = new getCooprateWorkProcedure(time
				+ PartRang);

		TechnicsData = processTechnicsData(LogicPublic.instance.dbkm
				.getData(SQL_GET_KM_TECHNICS));
		updateDataMap(TechnicsData, c.processData(LogicPublic.instance.dbkm
				.getData(c.get_km_coopratetechnics)));
		TechnicsDataToXml(TechnicsData);
		Tools.print("总共导出了" + TechnicsData.size() + "个图号的工艺！");
	}

	public HashMap processTechnicsData(ArrayList<HashMap> list) {
		if (list == null) {
			return null;
		}
		HashMap MapTechnicsData = new HashMap();
		HashMap oneRow = null;
		HashMap nextRow = null;
		String partcode = null;
		ArrayList<String> PartArrayList = null;
		// ArrayList<String> temp=new ArrayList<String>();
		// tools.getDataFromFile("erp遗漏的工艺.txt", temp);
		int MaxRow = list.size();
		for (int n = 0; n < MaxRow; n++) {
			oneRow = list.get(n);
			if (n + 1 < MaxRow) {
				nextRow = list.get(n + 1);
			}
			partcode = String.valueOf(oneRow.get("INVCODE"));
			if (partcode == null) {
				continue;
			}
			if (partcode.indexOf("/") > 3) {
				PartArrayList = LogicPublic.instance.jiexipartcode(partcode);
				if (PartArrayList == null || PartArrayList.size() > 600) {
					continue;
				}
			} else {
				PartArrayList = new ArrayList<String>();
				PartArrayList.add(partcode);
			}

			for (int m = PartArrayList.size() - 1; m >= 0; m--) {
				partcode = PartArrayList.get(m);
				// if(!temp.contains(partcode))continue;
				getTechnicsDetailsData(MapTechnicsData, oneRow, nextRow,
						partcode);
			}
		}
		return MapTechnicsData;
	}

	public HashMap processTechnicsData(ArrayList<HashMap> list,
			ArrayList<String> rangpart) {
		if (list == null) {
			return null;
		}
		HashMap MapTechnicsData = new HashMap();
		HashMap oneRow = null;
		HashMap nextRow = null;
		String partcode = null;
		ArrayList<String> PartArrayList = null;
		int MaxRow = list.size();
		for (int n = 0; n < MaxRow; n++) {
			oneRow = list.get(n);
			if (n + 1 < MaxRow) {
				nextRow = list.get(n + 1);
			}
			partcode = String.valueOf(oneRow.get("INVCODE"));
			if (partcode == null) {
				continue;
			}
			if (partcode.indexOf("/") > 3) {
				PartArrayList = LogicPublic.instance.jiexipartcode(partcode);
				if (PartArrayList == null || PartArrayList.size() > 500) {
					continue;
				}
			} else {
				PartArrayList = new ArrayList<String>();
				PartArrayList.add(partcode);
			}

			for (int m = PartArrayList.size() - 1; m >= 0; m--) {
				partcode = PartArrayList.get(m);
				if (!rangpart.contains(partcode)) {
					continue;
				}
				getTechnicsDetailsData(MapTechnicsData, oneRow, nextRow,
						partcode);
			}
		}
		return MapTechnicsData;
	}

	private void setTechnicsBakdata(HashMap onehead, String partcode) {
		String[] tmp = null;
		String s = null;
		String bakratio = null;
		String bakmaxnum = null;
		s = KMdata.get(partcode);
		if (s == null) {
			bakratio = "0";
			bakmaxnum = "0";
		} else {
			tmp = s.split(LogicPublic.instance.lable);
			bakratio = tmp[0];
			bakmaxnum = tmp[1];
		}
		onehead.put(XMLProcessTechnicsWay.ARTBFNUM, bakratio);
		onehead.put(XMLProcessTechnicsWay.ZDBFSL, bakmaxnum);
	}

	private final String StringProcess(String s) {
		String tmp = s;
		if (tmp != null) {
			tmp = tmp.replaceAll("", "");
			tmp = tmp.replaceAll("", "");
			tmp = tmp.replaceAll("", "");
			tmp = tmp.replaceAll("", "");
			if (tmp.getBytes().length > 145) {
				tmp = tmp.substring(0, 70);
			}
		}
		return tmp;
	}

	public void TechnicsDataToXml(HashMap Datamap) {
		String FormatTime = "yyyy-MM-dd-HH.mm.ss";
		String filename = Tools.getDateTime(FormatTime, Calendar.HOUR, 0);
		filename = "工艺路线" + filename + "-";
		Iterator itor = Datamap.keySet().iterator();
		String partcode = null;
		HashMap TechnicsDetailsData = null;
		HashMap head = null;
		ArrayList body = null;
		XMLProcessTechnicsWay XMLInstance = new XMLProcessTechnicsWay();
		int Maxcount = 5000, partnumber = 0, filenumber = 0;
		while (itor.hasNext()) {
			partcode = (String) itor.next();
			TechnicsDetailsData = (HashMap) Datamap.get(partcode);
			if (TechnicsDetailsData == null || TechnicsDetailsData.size() == 0) {
				Tools.print(partcode + ":no gy");
				continue;
			}
			// System.out.println(partcode + "," + version_km + "--" + a++);
			head = (HashMap) TechnicsDetailsData.get("head");
			body = (ArrayList) TechnicsDetailsData.get("body");
			XMLInstance.addBill();
			XMLInstance.processBillHead(head);
			XMLInstance.processBillBody(body);
			partnumber += 1;
			Tools.print(partnumber + "\tok");
			if (partnumber == Maxcount) {
				filenumber++;
				String newFileName = filename + filenumber
						+ LogicPublic.instance.XmlExtendName;
				XMLInstance.outputFile(newFileName);
				Tools.print("ok,print one file" + partnumber);
				partnumber = 0;
				XMLInstance = new XMLProcessTechnicsWay();
			}
		}
		if (partnumber > 0) {
			filenumber++;
			String newFileName = filename + filenumber
					+ LogicPublic.instance.XmlExtendName;
			XMLInstance.outputFile(newFileName);
			Tools.print("工艺数据导出的文件为：" + newFileName);
		}
	}

	public void updateDataMap(HashMap Datamap, HashMap SetingMap) {
		Iterator key = SetingMap.keySet().iterator();
		String partcode = null;
		String[] temp = null;
		String gxh = null;
		String onegx = null;
		String gzzxpkid = null;
		String jgsj = null;
		ArrayList<HashMap<String, String>> list = null;
		HashMap TechnicsData = null;
		HashMap onegxdate = null;
		while (key.hasNext()) {
			onegx = (String) key.next();
			list = (ArrayList<HashMap<String, String>>) SetingMap.get(onegx);
			temp = onegx.split(LogicPublic.instance.lable);
			partcode = temp[0];
			gxh = temp[2];
			TechnicsData = (HashMap) Datamap.get(partcode);
			if (TechnicsData == null || TechnicsData.size() == 0) {
				continue;
			}
			ArrayList body = (ArrayList) TechnicsData.get("body");
			if (body == null) {
				Tools.print(TechnicsData);
				continue;
			}
			HashMap<String, String> onecooprategx = null;
			int nmb = 11;
			for (int n = body.size() - 1; n >= 0; n--) {
				onegxdate = (HashMap) body.get(n);
				if (onegxdate.get(XMLProcessTechnicsWay.GXH).equals(gxh)) {
					for (int m = 0; m < list.size(); m++) {
						onecooprategx = list.get(m);
						jgsj = onecooprategx.get("jgsj");
						gzzxpkid = onecooprategx.get("gzzxbm");
						onegxdate.put("zdy" + nmb, gzzxpkid);
						nmb++;
						onegxdate.put("zdy" + nmb, jgsj);
						nmb++;
						// tools.print(list);
					}
				}
			}
		}
	}
}
