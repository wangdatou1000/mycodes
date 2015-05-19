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
'���ϼƻ�';

COMMENT ON COLUMN MATERIALSTOCK.CREATEDATE IS
'����ʱ��';

COMMENT ON COLUMN MATERIALSTOCK.CREATOR IS
'������';

COMMENT ON COLUMN MATERIALSTOCK.MODIFYDATE IS
'����ʱ��';

COMMENT ON COLUMN MATERIALSTOCK.MODIFIER IS
'������';

COMMENT ON COLUMN MATERIALSTOCK.REMARK IS
'��ע';

COMMENT ON COLUMN MATERIALSTOCK.STOCKID IS
'����ID��������seq_material_stockidȡֵ';

COMMENT ON COLUMN MATERIALSTOCK.PROJECTNO IS
'��Ŀ����';

COMMENT ON COLUMN MATERIALSTOCK.PK_POID IS
'�ƻ���������';

COMMENT ON COLUMN MATERIALSTOCK.PK_MOID IS
'������������';

COMMENT ON COLUMN MATERIALSTOCK.PRODUCENO IS
'����������';

COMMENT ON COLUMN MATERIALSTOCK.ARCHIVEID IS
'�Ƿ����հ汾(0��;1��)';

COMMENT ON COLUMN MATERIALSTOCK.DATEMARK IS
'����';

COMMENT ON COLUMN MATERIALSTOCK.LATESTID IS
'�Ƿ������汾(0��;1��)';

COMMENT ON COLUMN MATERIALSTOCK.STATID IS
'�Ƿ���ͳ��(0��;1��)';

COMMENT ON COLUMN MATERIALSTOCK.STATDATE IS
'ͳ��ʱ��';

COMMENT ON COLUMN MATERIALSTOCK.PARTTYPE IS
'part����';

COMMENT ON COLUMN MATERIALSTOCK.PARTNO IS
'part���';

COMMENT ON COLUMN MATERIALSTOCK.PARTVERSION IS
'part�汾';

COMMENT ON COLUMN MATERIALSTOCK.PARTNAME IS
'part����';

COMMENT ON COLUMN MATERIALSTOCK.PARTSPEC IS
'�ͺŹ��';

COMMENT ON COLUMN MATERIALSTOCK.ASSEMBLYSNO IS
'�������';

COMMENT ON COLUMN MATERIALSTOCK.COMPONENTSNO IS
'������';

COMMENT ON COLUMN MATERIALSTOCK.BATCHNO IS
'���κ�';

COMMENT ON COLUMN MATERIALSTOCK.AMOUNT IS
'����';

COMMENT ON COLUMN MATERIALSTOCK.UNIT IS
'������λ';

COMMENT ON COLUMN MATERIALSTOCK.PK_DEPTDOC IS
'���Ʋ���id';

COMMENT ON COLUMN MATERIALSTOCK.DEPTNAME IS
'���Ʋ�������';

COMMENT ON COLUMN MATERIALSTOCK.STATE IS
'״̬(�ƻ�/Ͷ��/�깤)';

COMMENT ON COLUMN MATERIALSTOCK.FIRSTBDATE IS
'�״μƻ���ʼʱ��';

COMMENT ON COLUMN MATERIALSTOCK.FIRSTEDATE IS
'�״μƻ����ʱ��';

COMMENT ON COLUMN MATERIALSTOCK.PLANBDATE IS
'�ƻ���ʼʱ��';

COMMENT ON COLUMN MATERIALSTOCK.PLANEDATE IS
'�ƻ����ʱ��';

COMMENT ON COLUMN MATERIALSTOCK.FACTBDATE IS
'ʵ�ʿ�ʼʱ��';

COMMENT ON COLUMN MATERIALSTOCK.FACTEDATE IS
'ʵ�����ʱ��';

COMMENT ON COLUMN MATERIALSTOCK.PK_PICKM_BID IS
'���ϼƻ���������';

