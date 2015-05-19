package logicbeans.technicsprocess;

import java.util.ArrayList;
import java.util.HashMap;

import org.jdom.Element;

public class XMLProcessTechnicsWay extends XMLProcess {
	public static final String ARTBFNUM = "artbfnum"; // ���ձ��ݱ���
	public static final String ZDBFSL = "zdbfsl"; // ������󱸷ݼ���
	public static final String ARTCATEGORY = "artcategory"; // ��������,���� 0������ 1
	public static final String BBLX = "bblx"; // �汾���ͣ���Ч�汾 0����Ч�汾 1
	public static final String DESIGNERID = "designerid"; // ���ʦ
	public static final String HEADGCBM = "gcbm"; // ����
	public static final String GYLXLX = "gylxlx"; // ����·������
	public static final String MEMO = "memo"; // ��ע
	public static final String PK_CORP = "pk_corp"; // ��˾
	public static final String SL = "sl"; // ����
	public static final String WLBM = "wlbm"; // ���ϱ���
	public static final String WLMC = "wlmc"; // ��������
	public static final String VERSION = "version"; // �汾��
	public static final String HEADDR = "dr"; // ɾ����־
	public static final String TH = "th"; // ͼ��
	public static final String ZDY1 = "zdy1"; // ���ϱ���
	public static final String ZDY2 = "zdy2"; // �����ƺ�
	public static final String ZDY3 = "zdy3"; // ���ϱ�׼��
	public static final String ZDY4 = "zdy4"; // ��������

	public static final String ZDY5 = "zdy5"; // ���Ƴ���
	public static final String ZT = "zt"; // ״̬
	public static final String ZYX1 = "zyx1";
	public static final String ZYX2 = "zyx2";
	public static final String ZYX3 = "zyx3";
	public static final String ZYX4 = "zyx4";
	public static final String ZYX5 = "zyx5";

	public static final String Zdy11 = "zdy11";
	public static final String Zdy12 = "zdy12";
	public static final String Zdy13 = "zdy13";
	public static final String Zdy14 = "zdy14";
	public static final String Zdy15 = "zdy15";
	public static final String Zdy16 = "zdy16";
	public static final String Zdy17 = "zdy17";
	public static final String Zdy18 = "zdy18";
	public static final String Zdy19 = "zdy19";
	public static final String Zdy20 = "zdy20";

	public static final String DOCPATH = "zdy19"; // �����ļ�·��
	public static final String DOCNAME = "zdy20"; // �����ļ�����
	public static final String SFWQWX="zdy13";//�Ƿ���ȫ��Э
	public static final String PRIMARYTYPE = "primarytype";
    
	public static final String CHECKMODE = "checkmode"; // �׼췽ʽ
	public static final String GXH = "gxh"; // �����
	public static final String GYMS = "gyms"; // �������ƣ�������룩
	public static final String GZZXBM = "gzzxbm"; // �������ı���
	public static final String GZZXMC = "gzzxmc"; // ������������
	public static final String GZZXBMID = "gzzxbmid"; // �������ı���ID
	public static final String WORKTYPE = "pk_work_type"; // ���ֱ���
	public static final String BODYDR = "dr"; // ɾ����־
	public static final String EDATE = "edate"; // ʧЧʱ��
	public static final String BODYGCBM = "gcbm"; // ����
	public static final String BLFS = "blfs"; // ���Ϸ�ʽ
	public static final String GYFS = "gyfs"; // �������
	public static final String HH = "hh"; // �к�
	public static final String GZWLBM = "wlbm"; // ��װ���ϱ���
	public static final String GZWLMC = "wlmc"; // ��װ��������
	public static final String XHSL = "xhsl"; // ��װ����
	public static final String BODYZYX1 = "zyx1";
	public static final String BODYZYX2 = "zyx2";
	public static final String BODYZYX3 = "zyx3";
	public static final String BODYZYX4 = "zyx4";
	public static final String BODYZYX5 = "zyx5";
	public static final String PK_JOBMNGFIL = "pk_jobmngfil";
	public static final String PK_PRODUCE = "pk_produce";
	public static final String PK_RGID = "pk_rgid";
	public static final String PK_RTID = "pk_rtid";
	public static final String SFMR = "sfmr";
	public static final String SFQS = "sfqs";
	public static final String ZBSJ = "zbsj"; // ׼��ʱ��
	public static final String JGSJ = "jgsj"; // �ӹ�ʱ��
	public static final String gdzqpl = "gdzqpl"; // ÿ������
	public static final String sfgxjjd = "sfjjd"; // �Ƿ񽻽ӵ�

