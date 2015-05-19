package logicbeans.technicsprocess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import DbBeans.DbInfo;
import DbBeans.DbManager;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class checkKmTechnics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		checkKmTechnics c = new checkKmTechnics();
		c.process();
	}

	private final DbManager kmdb = new DbManager(DbInfo.dbinfo.KM);
	private final String SQLgetKmData = "Select Partcode, partname,asmcode,docver,prodname From Pdm_Part b where 1=1 "
			+ " and b.workhourdate>to_date('"
			+ Tools.getDateTime(LogicPublic.instance.FormatTime,
					Calendar.DAY_OF_YEAR, -200) + "', 'yyyy-mm-dd hh24:mi:ss')";

	public void process() {
		HashMap<String, ArrayList<String>> data = processPartcode(kmdb
				.getData(SQLgetKmData));
		Iterator<String> index = data.keySet().iterator();
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<String> one = null;
		String partcode = null;
		int numb = 0;
		while (index.hasNext()) {
			partcode = index.next();
			one = data.get(partcode);
			numb = one.size();
			if (numb > 1) {
				for (int n = numb - 1; n >= 0; n--) {
					results.add(one.get(n) + LogicPublic.instance.comma + numb);
				}
			}
		}
		results.add("总条数为：" + results.size());
		Tools.dataToFile("工艺路线重复的图号.csv", results);
	}

	public HashMap<String, ArrayList<String>> processPartcode(
			ArrayList<HashMap> list) {
		if (list == null) {
			return null;
		}
		int numb = 0;
		StringBuilder tmp = null;
		String info = null;
		HashMap<String, ArrayList<String>> MapData = new HashMap<String, ArrayList<String>>();
		ArrayList<String> onepart = new ArrayList<String>();
		HashMap oneRow = null;
		String partcode = null;
		ArrayList<String> part = null;
		for (int n = list.size() - 1; n >= 0; n--) {
			oneRow = list.get(n);
			partcode = String.valueOf(oneRow.get("PARTCODE"));
			tmp = new StringBuilder();
			tmp.append(partcode);
			tmp.append(LogicPublic.instance.comma);
			tmp.append(String.valueOf(oneRow.get("PARTNAME")));
			tmp.append(LogicPublic.instance.comma);
			tmp.append(String.valueOf(oneRow.get("ASMCODE")));
			tmp.append(LogicPublic.instance.comma);
			tmp.append(String.valueOf(oneRow.get("PRODNAME")));
			info = tmp.toString();
			if (partcode == null) {
				continue;
			}
			if (partcode.indexOf("/") > 3) {
				part = LogicPublic.instance.jiexipartcode(partcode);
				if (part == null || part.size() > 500) {
					continue;
				}
				for (int m = part.size() - 1; m >= 0; m--) {
					partcode = part.get(m);
					if (!MapData.containsKey(partcode)) {
						MapData.put(partcode, new ArrayList<String>());
					}
					onepart = MapData.get(partcode);
					onepart.add(info);
				}
			} else {
				if (!MapData.containsKey(partcode)) {
					MapData.put(partcode, new ArrayList<String>());
				}
				onepart = MapData.get(partcode);
				onepart.add(info);
			}
		}
		Tools.print(MapData.size());
		return MapData;
	}
}
