package logicbeans.mcsprocess;

public class MaterialStock {	
	public static final String CREATEDATE="CREATEDATE";     //CREATEDATE           DATE,
	public static final String CREATOR="CREATOR";           //CREATOR              VARCHAR2(10),
	public static final String MODIFYDATE="MODIFYDATE";
	public static final String MODIFIER="MODIFIER";
	public static final String REMARK="REMARK";
	public static final String ORDERID="ORDERID";
	public static final String PROJECTNO="PROJECTNO";
	public static final String STOCKID="STOCKID";
	public static final String PK_POID="PK_POID";
	public static final String PK_MOID="PK_MOID";
	public static final String PRODUCENO="PRODUCENO";
	public static final String ARCHIVEID="ARCHIVEID";
	public static final String DATEMARK="DATEMARK";
	public static final String LATESTID="LATESTID";
	public static final String STATID="STATID";
	public static final String STATDATE="STATDATE";
	public static final String PARTTYPE="PARTTYPE";
	public static final String PARTNO="PARTNO";
	public static final String PARTVERSION="PARTVERSION";
	public static final String PARTNAME="PARTNAME";
	public static final String PARTSPEC="PARTSPEC";
	public static final String ASSEMBLYSNO="ASSEMBLYSNO";
	public static final String COMPONENTSNO="COMPONENTSNO";
	public static final String BATCHNO="BATCHNO";
	public static final String AMOUNT="AMOUNT";
	public static final String UNIT="UNIT";
	public static final String PK_DEPTDOC="PK_DEPTDOC";
	public static final String DEPTNAME="DEPTNAME";
	public static final String STATE="STATE";
	public static final String FIRSTBDATE="FIRSTBDATE";
	public static final String FIRSTEDATE="FIRSTEDATE";	
	public static final String PLANEDATE="PLANEDATE";
	public static final String PLANBDATE="PLANBDATE";	
	public static final String FACTBDATE="FACTBDATE";
	public static final String FACTEDATE="FACTEDATE";	
	public static final String PK_PICKM_BID="PK_PICKM_BID";
	public static final String OUTWAREHOUSENO="OUTWAREHOUSENO";
	public static final String OUTWAREHOUSENAME="OUTWAREHOUSENAME";
	public static final String PLANNUM="PLANNUM";	
	public static final String UNITRATION="UNITRATION";
	public static final String RATIONNUM="RATIONNUM";
	public static final String OUTNUM="OUTNUM";	
	public static final String WAITOUTNUM="WAITOUTNUM";
	public static final String DUEOUTNUM="DUEOUTNUM";
	public static final String REDUCENUM="REDUCENUM";	
	public static final String FILTESTATE="FILTESTATE";
	public static final String REQDATE="REQDATE";
	public static final String REQTIME="REQTIME";
	public static final String SUBSTID="SUBSTID";
	public static final String MEMO="MEMO";
	public static final String UPARTNO="UPARTNO";	
	public static final String EARLYOUTNUM="EARLYOUTNUM";
	public static final String REMAINPLANNUM="REMAINPLANNUM";
	public static final String LOCKEDNUM="LOCKEDNUM";
	public static final String REQNUM="REQNUM";
	public static final String FINISHEDID="FINISHEDID";
	public static final String OVERDUEID="OVERDUEID";
	public static final String DAYEXPIREID="DAYEXPIREID";
	public static final String STOCKREADYID="STOCKREADYID";
	public static final String DAYOVERDUEID="DAYOVERDUEID";	
	public static final String STOCKREASON1="STOCKREASON1";
	public static final String STOCKREASON2="STOCKREASON2";
	public static final String STOCKREASON3="STOCKREASON3";
	public static final String STOCKREASON4="STOCKREASON4";
	public static final String STOCKREASON5="STOCKREASON5";
	public static final String STOCKREASON6="STOCKREASON6";
	public static final String STOCKREASON7="STOCKREASON7";
	public static final String STOCKREASON8="STOCKREASON8";	
	public static final String BUYNUM="BUYNUM";
	public static final String BUYPLAN="BUYPLAN";
	public static final String ELSELOCKENUM="ELSELOCKEDNUM";
	public static final String UNLOCKEDNUM="UNLOCKEDNUM";
	public static final String STOCKNUM="STOCKNUM";
	public static final String GATHERID="GATHERID";
	public static final String BOUGHTENID="BOUGHTENID";
/**
COMMENT ON TABLE MATERIALSTOCK IS
'备料计划';

COMMENT ON COLUMN MATERIALSTOCK.CREATEDATE IS
'创建时间';

COMMENT ON COLUMN MATERIALSTOCK.CREATOR IS
'创建者';

COMMENT ON COLUMN MATERIALSTOCK.MODIFYDATE IS
'更新时间';

COMMENT ON COLUMN MATERIALSTOCK.MODIFIER IS
'更新者';

COMMENT ON COLUMN MATERIALSTOCK.REMARK IS
'备注';

COMMENT ON COLUMN MATERIALSTOCK.STOCKID IS
'备料ID：从序列seq_material_stockid取值';

COMMENT ON COLUMN MATERIALSTOCK.PROJECTNO IS
'项目代号';

COMMENT ON COLUMN MATERIALSTOCK.PK_POID IS
'计划订单主键';

COMMENT ON COLUMN MATERIALSTOCK.PK_MOID IS
'生产订单主键';

COMMENT ON COLUMN MATERIALSTOCK.PRODUCENO IS
'生产订单号';

COMMENT ON COLUMN MATERIALSTOCK.ARCHIVEID IS
'是否最终版本(0否;1是)';

COMMENT ON COLUMN MATERIALSTOCK.DATEMARK IS
'日期';

COMMENT ON COLUMN MATERIALSTOCK.LATESTID IS
'是否当天最后版本(0否;1是)';

COMMENT ON COLUMN MATERIALSTOCK.STATID IS
'是否已统计(0否;1是)';

COMMENT ON COLUMN MATERIALSTOCK.STATDATE IS
'统计时间';

COMMENT ON COLUMN MATERIALSTOCK.PARTTYPE IS
'part类型';

COMMENT ON COLUMN MATERIALSTOCK.PARTNO IS
'part编号';

COMMENT ON COLUMN MATERIALSTOCK.PARTVERSION IS
'part版本';

COMMENT ON COLUMN MATERIALSTOCK.PARTNAME IS
'part名称';

COMMENT ON COLUMN MATERIALSTOCK.PARTSPEC IS
'型号规格';

COMMENT ON COLUMN MATERIALSTOCK.ASSEMBLYSNO IS
'整件序号';

COMMENT ON COLUMN MATERIALSTOCK.COMPONENTSNO IS
'零件序号';

COMMENT ON COLUMN MATERIALSTOCK.BATCHNO IS
'批次号';

COMMENT ON COLUMN MATERIALSTOCK.AMOUNT IS
'数量';

COMMENT ON COLUMN MATERIALSTOCK.UNIT IS
'计量单位';

COMMENT ON COLUMN MATERIALSTOCK.PK_DEPTDOC IS
'主制部门id';

COMMENT ON COLUMN MATERIALSTOCK.DEPTNAME IS
'主制部门名称';

COMMENT ON COLUMN MATERIALSTOCK.STATE IS
'状态(计划/投放/完工)';

COMMENT ON COLUMN MATERIALSTOCK.FIRSTBDATE IS
'首次计划开始时间';

COMMENT ON COLUMN MATERIALSTOCK.FIRSTEDATE IS
'首次计划完成时间';

COMMENT ON COLUMN MATERIALSTOCK.PLANBDATE IS
'计划开始时间';

COMMENT ON COLUMN MATERIALSTOCK.PLANEDATE IS
'计划完成时间';

COMMENT ON COLUMN MATERIALSTOCK.FACTBDATE IS
'实际开始时间';

COMMENT ON COLUMN MATERIALSTOCK.FACTEDATE IS
'实际完成时间';

COMMENT ON COLUMN MATERIALSTOCK.PK_PICKM_BID IS
'备料计划单体主键';

COMMENT ON COLUMN MATERIALSTOCK.OUTWAREHOUSENO IS
'出库仓库编码';

COMMENT ON COLUMN MATERIALSTOCK.OUTWAREHOUSENAME IS
'出库仓库名称';

COMMENT ON COLUMN MATERIALSTOCK.PLANNUM IS
'计划出库数量';

COMMENT ON COLUMN MATERIALSTOCK.UNITRATION IS
'单位定额';

COMMENT ON COLUMN MATERIALSTOCK.RATIONNUM IS
'定额用量';

COMMENT ON COLUMN MATERIALSTOCK.OUTNUM IS
'累计出库数量';

COMMENT ON COLUMN MATERIALSTOCK.WAITOUTNUM IS
'已完数量';

COMMENT ON COLUMN MATERIALSTOCK.DUEOUTNUM IS
'累计报出库数量';

COMMENT ON COLUMN MATERIALSTOCK.REDUCENUM IS
'调减数';

COMMENT ON COLUMN MATERIALSTOCK.FILTESTATE IS
'筛选状态';

COMMENT ON COLUMN MATERIALSTOCK.REQDATE IS
'需用日期';

COMMENT ON COLUMN MATERIALSTOCK.REQTIME IS
'需用时间';

COMMENT ON COLUMN MATERIALSTOCK.SUBSTID IS
'替代信息';

COMMENT ON COLUMN MATERIALSTOCK.MEMO IS
'备料计划备注';

COMMENT ON COLUMN MATERIALSTOCK.UPARTNO IS
'装入图号';

COMMENT ON COLUMN MATERIALSTOCK.EARLYOUTNUM IS
'老系统出库数';

COMMENT ON COLUMN MATERIALSTOCK.REMAINPLANNUM IS
'剩余需求量';

COMMENT ON COLUMN MATERIALSTOCK.LOCKEDNUM IS
'已锁定量';

COMMENT ON COLUMN MATERIALSTOCK.REQNUM IS
'缺料数量';

COMMENT ON COLUMN MATERIALSTOCK.FINISHEDID IS
'是否完成(0否;1是)';

COMMENT ON COLUMN MATERIALSTOCK.OVERDUEID IS
'是否拖期(0否;1是)';

COMMENT ON COLUMN MATERIALSTOCK.DAYEXPIREID IS
'当日是否到期(0否;1是)';

COMMENT ON COLUMN MATERIALSTOCK.DAYOVERDUEID IS
'当日是否拖期(0否;1是)';

COMMENT ON COLUMN MATERIALSTOCK.STOCKREADYID IS
'外购件是否齐套(0否;1是)';

COMMENT ON COLUMN MATERIALSTOCK.STOCKREASON1 IS
'外购件不齐套原因1：外购件是否无库存且未采购(0否;1是)';

COMMENT ON COLUMN MATERIALSTOCK.STOCKREASON2 IS
'外购件不齐套原因2：外购件是否无库存但已采购(0否;1是)';

COMMENT ON COLUMN MATERIALSTOCK.STOCKREASON3 IS
'外购件不齐套原因3：外购件是否有库存但被占用(0否;1是)';

COMMENT ON COLUMN MATERIALSTOCK.BUYNUM IS
'采购在途数量';

COMMENT ON COLUMN MATERIALSTOCK.BUYPLAN IS
'采购到货计划';

COMMENT ON COLUMN MATERIALSTOCK.ELSELOCKENUM IS
'其它锁定量';

COMMENT ON COLUMN MATERIALSTOCK.UNLOCKEDNUM IS
'未锁定量';

COMMENT ON COLUMN MATERIALSTOCK.STOCKNUM IS
'现存量';	
gatherid	 //是否启套	
/**
* @param args
*/
	
public static void main(String[] args) {
		// TODO Auto-generated method stub

}
}
