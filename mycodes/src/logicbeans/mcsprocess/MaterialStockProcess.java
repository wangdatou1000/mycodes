package logicbeans.mcsprocess;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import publicbeans.LogicPublic;
import publicbeans.Tools;

public class MaterialStockProcess {
	String xm = "";
	private ArrayList<String> WhichUpdate = new ArrayList<String>();
	private ArrayList<String> NoStock = new ArrayList<String>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MaterialStockProcess t = new MaterialStockProcess("302B-00");
		// t.deleteTable();
		t.process();
	}

	public ArrayList<String> getWhichUpdate() {
		return this.WhichUpdate;
	}

	// public ArrayList<String> getNoStock() {
	// return this.NoStock;
	// }

	public MaterialStockProcess(String xm) {
		super();
		this.xm = "'" + xm + "'";
	}

	public void process() {
		deleteOldMaterialStock();
		HashMap<String, String> orderidmap = getOrderid();
		getAndSaveMaterialStock(orderidmap);
		UpdateProduceorder u = UpdateProduceorder.getInstance();
		u.setXm(xm);
		u.UpdateStockReason(getWhichUpdate());
		u.UpdateStockSetId(getWhichUpdate(),
				getNoStock(getWhichUpdate(), orderidmap));
	}

	public ArrayList<String> getNoStock(ArrayList<String> WhichUpdate,
			HashMap<String, String> orderidmap) {
		Iterator<String> key = orderidmap.keySet().iterator();
		ArrayList<String> NoStock = new ArrayList<String>();
		String OrderId;
		while (key.hasNext()) {
			OrderId = orderidmap.get(key.next());
			if (!WhichUpdate.contains(OrderId))
				NoStock.add(OrderId);
		}
		return NoStock;
	}

	public void deleteOldMaterialStock() {
		String where = " where " + MaterialStock.PROJECTNO + " like " + this.xm;
		String sql = "Delete From materialstock " + where;
		Tools.print(LogicPublic.instance.dbmcs.execute(sql));
	}
	public void deleteSameOrderid(HashMap<String, String> orderidmap){
		String where = " where " + MaterialStock.PROJECTNO + " like " + this.xm +" and orderid=?";
		String sql = "Delete From materialstock " + where;
		Iterator<String> orderidkey=orderidmap.keySet().iterator();
		String onekey;
		String oneorderid;
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		HashMap<Integer, String> oneupdate;
		ArrayList<String> executeResults = null;
		while(orderidkey.hasNext()){
			onekey=orderidkey.next();
			oneorderid=orderidmap.get(onekey);
			oneupdate = new HashMap<Integer, String>();
			oneupdate.put(1, oneorderid);
			dbdate.add(oneupdate);
		}
		try {
			executeResults = LogicPublic.instance.dbmcs.updateData(
					sql, dbdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Tools.print("共有：" + executeResults.size() + "個orderid被刪除");
	}
	public HashMap<String, String> getOrderid() {
		ArrayList<HashMap<String, String>> tmp = LogicPublic.instance.dbmcs
				.getDataByString((OrderidQUERY + xm));
		HashMap<String, String> orderid = new HashMap<String, String>();
		HashMap<String, String> oneRow;
		for (int n = tmp.size() - 1; n >= 0; n--) {
			oneRow = tmp.get(n);
			orderid.put(String.valueOf(oneRow.get("PRODUCENO")),
					oneRow.get("ORDERID"));
		}
		return orderid;
	}

	public boolean IsStockReady(String qxsl) {
		if (qxsl == null)
			return false;
		double d = Double.valueOf(qxsl);
		if (d > 0) {
			return false;
		} else {
			return true;
		}
	}

	public void getAndSaveMaterialStock(HashMap<String, String> orderidmap) {
		ArrayList<HashMap<String, String>> tmp = LogicPublic.instance.dberp
				.getDataByString(getSqlquery());
		ArrayList<HashMap<Integer, String>> dbdate = new ArrayList<HashMap<Integer, String>>();
		HashMap<Integer, String> oneupdate;
		HashMap<String, String> oneRow;
		ArrayList<String> executeResults = null;
		String t = "";
		String PRODUCENO = "";
		int OrderidNum = orderidmap.size();
		Tools.print("备料计划总数：" + tmp.size() + "\n生产订单总数：" + OrderidNum);
		String Orderid = "";
		int IsFinished = 0;
		boolean IsQiTaoJ=false;
		for (int n = tmp.size() - 1; n >= 0; n--) {
			oneRow = tmp.get(n);
			PRODUCENO = oneRow.get(MaterialStock.PRODUCENO);
			if (!orderidmap.containsKey(PRODUCENO)) {
				continue;
			}// 如果在系统中找不到生产令号对应的orderid，则不进行该令号备料计划的导入。
			Orderid = orderidmap.get(PRODUCENO);
			if (!WhichUpdate.contains(Orderid))
				WhichUpdate.add(Orderid);
			oneupdate = new HashMap<Integer, String>();
			oneupdate.put(1, oneRow.get(MaterialStock.PROJECTNO));
			oneupdate.put(2, oneRow.get(MaterialStock.PK_POID));
			oneupdate.put(3, oneRow.get(MaterialStock.PK_MOID));
			oneupdate.put(4, oneRow.get(MaterialStock.UPARTNO));
			oneupdate.put(5, oneRow.get(MaterialStock.PARTNO));
			oneupdate.put(6, oneRow.get(MaterialStock.PARTNAME));
			oneupdate.put(7, oneRow.get(MaterialStock.PARTTYPE));
			oneupdate.put(8, oneRow.get(MaterialStock.PARTSPEC));
			oneupdate.put(9, oneRow.get(MaterialStock.UNIT));
			oneupdate.put(10, oneRow.get(MaterialStock.ASSEMBLYSNO));
			oneupdate.put(11, oneRow.get(MaterialStock.COMPONENTSNO));
			oneupdate.put(12, oneRow.get(MaterialStock.BATCHNO));
			oneupdate.put(13, oneRow.get(MaterialStock.PK_DEPTDOC));
			oneupdate.put(14, oneRow.get(MaterialStock.DEPTNAME));
			oneupdate.put(15, oneRow.get(MaterialStock.OUTWAREHOUSENO));
			oneupdate.put(16, oneRow.get(MaterialStock.OUTWAREHOUSENAME));
			oneupdate.put(17, oneRow.get(MaterialStock.PK_PICKM_BID));
			oneupdate.put(18, oneRow.get(MaterialStock.UNITRATION));
			oneupdate.put(19, oneRow.get(MaterialStock.RATIONNUM));
			oneupdate.put(20, oneRow.get(MaterialStock.DUEOUTNUM));
			oneupdate.put(21, oneRow.get(MaterialStock.OUTNUM));
			oneupdate.put(22, oneRow.get(MaterialStock.WAITOUTNUM));
			oneupdate.put(23, oneRow.get(MaterialStock.REQNUM));
			oneupdate.put(24, oneRow.get(MaterialStock.REMAINPLANNUM));
			oneupdate.put(25, oneRow.get(MaterialStock.ELSELOCKENUM));
			oneupdate.put(26, oneRow.get(MaterialStock.REDUCENUM));
			oneupdate.put(27, oneRow.get(MaterialStock.STOCKNUM));
			oneupdate.put(28, oneRow.get(MaterialStock.BUYNUM));

			if (IsStockReady(oneRow.get(MaterialStock.REQNUM))) {
				oneupdate.put(29, "1");
			} else {
				oneupdate.put(29, "0");
			}
			IsFinished = Integer.valueOf(oneRow.get(MaterialStock.FINISHEDID));
			if (IsFinished == 0
					&& !IsStockReady(oneRow.get(MaterialStock.REQNUM))
					&& Tools.after(oneRow.get(MaterialStock.REQDATE))) {
				oneupdate.put(53, "1");
			} else {
				oneupdate.put(53, "0");
			}
			oneupdate.put(30, oneRow.get(MaterialStock.FINISHEDID));

			oneupdate.put(31, oneRow.get(MaterialStock.FILTESTATE));
			oneupdate.put(32, oneRow.get(MaterialStock.REQDATE));

			oneupdate.put(33, oneRow.get(MaterialStock.REQTIME));
			oneupdate.put(34, oneRow.get(MaterialStock.SUBSTID));

			oneupdate.put(35, oneRow.get(MaterialStock.EARLYOUTNUM));

			oneupdate.put(36, oneRow.get(MaterialStock.PLANBDATE));

			oneupdate.put(37, oneRow.get(MaterialStock.PLANEDATE));
			oneupdate.put(38, oneRow.get(MaterialStock.FACTBDATE));
			oneupdate.put(39, oneRow.get(MaterialStock.FACTEDATE));
			oneupdate.put(40, oneRow.get(MaterialStock.STATE));

			t = oneRow.get(MaterialStock.GATHERID);
			if (t != null && t.equals("Y")) {
				oneupdate.put(41, "1");
				IsQiTaoJ=true;
			} else {
				oneupdate.put(41, "0");
				IsQiTaoJ=false;
			}
			oneupdate.put(42, "1");
			oneupdate.put(43, "1");
			oneupdate.put(44, Tools.getCurrentDate());
			oneupdate.put(45, orderidmap.get(PRODUCENO));
			oneupdate.put(46, oneRow.get(MaterialStock.PRODUCENO));
			oneupdate.put(47, oneRow.get(MaterialStock.MEMO));

			// 外购件是否无库存且未采购,外购件是否无库存但已采购,外购件是否有库存但被占用
			t = (String) oneRow.get(MaterialStock.BOUGHTENID);
			Double xcl = Double.valueOf(oneRow.get(MaterialStock.STOCKNUM));
			Double ztl = Double.valueOf(oneRow.get(MaterialStock.BUYNUM));
			Double ljqzksdl = Double.valueOf(oneRow
					.get(MaterialStock.LOCKEDNUM));
			// System.out.print(t);
			if (t != null && t.equals("MR")) {

				if (xcl == 0 && ztl == 0 && IsFinished == 0) {
					oneupdate.put(48, "1");
				} else {
					oneupdate.put(48, "0");
				}
				if (xcl == 0 && ztl > 0 && IsFinished == 0) {
					oneupdate.put(49, "1");
				} else {
					oneupdate.put(49, "0");
				}
				if (xcl > 0 && ljqzksdl == 0 && IsFinished == 0&& IsQiTaoJ) {
					oneupdate.put(50, "1");
				} else {
					oneupdate.put(50, "0");
				}
				oneupdate.put(52, "1");
			} else {
				oneupdate.put(48, "0");
				oneupdate.put(49, "0");
				oneupdate.put(50, "0");
				oneupdate.put(52, "0");
			}

			oneupdate.put(51, oneRow.get(MaterialStock.UNLOCKEDNUM));
			oneupdate.put(54, Tools.getCurrentDateTime());
			oneupdate.put(55, oneRow.get(MaterialStock.LOCKEDNUM));
			oneupdate.put(56, oneRow.get(MaterialStock.PLANNUM));
			dbdate.add(oneupdate);
			//break;
		}
		try {
			executeResults = LogicPublic.instance.dbmcs.updateData(type,
					getUpdateSql(), dbdate);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(dbdate);
		}

		Tools.print("更新的备料计划数量：" + executeResults.size() + "\n对应的生产订单数量："
				+ (OrderidNum - NoStock.size()));
	}

	public String getUpdateSql() {
		StringBuilder InsertSQL = new StringBuilder();
		InsertSQL.append("insert into materialstock(")
				.append(MaterialStock.STOCKID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PROJECTNO)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PK_POID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PK_MOID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.UPARTNO)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PARTNO)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PARTNAME)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PARTTYPE)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PARTSPEC)
				.append(LogicPublic.instance.comma).append(MaterialStock.UNIT)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.ASSEMBLYSNO)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.COMPONENTSNO)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.BATCHNO)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PK_DEPTDOC)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.DEPTNAME)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.OUTWAREHOUSENO)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.OUTWAREHOUSENAME)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PK_PICKM_BID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.UNITRATION)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.RATIONNUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.DUEOUTNUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.OUTNUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.WAITOUTNUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.REQNUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.REMAINPLANNUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.ELSELOCKENUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.REDUCENUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.STOCKNUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.BUYNUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.STOCKREADYID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.FINISHEDID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.FILTESTATE)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.REQDATE)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.REQTIME)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.SUBSTID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.EARLYOUTNUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PLANBDATE)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PLANEDATE)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.FACTBDATE)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.FACTEDATE)
				.append(LogicPublic.instance.comma).append(MaterialStock.STATE)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.GATHERID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.STATID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.LATESTID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.DATEMARK)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.ORDERID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PRODUCENO)
				.append(LogicPublic.instance.comma).append(MaterialStock.MEMO)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.STOCKREASON1)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.STOCKREASON2)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.STOCKREASON3)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.UNLOCKEDNUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.BOUGHTENID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.OVERDUEID)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.STATDATE)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.LOCKEDNUM)
				.append(LogicPublic.instance.comma)
				.append(MaterialStock.PLANNUM).append(values)
				.append(getValues()).append(")");
		return InsertSQL.toString();
	}

	private String getSqlquery() {
		StringBuilder sb = new StringBuilder();
		sb.append("select v_xm.jobcode as ").append(MaterialStock.PROJECTNO)
				.append(LogicPublic.instance.comma);
		sb.append("mm_po.pk_poid as ").append(MaterialStock.PK_POID)
				.append(LogicPublic.instance.comma);
		sb.append("mm_mo.pk_moid as ").append(MaterialStock.PK_MOID)
				.append(LogicPublic.instance.comma);
		sb.append("mm_mo.scddh as  ").append(MaterialStock.PRODUCENO)
				.append(LogicPublic.instance.comma);
		// 装入图号
		sb.append("mm_pickm_b.zdy18 as ").append(MaterialStock.UPARTNO)
				.append(LogicPublic.instance.comma);
		sb.append("zxwl.invcode as ").append(MaterialStock.PARTNO)
				.append(LogicPublic.instance.comma);
		sb.append("zxwl.invname as  ").append(MaterialStock.PARTNAME)
				.append(LogicPublic.instance.comma);
		sb.append("zxwl.homemade  as  ").append(MaterialStock.PARTTYPE)
				.append(LogicPublic.instance.comma);
		sb.append("zxwl.invspec  as  ").append(MaterialStock.PARTSPEC)
				.append(LogicPublic.instance.comma);
		sb.append("zxwl.measname  as  ").append(MaterialStock.UNIT)
				.append(LogicPublic.instance.comma);

		sb.append("mm_mo.zdy35  as  ").append(MaterialStock.ASSEMBLYSNO)
				.append(LogicPublic.instance.comma);
		sb.append("mm_mo.zdy36  as  ").append(MaterialStock.COMPONENTSNO)
				.append(LogicPublic.instance.comma);
		sb.append("mm_pickm_b.pch as   ").append(MaterialStock.BATCHNO)
				.append(LogicPublic.instance.comma);
		sb.append("mm_mo.scbmid  as ").append(MaterialStock.PK_DEPTDOC)
				.append(LogicPublic.instance.comma);
		sb.append("bd_deptdoc.deptname as ").append(MaterialStock.DEPTNAME)
				.append(LogicPublic.instance.comma);
		sb.append("bd_stordoc.storcode as  ")
				.append(MaterialStock.OUTWAREHOUSENO)
				.append(LogicPublic.instance.comma);
		sb.append("bd_stordoc.storname as ")
				.append(MaterialStock.OUTWAREHOUSENAME)
				.append(LogicPublic.instance.comma);

		sb.append("mm_pickm_b.pk_pickm_bid as ")
				.append(MaterialStock.PK_PICKM_BID)
				.append(LogicPublic.instance.comma);
		sb.append("mm_pickm_b.dwde as  ").append(MaterialStock.UNITRATION)
				.append(LogicPublic.instance.comma);
		// 定额用量
		sb.append("nvl(mm_pickm_b.deyl,0) as  ")
				.append(MaterialStock.RATIONNUM)
				.append(LogicPublic.instance.comma);
		sb.append("nvl(mm_pickm_b.jhcksl,0) as  ")
				.append(MaterialStock.PLANNUM)
				.append(LogicPublic.instance.comma);
		// 累计报出库数量
		sb.append("nvl(mm_pickm_b.ljyfsl,0) as  ")
				.append(MaterialStock.DUEOUTNUM)
				.append(LogicPublic.instance.comma);
		// 累计出库数量
		sb.append("nvl(mm_pickm_b.ljcksl,0) as ").append(MaterialStock.OUTNUM)
				.append(LogicPublic.instance.comma);
		// 已发未出数量
		sb.append("nvl(mm_pickm_b.ljyfsl,0)-nvl(mm_pickm_b.ljcksl,0) as  ")
				.append(MaterialStock.WAITOUTNUM)
				.append(LogicPublic.instance.comma);
		// 缺料数量
		sb.append(
				"nvl(mm_pickm_b.jhcksl,0)-nvl(mm_pickm_b.ljcksl,0)-nvl(sdl.sdl,0) as  ")
				.append(MaterialStock.REQNUM)
				.append(LogicPublic.instance.comma);
		// 剩余需求量
		sb.append("nvl(mm_pickm_b.jhcksl,0)-nvl(mm_pickm_b.ljcksl,0) as  ")
				.append(MaterialStock.REMAINPLANNUM)
				.append(LogicPublic.instance.comma);
		// 已锁定量
		sb.append("nvl(sdl.sdl,0) as  ").append(MaterialStock.LOCKEDNUM)
				.append(LogicPublic.instance.comma);
		// 其它锁定量
		sb.append("nvl(xmsd.sdl,0)-nvl(sdl.sdl,0) as  ")
				.append(MaterialStock.ELSELOCKENUM)
				.append(LogicPublic.instance.comma);
		// 未锁定量
		sb.append("nvl(zyl.zyl,0) as  ").append(MaterialStock.UNLOCKEDNUM)
				.append(LogicPublic.instance.comma);
		// 调减数
		sb.append("nvl(mm_pickm_b.zdy14,0) as ")
				.append(MaterialStock.REDUCENUM)
				.append(LogicPublic.instance.comma);
		// 现存量
		sb.append("nvl(xcl.xcl,0) as  ").append(MaterialStock.STOCKNUM)
				.append(LogicPublic.instance.comma);
		// 采购在途数量
		sb.append("nvl(ztl.ztl,0) as  ").append(MaterialStock.BUYNUM)
				.append(LogicPublic.instance.comma);
		// 外购件是否齐套(0否;1是) zxwl.def1 as
		sb.append("mm_pickm.ismrqt as ").append(MaterialStock.STOCKREADYID)
				.append(LogicPublic.instance.comma);
		// 是否齐套件(0否;1是)
		sb.append("zxwl.def1 as ").append(MaterialStock.GATHERID)
				.append(LogicPublic.instance.comma);
		// 是否完成(0否;1是)
		sb.append(
				"(case when (nvl(mm_pickm_b.jhcksl,0) - nvl(mm_pickm_b.ljcksl,0)) >0 then 0 "
						+ "  else 1 end )  as ")
				.append(MaterialStock.FINISHEDID)
				.append(LogicPublic.instance.comma);
		sb.append("mm_pickm_b.bodybz as ").append(MaterialStock.MEMO)
				.append(LogicPublic.instance.comma);
		sb.append("mm_pickm_b.zyx1 as ").append(MaterialStock.FILTESTATE)
				.append(LogicPublic.instance.comma);
		sb.append("mm_pickm_b.flrq  as ").append(MaterialStock.REQDATE)
				.append(LogicPublic.instance.comma);
		sb.append("mm_pickm_b.xqsj as ").append(MaterialStock.REQTIME)
				.append(LogicPublic.instance.comma);
		sb.append("mm_pickm_b.tdbz as   ").append(MaterialStock.SUBSTID)
				.append(LogicPublic.instance.comma);

		sb.append("nvl(mm_pickm_b.zdy20,0) as  ")
				.append(MaterialStock.EARLYOUTNUM)
				.append(LogicPublic.instance.comma);

		sb.append("mm_mo.jhkgrq as  ").append(MaterialStock.PLANBDATE)
				.append(LogicPublic.instance.comma);
		sb.append("mm_mo.jhwgrq as ").append(MaterialStock.PLANEDATE)
				.append(LogicPublic.instance.comma);
		sb.append("mm_mo.sjkgrq as ").append(MaterialStock.FACTBDATE)
				.append(LogicPublic.instance.comma);
		sb.append("mm_mo.sjwgrq as ").append(MaterialStock.FACTEDATE)
				.append(LogicPublic.instance.comma);
		sb.append(
				"(case when mm_mo.zt='A' then '计划'  when mm_mo.zt='B' then '投放' "
						+ " when mm_mo.zt='C' then '完工' end ) as  ")
				.append(MaterialStock.STATE).append(LogicPublic.instance.comma);

		sb.append("zxwl.matertype as ").append(MaterialStock.BOUGHTENID);

		sb.append(from).append(xiancl).append(zaitl).append(suodl)
				.append(zhiyl).append(xiangmusdl).append(where).append(xm);

		return sb.toString();
	}

	public String getValues() {
		StringBuilder tmp = new StringBuilder(STOCK_ID);
		for (int n = type.length - 1; n >= 0; n--) {
			tmp.append(LogicPublic.instance.comma).append("?");
		}
		return tmp.toString();
	}

	private static final int[] type = { Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP,
			Types.VARCHAR, Types.VARCHAR };
	private static final String from = " from mm_mo"
			// + "    	  left join v_wl on v_wl.pk_invbasdoc = mm_mo.wlbmid\n"
			+ "    	  left join mm_mosource on mm_mosource.scddid = mm_mo.pk_moid\n"
			+ "    	  left join mm_po on mm_po.pk_poid = mm_mosource.lyid\n"
			+ "    	  left join v_xm on v_xm.pk_jobmngfil = mm_mo.pk_jobmngfil\n"
			+ "    	  left join bd_deptdoc on bd_deptdoc.pk_deptdoc = mm_mo.scbmid\n"
			+ "    	  left join mm_pickm on nvl(mm_pickm.lydjh,mm_pickm.zdy4) = mm_mo.scddh\n"
			+ "    	  left join mm_pickm_b on mm_pickm_b.pk_pickmid = mm_pickm.pk_pickmid\n"
			+ "    	  left join bd_stordoc on bd_stordoc.pk_stordoc = mm_pickm_b.ckckid\n"
			+ " 	      left join (select sum(nvl(cetc_qtps_b.nxqsl,0)) as ytqtjhs, cetc_qtps_b.pk_pickm_bid from cetc_qtps_b\n"
			+ "                    where cetc_qtps_b.dr =0  group by pk_pickm_bid )\n"
			+ "  					 qtjh on qtjh.pk_pickm_bid =mm_pickm_b.pk_pickm_bid\n"
			+ "    	  left join v_wl zxwl on zxwl.pk_invbasdoc = mm_pickm_b.wlbmid\n";

	private static final String where = " where mm_mo.dr =0  and mm_po.dr =0  and mm_pickm.dr =0 \n"
			+ " and mm_pickm_b.dr=0  and mm_pickm.bljhlx=0 and v_xm.jobcode like ";
	// " and zxwl.invcode like 'AL4%'";
	// 现存量
	private static final String xiancl = "  left join   (select   sum(nvl(ic_onhandnum.nonhandnum,0) ) as xcl , ic_onhandnum.cinvbasid,  ic_onhandnum.vfree1"
			+ "  from ic_onhandnum   where nvl(ic_onhandnum.nonhandnum,0) >0 and ic_onhandnum.dr =0 \n"
			+ "  group by  ic_onhandnum.cinvbasid,  ic_onhandnum.vfree1 ) xcl \n"
			+ "  on xcl.cinvbasid = zxwl.pk_invbasdoc and mm_pickm_b.zyx1 = xcl.vfree1\n";
	// 锁定量
	private static final String suodl = " left join (select sum(nvl(cetc_iclock_no.nlocknum,0)) as sdl,\n"
			+ "  cetc_iclock_no.bldh, cetc_iclock_no.vfree1, cetc_iclock_no.cinvbasid \n"
			+ "  from cetc_iclock_no where cetc_iclock_no.dr=0 and nvl(cetc_iclock_no.nlocknum,0) >0\n"
			+ "  group by cetc_iclock_no.bldh, cetc_iclock_no.vfree1, cetc_iclock_no.cinvbasid) sdl \n"
			+ "   on sdl.bldh =mm_pickm.bljhdh  and sdl.cinvbasid = zxwl.pk_invbasdoc and sdl.vfree1 = mm_pickm_b.zyx1\n";
	// 在途量
	private static final String zaitl = "   left join  ( select po_order_b.cbaseid,\n"
			+ " sum(nvl(po_order_b.nordernum,0))-sum(nvl(po_order_b.naccumstorenum,0)) as ztl \n"
			+ " from po_order_b  where po_order_b.dr =0"
			+ "  group by po_order_b.cbaseid) ztl on ztl.cbaseid = zxwl.pk_invbasdoc\n";
	// 自由量
	private static final String zhiyl = "   left join (select  sum(nvl(cetc_iclock.nlocknum,0)) - sum(nvl(cetc_iclock_no.nlocknum,0))  as zyl,\n"
			+ "  cetc_iclock.cinvbasid, cetc_iclock.vfree1, cetc_iclock.cprojectid\n"
			+ "  from  cetc_iclock  inner  join cetc_iclock_no on cetc_iclock_no.pk_iclock = cetc_iclock.pk_iclock\n"
			+ "  where cetc_iclock_no.dr =0  and cetc_iclock.dr =0  and nvl(cetc_iclock.nlocknum,0) >0\n"
			+ "  and nvl(cetc_iclock_no.nlocknum,0)>0\n"
			+ "  group by cetc_iclock.cinvbasid, cetc_iclock.vfree1, cetc_iclock.cprojectid)\n"
			+ "  zyl on zyl.cinvbasid = zxwl.pk_invbasdoc and zyl.vfree1 = mm_pickm_b.zyx1\n "
			+ "  and zyl.cprojectid = mm_mo.pk_jobmngfil\n";
	// 项目锁定量
	private static final String xiangmusdl = " left join (select sum(nvl(cetc_iclock.nlocknum,0)) as sdl, \n"
			+ "  cetc_iclock.cinvbasid, cetc_iclock.vfree1 \n"
			+ "  from cetc_iclock where cetc_iclock.dr=0 and nvl(cetc_iclock.nlocknum,0) >0"
			+ "  group by cetc_iclock.cinvbasid, cetc_iclock.vfree1 ) xmsd\n "
			+ "  on xmsd.cinvbasid = zxwl.pk_invbasdoc and xmsd.vfree1 = mm_pickm_b.zyx1 \n";
			//+ "  and xmsd.cprojectid = mm_mo.pk_jobmngfil ";

	private static final String STOCK_ID = "seq_material_stockid.nextval";
	private static final String values = ") values(";
	private static final String OrderidQUERY = "Select Bm.bomid,bm.PROJECTNO,pd.ORDERID,pd.PRODUCENO"
			+ "  From v_Bominfo_Archive Bm"
			+ "  Left Join v_Bom_Produceorder Pd On Pd.Bomid = Bm.Bomid where 1=1 and bm.projectno like ";
}
