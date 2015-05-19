package logicbeans.technicsprocess;

import java.util.ArrayList;
import java.util.HashMap;

import publicbeans.LogicPublic;

public class getCooprateWorkProcedure extends BaseTechnics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    
	}

	HashMap<String, String> isrepeate = new HashMap<String, String>();

	public String get_km_coopratetechnics = "Select a.jgsj, b.partcode as invcode,b.docver as version,"
			+ "(Case When Length(a.Gx_Id) = 1 Then '0' || a.Gx_Id || '0'"
			+ " Else a.Gx_Id || '0' End) As gxh,"
			+ " a.code_gzzx From Pdm_Part b"
			+ " Left Join  Pdm_Gong_Xu a On a.Docid = b.Docid"
			+ "	Where b.Partcode Like '%AL%.%.%' And a.gb_id<>'-0' And a.code_gzzx<>'000000'"
			+ " and b.workhour='1' ";
	private final String orderby = "   Order By partcode ,gx_id,gb_id";

	public getCooprateWorkProcedure() {
		// TODO Auto-generated constructor stub
	}

	public getCooprateWorkProcedure(String TimeOrRang) {
		if (TimeOrRang == null) {
			TimeOrRang = "";
		}
		get_km_coopratetechnics += TimeOrRang + orderby;
	}

	@Override
	public void getData(HashMap MapData, HashMap oneRow, HashMap nextRow,
			String partcode) {

		String gxh = (String) oneRow.get("GXH");
		String version = String.valueOf(oneRow.get("VERSION"));
		String parameter = partcode + LogicPublic.instance.lable + version
				+ LogicPublic.instance.lable + gxh;
		String gzzxbm = (String) oneRow.get("CODE_GZZX");
		String jgsj = String.valueOf(oneRow.get("JGSJ"));
		ArrayList<HashMap<String, String>> list = null;
		list = (ArrayList<HashMap<String, String>>) MapData.get(parameter);
		if (list == null) {
			list = new ArrayList<HashMap<String, String>>();
			MapData.put(parameter, list);
		}
		HashMap<String, String> onegx = new HashMap<String, String>();
		onegx.put("gzzxbm", gzzxbm);
		onegx.put("jgsj", jgsj);
		// isrepeate.get(parameter + gzzxbm) == null &&
		if (list.size() < 5) {
			list.add(onegx);
			//isrepeate.put(parameter + gzzxbm, parameter + gzzxbm);
		}
	}

	public void process() {
		processData(LogicPublic.instance.dbkm.getData(get_km_coopratetechnics));
	}
}