	public XMLProcessTechnicsWay() {
		super();
		ufinterface.setAttribute("billtype", "AX");
	}

	public final void addBill() {
		bill = new Element("bill");
		ufinterface.addContent(bill);
		billhead = new Element("billhead");
		billbody = new Element("billbody");
		bill.addContent(billhead);
		bill.addContent(billbody);

		billhead.addContent(new Element(ARTBFNUM));
		billhead.addContent(new Element(ZDBFSL));
		billhead.addContent(new Element(ARTCATEGORY));
		billhead.addContent(new Element(BBLX));
		billhead.addContent(new Element(DESIGNERID));
		billhead.addContent(new Element(HEADGCBM));
		billhead.addContent(new Element(GYLXLX));
		billhead.addContent(new Element(MEMO));
		// billhead.addContent(new Element(PK_CORP));
		// billhead.addContent(new Element(PK_JOBMNGFIL));
		// billhead.addContent(new Element(PK_PRODUCE));
		// billhead.addContent(new Element(PK_RGID));
		// billhead.addContent(new Element(PK_RTID));
		billhead.addContent(new Element(SFMR));
		billhead.addContent(new Element(SFQS));
		billhead.addContent(new Element(SL));
		billhead.addContent(new Element(TH));
		billhead.addContent(new Element(VERSION));
		billhead.addContent(new Element(WLBM));
		billhead.addContent(new Element(WLMC));
		billhead.addContent(new Element(ZDY1));
		billhead.addContent(new Element(ZDY2));
		// billhead.addContent(new Element(ZDY3));
		billhead.addContent(new Element(ZDY4));
		// billhead.addContent(new Element(ZDY5));
		billhead.addContent(new Element(sfgxjjd));
		// billhead.addContent(new Element(ZYX1));
		// billhead.addContent(new Element(ZYX2));
		// billhead.addContent(new Element(ZYX3));
		billhead.addContent(new Element(DOCPATH));
		billhead.addContent(new Element(DOCNAME));
		billhead.addContent(new Element(HEADDR));
	    billhead.addContent(new Element(SFWQWX));
		// billhead.addContent(new Element(gdzqpl));
		// billhead.addContent(new Element(ZBSJ));

		billhead.getChild(ARTCATEGORY).setText("0");
		billhead.getChild(BBLX).setText("0");
		billhead.getChild(SL).setText("1");
		billhead.getChild(HEADDR).setText("0");

	}

	public void process(HashMap part) {
		addBill();
		processBillHead(part);
		processBillBody(null);
	}

