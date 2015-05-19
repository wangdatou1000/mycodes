package logicbeans.technicsprocess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class getPartMaterialData extends BaseTechnics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// getPartMaterialData a = new getPartMaterialData();
		getPartMaterialData a = new getPartMaterialData("AL",
				Calendar.HOUR_OF_DAY, -2400);
		a.process("ddd");
	}

	private final String FormatTime = "yyyy-MM-dd HH:mm:ss";

	public String get_km_material_info = "Select b.partcode as invcode,b.docver as version"
			+ ",b.COUNT_MATERIAL,b.partname as invname ,b.code_material "
			+ " From Pdm_Part b"
			+ "	Where (b.partcode like 'AL7.%' or b.partcode like 'AL8.%' or b.partcode like 'ALK7.%' "
			+ "  or b.partcode like 'ALK8.%') "
			+ " and b.code_material is not null ";

	public String get_erp_material_info = "select a.INVCODE as INVCODE, a.INVNAME as INVNAME ,"
			+ " nvl(b.version,'000') as version"
			+ " from BD_INVBASDOC a"
			+ " left join  BD_BOM b on a.PK_INVBASDOC = b.WLBMID "
			+ " where (b.WLBMID is null or ( b.WLBMID is not null and b.sfmr='Y'))";

	public getPartMaterialData() {
		// TODO Auto-generated constructor stub
	}

	public getPartMaterialData(String partrang, int formattime, int value) {
		String PartRang = getPartRang(partrang);
		String time = getTime(formattime, value);
		get_km_material_info += PartRang + time;
		Tools.print(get_km_material_info);
	}

	public void convert_materialData_to_xml(ArrayList material_data_xml) {
		String FormatTime = "yyyy-MM-dd-HH.mm.ss";
		String filename = Tools.getDateTime(FormatTime, Calendar.HOUR, 0);
		filename = "BOM" + filename + "-";
		XMLProcessBOM_ycl XMLInstance = null;
		HashMap Onepart = null;
		ArrayList oneBody = null;
		HashMap OneHead = null;
		int Maxcount = 5000, numberfile = 0, partnumber = 0;
		XMLInstance = new XMLProcessBOM_ycl();
		for (int n = material_data_xml.size() - 1; n >= 0; n--) {
			Onepart = (HashMap) material_data_xml.get(n);
			OneHead = (HashMap) Onepart.get("head");
			oneBody = (ArrayList) Onepart.get("body");
			XMLInstance.addBill(OneHead, oneBody);
			if (++partnumber == Maxcount) {
				numberfile++;
				String newFileName = filename + numberfile
						+ LogicPublic.instance.XmlExtendName;
				XMLInstance.outputFile(newFileName);
				Tools.print(newFileName);
				partnumber = 0;
				XMLInstance = new XMLProcessBOM_ycl();
				// break;
			}
		}
		if (partnumber > 0) {
			numberfile++;
			String newFileName = filename + numberfile
					+ LogicPublic.instance.XmlExtendName;
			XMLInstance.outputFile(newFileName);
			Tools.print(newFileName);
		}

	}

	public ArrayList<String> convertlist(HashMap<String, HashMap> km) {
		Iterator<String> index = km.keySet().iterator();
		String kmpartcode = null;
		ArrayList<String> kmlist = new ArrayList<String>();
		while (index.hasNext()) {
			kmpartcode = index.next();
			kmlist.add(kmpartcode);
		}
		return kmlist;
	}

	public HashMap<String, String> get_ERP_partmaterial_info(
			ArrayList<HashMap> list) {
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
			version = String.valueOf(oneRow.get("VERSION"));
			MapData.put(partcode, version);
		}
		return MapData;
	}

	@Override
	public void getData(HashMap MapData, HashMap oneRow, HashMap nextRow,
			String partcode) {
		HashMap material_data = null;
		String code_material = (String) oneRow.get("CODE_MATERIAL");
		String version = String.valueOf(oneRow.get("VERSION"));
		String count_material = String.valueOf(oneRow.get("COUNT_MATERIAL"));
		String invname = (String) oneRow.get("INVNAME");
		material_data = (HashMap) MapData.get(partcode);
		if (material_data == null) {
			material_data = new HashMap();
			MapData.put(partcode, material_data);
		}
		material_data.put("CODE_MATERIAL", code_material);
		material_data.put("VERSION", version);
		material_data.put("COUNT_MATERIAL", count_material);
		material_data.put("INVNAME", invname);
	}

	private final String getPartRang(String partrang) {
		return " and b.partcode like '%" + partrang + "%' ";
	}

	private final String getTime(int formattime, int value) {
		String time = " and b.workhourdate>to_date('"
				+ Tools.getDateTime(FormatTime, formattime, value)
				+ "', 'yyyy-mm-dd hh24:mi:ss')"
				+ " and b.workhourdate<to_date('"
				+ Tools.getDateTime(FormatTime, Calendar.DAY_OF_YEAR, 1)
				+ "', 'yyyy-mm-dd hh24:mi:ss') ";
		return time;
	}

	public void process() {
		HashMap<String, String> ERPdate;
		HashMap<String, HashMap> KMdate;
		KMdate = processData(LogicPublic.instance.dbkm
				.getData(get_km_material_info));
	    //ERPdate = get_ERP_partmaterial_info(LogicPublic.instance.dberp
		    // .getData(get_erp_material_info));
		ArrayList material_data_xml = processData(convertlist(KMdate), KMdate);
		convert_materialData_to_xml(material_data_xml);
	}

	public void process(String compare) {
		HashMap<String, String> ERPdate;
		HashMap<String, HashMap> KMdate;
		KMdate = processData(LogicPublic.instance.dbkm
				.getData(get_km_material_info));
		ERPdate = get_ERP_partmaterial_info(LogicPublic.instance.dberp
				.getData(get_erp_material_info));
		ArrayList material_data_xml = processData(QuickCompareDataInfo(ERPdate,
				KMdate), KMdate);
		convert_materialData_to_xml(material_data_xml);
	}

	public ArrayList processData(ArrayList<String> kmanderpisdif,
			HashMap<String, HashMap> KM) {
		String partcode = null;
		HashMap material_body = null;
		HashMap material_head = null;
		HashMap material_data = null;
		ArrayList onebody = null;
		ArrayList material_data_xml = new ArrayList();
		HashMap km_material_data = null;
		String code_material = null;
		String version = null;
		String count_material = null;
		String invname = null;
		for (int n = kmanderpisdif.size() - 1; n >= 0; n--) {
			partcode = kmanderpisdif.get(n);
			km_material_data = KM.get(partcode);

			code_material = (String) km_material_data.get("CODE_MATERIAL");
			count_material = String.valueOf(km_material_data
					.get("COUNT_MATERIAL"));
			material_body = new HashMap();
			material_body.put(XMLProcessBOM_ycl.ZXBM, code_material);
			material_body.put(XMLProcessBOM_ycl.SL, count_material);

			onebody = new ArrayList();
			onebody.add(material_body);

			invname = (String) km_material_data.get("INVNAME");
			version = (String) km_material_data.get("VERSION");

			material_head = new HashMap();
			material_head.put(XMLProcessBOM_ycl.WLBM, partcode);
			material_head.put(XMLProcessBOM_ycl.WLMC, invname);
			material_head.put(XMLProcessBOM_ycl.VERSION, version);

			material_data = new HashMap();
			material_data.put("body", onebody);
			material_data.put("head", material_head);

			material_data_xml.add(material_data);
		}
		return material_data_xml;
	}
    public boolean checkerpversion(String version){
		char[] b=version.toCharArray();
		int asc=0;
		for(char c:b){
			if((c+0)>=65 &&(c+0)<=90 ){
			System.out.println(c+":"+(c+0));
			return true;
			}else if((c+0)>=97 &&(c+0)<=122 ){
				System.out.println(c+":"+(c+0));
				return true;
			}	
		}
    	return false;
    }
	public ArrayList<String> QuickCompareDataInfo(HashMap<String, String> ERP,
			HashMap<String, HashMap> KM) {
		Iterator<String> index = ERP.keySet().iterator();
		String partcode = null;
		String version = null;
		String erppartcode = null;
		String erpversion = null;
		HashMap material_data = null;
		ArrayList<String> kmanderpisdif = new ArrayList<String>();
		while (index.hasNext()) {
			erppartcode = index.next();
			material_data = KM.get(erppartcode);
			erpversion = ERP.get(erppartcode).trim();
			//if(erpversion.equals("000"))tools.print(erpversion);
			if(checkerpversion(erpversion))continue;
			if (material_data != null) {
				version = (String) material_data.get("VERSION");
				version = version.trim();
				// tools.print(erppartcode+":km:"+version + "--version:"+
				// erpversion);
				if (!version.equals(erpversion)) {
					kmanderpisdif.add(erppartcode);
				} else {
					// tools.print("版本号相同的BOM：" + erppartcode + "--version:"
					// + erpversion);
				}
			}
		}
		Tools.dataToFile("erp需更新原材料BOM的图号：", kmanderpisdif);
		Tools.print(kmanderpisdif.size());
		return kmanderpisdif;
	}
}
