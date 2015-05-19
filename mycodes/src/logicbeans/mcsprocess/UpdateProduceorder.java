package logicbeans.mcsprocess;

import java.util.ArrayList;
import java.util.HashMap;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class UpdateProduceorder {
	private String xm = "";
	private static UpdateProduceorder oneUpdateBean = new UpdateProduceorder();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void setXm(String xm) {
		this.xm = "'" + xm + "'";
	}

	public static UpdateProduceorder getInstance() {
		return oneUpdateBean;
	}

	public String getQueryOrderidSql() {
		return QueryOrderidSql + "and t.PROJECTNO like " + this.xm
				+ " Group By t.Orderid";
	}

	public void UpdateStockSetId(ArrayList<String> OkOrderidArray,
			ArrayList<String> NoStockArray) {
		String orderid = "";
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		HashMap<Integer, String> oneupdate;
		ArrayList<String> executeResults = null;
		for (int n = OkOrderidArray.size() - 1; n >= 0; n--) {
			orderid = OkOrderidArray.get(n);
			oneupdate = new HashMap<Integer, String>();
			oneupdate.put(1, "1");
			oneupdate.put(2, orderid);
			dbdate.add(oneupdate);
		}
		for (int n = NoStockArray.size() - 1; n >= 0; n--) {
			orderid = NoStockArray.get(n);
			oneupdate = new HashMap<Integer, String>();
			oneupdate.put(1, "2");
			oneupdate.put(2, orderid);
			dbdate.add(oneupdate);
		}
		try {
			executeResults = LogicPublic.instance.dbmcs.updateData(
					UpdateStocketcidSql, dbdate);
		} catch (Exception e) {
			e.printStackTrace();
			Tools.print(dbdate);
		}
		Tools.print("共有：" + OkOrderidArray.size() + "条生产订单的备料计划被抓取");
		Tools.print("共有：" + NoStockArray.size() + "条生产订单无备料计划");
	}

	public void UpdateStockReason(ArrayList<String> OrderidArray) {
		ArrayList<HashMap<String, String>> tmp = LogicPublic.instance.dbmcs
				.getDataByString(getQueryOrderidSql());
		HashMap<String, String> oneRow;
		String orderid = "";
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		HashMap<Integer, String> oneupdate;
		ArrayList<String> executeResults = null;
		for (int n = tmp.size() - 1; n >= 0; n--) {
			oneRow = tmp.get(n);
			orderid = oneRow.get(MaterialStock.ORDERID);
			if (!OrderidArray.contains(orderid))
				continue;
			oneupdate = new HashMap<Integer, String>();
			if (Integer.valueOf(oneRow.get(MaterialStock.STOCKREASON1)) > 0) {
				oneupdate.put(1, "1");
			} else {
				oneupdate.put(1, "0");
			}
			if (Integer.valueOf(oneRow.get(MaterialStock.STOCKREASON2)) > 0) {
				oneupdate.put(2, "1");
			} else {
				oneupdate.put(2, "0");
			}
			if (Integer.valueOf(oneRow.get(MaterialStock.STOCKREASON3)) > 0) {
				oneupdate.put(3, "1");
			} else {
				oneupdate.put(3, "0");
			}
			oneupdate.put(4, orderid);
			dbdate.add(oneupdate);
		}
		try {
			executeResults = LogicPublic.instance.dbmcs.updateData(UpdateSql,
					dbdate);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(dbdate);
		}
		Tools.print("共更新了：" + executeResults.size() + "条生产订单的外购件属性");
	}

	private final String UpdateSql = "update produceorder set Stockreason1=?,Stockreason2=?,Stockreason3=? "
			+ "where orderid=?";
	private final String UpdateStocketcidSql = "update produceorder set stocketcid=? where orderid=?";
	private final String QueryOrderidSql = "Select t.Orderid,"
			+ "Sum(t.Stockreason1) as " + MaterialStock.STOCKREASON1
			+ LogicPublic.instance.comma + "Sum(t.Stockreason2) as "
			+ MaterialStock.STOCKREASON2 + LogicPublic.instance.comma
			+ "Sum(t.Stockreason3) as " + MaterialStock.STOCKREASON3
			+ "  From Materialstock t where t.BOUGHTENID=1 ";
}
