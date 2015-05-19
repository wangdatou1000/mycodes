package logicbeans.technicsprocess;

import java.util.ArrayList;
import java.util.HashMap;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class BaseTechnics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public BaseTechnics() {
		// TODO Auto-generated constructor stub
	}

	public void getData(HashMap MapTechnicsData, HashMap oneRow,
			HashMap nextRow, String partcode) {
	}

	public HashMap processData(ArrayList<HashMap> list) {
		if (list == null) {
			return null;
		}
		HashMap MapTechnicsData = new HashMap();
		HashMap oneRow = null;
		HashMap nextRow = null;
		String partcode = null;
		ArrayList<String> PartArrayList = null;
		int MaxRow = list.size();
		 Tools.print(MaxRow);
		for (int n = 0; n < MaxRow; n++) {
			oneRow = list.get(n);
			if (n + 1 < MaxRow) {
				nextRow = list.get(n + 1);
			}
			partcode = String.valueOf(oneRow.get("INVCODE"));
			if (partcode == null) {
				continue;
			}
			// tools.print(partcode);
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
				getData(MapTechnicsData, oneRow, nextRow, partcode);
			}
		}
		Tools.print("the size of  MapData:"
				+ MapTechnicsData.size());
		return MapTechnicsData;
	}
}