COMMENT ON COLUMN MATERIALSTOCK.OUTWAREHOUSENO IS
'����ֿ����';

COMMENT ON COLUMN MATERIALSTOCK.OUTWAREHOUSENAME IS
'����ֿ�����';

COMMENT ON COLUMN MATERIALSTOCK.PLANNUM IS
'�ƻ���������';

COMMENT ON COLUMN MATERIALSTOCK.UNITRATION IS
'��λ����';

COMMENT ON COLUMN MATERIALSTOCK.RATIONNUM IS
'��������';

COMMENT ON COLUMN MATERIALSTOCK.OUTNUM IS
'�ۼƳ�������';

COMMENT ON COLUMN MATERIALSTOCK.WAITOUTNUM IS
'��������';

COMMENT ON COLUMN MATERIALSTOCK.DUEOUTNUM IS
'�ۼƱ���������';

COMMENT ON COLUMN MATERIALSTOCK.REDUCENUM IS
'������';

COMMENT ON COLUMN MATERIALSTOCK.FILTESTATE IS
'ɸѡ״̬';

COMMENT ON COLUMN MATERIALSTOCK.REQDATE IS
'��������';

COMMENT ON COLUMN MATERIALSTOCK.REQTIME IS
'����ʱ��';

COMMENT ON COLUMN MATERIALSTOCK.SUBSTID IS
'�����Ϣ';

COMMENT ON COLUMN MATERIALSTOCK.MEMO IS
'���ϼƻ���ע';

COMMENT ON COLUMN MATERIALSTOCK.UPARTNO IS
'װ��ͼ��';

COMMENT ON COLUMN MATERIALSTOCK.EARLYOUTNUM IS
'��ϵͳ������';

COMMENT ON COLUMN MATERIALSTOCK.REMAINPLANNUM IS
'ʣ��������';

COMMENT ON COLUMN MATERIALSTOCK.LOCKEDNUM IS
'��������';

COMMENT ON COLUMN MATERIALSTOCK.REQNUM IS
'ȱ������';

COMMENT ON COLUMN MATERIALSTOCK.FINISHEDID IS
'�Ƿ����(0��;1��)';

COMMENT ON COLUMN MATERIALSTOCK.OVERDUEID IS
'�Ƿ�����(0��;1��)';

COMMENT ON COLUMN MATERIALSTOCK.DAYEXPIREID IS
'�����Ƿ���(0��;1��)';

COMMENT ON COLUMN MATERIALSTOCK.DAYOVERDUEID IS
'�����Ƿ�����(0��;1��)';

COMMENT ON COLUMN MATERIALSTOCK.STOCKREADYID IS
'�⹺���Ƿ�����(0��;1��)';

COMMENT ON COLUMN MATERIALSTOCK.STOCKREASON1 IS
'�⹺��������ԭ��1���⹺���Ƿ��޿����δ�ɹ�(0��;1��)';

COMMENT ON COLUMN MATERIALSTOCK.STOCKREASON2 IS
'�⹺��������ԭ��2���⹺���Ƿ��޿�浫�Ѳɹ�(0��;1��)';

COMMENT ON COLUMN MATERIALSTOCK.STOCKREASON3 IS
'�⹺��������ԭ��3���⹺���Ƿ��п�浫��ռ��(0��;1��)';

COMMENT ON COLUMN MATERIALSTOCK.BUYNUM IS
'�ɹ���;����';

COMMENT ON COLUMN MATERIALSTOCK.BUYPLAN IS
'�ɹ������ƻ�';

COMMENT ON COLUMN MATERIALSTOCK.ELSELOCKENUM IS
'����������';

COMMENT ON COLUMN MATERIALSTOCK.UNLOCKEDNUM IS
'δ������';

COMMENT ON COLUMN MATERIALSTOCK.STOCKNUM IS
'�ִ���';	
gatherid	 //�Ƿ�����	
/**
* @param args
*/
	
public static void main(String[] args) {
		// TODO Auto-generated method stub

}
}