	public void processBillBody(ArrayList body) {
		if (body == null || body.size() == 0) {
			return;
		}
		HashMap oneRow = null;
		for (int i = 0; i < body.size(); i++) {
			Element entry = new Element("entry");
			billbody.addContent(entry);
			oneRow = (HashMap) body.get(i);

			entry.addContent(new Element(CHECKMODE));
			entry.getChild(CHECKMODE).setText((String) oneRow.get(CHECKMODE));

			entry.addContent(new Element(GXH));
			entry.getChild(GXH).setText((String) oneRow.get(GXH));
			entry.addContent(new Element(GYMS));
			entry.getChild(GYMS).setText((String) oneRow.get(GYMS));
			entry.addContent(new Element(GZZXBM));
			entry.getChild(GZZXBM).setText((String) oneRow.get(GZZXBM));
			// entry.addContent(new Element(GZZXBMID));
			entry.addContent(new Element(GZZXMC));
			entry.getChild(GZZXMC).setText((String) oneRow.get(GZZXMC));
			entry.addContent(new Element(Zdy11));
			entry.addContent(new Element(Zdy12));
			entry.addContent(new Element(Zdy13));
			entry.addContent(new Element(Zdy14));
			entry.addContent(new Element(Zdy15));
			entry.addContent(new Element(Zdy16));
			entry.addContent(new Element(Zdy17));
			entry.addContent(new Element(Zdy18));
			entry.addContent(new Element(Zdy19));
			entry.addContent(new Element(Zdy20));

			entry.getChild(Zdy11).setText((String) oneRow.get(Zdy11));
			entry.getChild(Zdy12).setText((String) oneRow.get(Zdy12));
			entry.getChild(Zdy13).setText((String) oneRow.get(Zdy13));
			entry.getChild(Zdy14).setText((String) oneRow.get(Zdy14));
			entry.getChild(Zdy15).setText((String) oneRow.get(Zdy15));
			entry.getChild(Zdy16).setText((String) oneRow.get(Zdy16));
			entry.getChild(Zdy17).setText((String) oneRow.get(Zdy17));
			entry.getChild(Zdy18).setText((String) oneRow.get(Zdy18));
			entry.getChild(Zdy19).setText((String) oneRow.get(Zdy19));
			entry.getChild(Zdy20).setText((String) oneRow.get(Zdy20));

			entry.addContent(new Element(XHSL));
			entry.getChild(XHSL).setText((String) oneRow.get(XHSL));

			entry.addContent(new Element(PRIMARYTYPE));
			entry.getChild(PRIMARYTYPE).setText(
					(String) oneRow.get(PRIMARYTYPE));

			entry.addContent(new Element(ZDY1));
			entry.getChild(ZDY1).setText((String) oneRow.get(ZDY1));

			// entry.addContent(new Element(BODYZYX4));
			// entry.addContent(new Element(BODYZYX5));
			entry.addContent(new Element(WORKTYPE));
			entry.getChild(WORKTYPE).setText((String) oneRow.get(WORKTYPE));
			entry.addContent(new Element(BODYDR));
			entry.getChild(BODYDR).setText("0");

			entry.addContent(new Element(ZBSJ));
			entry.addContent(new Element(JGSJ));
			entry.addContent(new Element(gdzqpl));
			entry.getChild(ZBSJ).setText((String) oneRow.get(ZBSJ));
			entry.getChild(JGSJ).setText((String) oneRow.get(JGSJ));
			entry.getChild(gdzqpl).setText((String) oneRow.get(gdzqpl));
			entry.addContent(new Element(ZDY4));
			entry.getChild(ZDY4).setText((String) oneRow.get(ZDY4));
			entry.addContent(new Element(sfgxjjd));
			if (oneRow.get(sfgxjjd) == null) {
				entry.getChild(sfgxjjd).setText("N");
			} else {
				entry.getChild(sfgxjjd).setText((String) oneRow.get(sfgxjjd));
			}
		}
		return;
	}

	public void processBillHead(HashMap part) {

		billhead.getChild(ARTBFNUM).setText((String) part.get(ARTBFNUM));
		billhead.getChild(ZDBFSL).setText((String) part.get(ZDBFSL));
		billhead.getChild(WLBM).setText((String) part.get(WLBM));
		billhead.getChild(WLMC).setText((String) part.get(WLMC));
		billhead.getChild(VERSION).setText((String) part.get(VERSION));
		billhead.getChild(DESIGNERID).setText((String) part.get(DESIGNERID));
		billhead.getChild(ZDY1).setText((String) part.get(ZDY1));
		billhead.getChild(ZDY4).setText((String) part.get(ZDY4));
		billhead.getChild(ZDY2).setText((String) part.get(ZDY2));
		billhead.getChild(DOCPATH).setText((String) part.get(DOCPATH));
		billhead.getChild(DOCNAME).setText((String) part.get(DOCNAME));
		billhead.getChild(SFWQWX).setText((String) part.get(SFWQWX));
	}

}