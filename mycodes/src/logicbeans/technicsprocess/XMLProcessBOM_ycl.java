package logicbeans.technicsprocess;
import java.util.ArrayList;
import java.util.HashMap;

import org.jdom.Element;

public class XMLProcessBOM_ycl extends XMLProcess {
	public static final String BBLX = "bblx";		//�汾���ͣ���Ч�汾 0����Ч�汾 1
	public static final String BOMLX = "bomlx";	//BOM����
	public static final String CLF = "clf";		//���Ϸ�
	public static final String CREATOR = "creator";	//������ID
	public static final String CREATORNAME = "creatorName";		//����������
	public static final String DESIGNERID = "designerid";	//���ʦ

	public static final String MEMO = "memo";		//��ע
	public static final String VERSION  = "version";	//�汾��
	public static final String SL = "sl";		//����
	public static final String WLBM = "wlbm";	//���ϱ���
	public static final String WLMC = "wlmc";		//��������
	
	public static final String ZXBM = "ZXBM";		//�������
	public static final String ZXMC = "ZXMC";	//��������

	public static final String DR = "dr";		//�����־ 0-������1�޸ģ�2ɾ��
	
	public XMLProcessBOM_ycl() {
        super();
        ufinterface.setAttribute("billtype","bom");
        ufinterface.setAttribute("sender","0001");
        
    }
    public void addBill(){
        bill = new Element("bill");
    	ufinterface.addContent(bill);
    	billhead = new Element("billhead");
    	billbody = new Element("billbody");
    	bill.addContent(billhead);
    	bill.addContent(billbody);
    	
        billhead.addContent(new Element(BBLX)); 
        billhead.addContent(new Element(BOMLX));
//       billhead.addContent(new Element(CLF));
        billhead.addContent(new Element(CREATOR));
//        billhead.addContent(new Element(CREATORNAME));
//        billhead.addContent(new Element(DESIGNERID));
//        billhead.addContent(new Element(MEMO));
        billhead.addContent(new Element(SL));
        billhead.addContent(new Element(WLBM));
        billhead.addContent(new Element(WLMC));
        billhead.addContent(new Element(VERSION));
        billhead.addContent(new Element(DR));
        
    	billhead.getChild(BBLX).setText("0");
    	billhead.getChild(BOMLX).setText("0");
		// billhead.getChild(VERSION).setText("1.0");
    	billhead.getChild(SL).setText("1");
    	billhead.getChild(DR).setText("0");
    }
	
	public void process(ArrayList partList){
    	processBillHead(null);
    	processBillBody(partList);
    }

    public void addBill(HashMap head,ArrayList body){
    	this.addBill();
    	processBillHead(head);
    	processBillBody(body);
    }
	
    public void processBillHead(HashMap head){
    	billhead.getChild(WLBM).setText((String)head.get(WLBM));
    	billhead.getChild(WLMC).setText((String)head.get(WLMC));
    	String version = (String)head.get(VERSION);
		//if (version == null || version.equals("1.0"))
		//	version = "1.1";
    	billhead.getChild(VERSION).setText(version);
    }

    public void processBillBody(ArrayList body){
    	if(body == null || body.size() == 0){
    		return;
    	}
    	HashMap oneRow = null;
    	for(int i = 0 ; i < body.size(); i++){
        	Element entry = new Element("entry");
        	billbody.addContent(entry);
        	oneRow = (HashMap)body.get(i);
        	
        	entry.addContent(new Element(ZXBM));
        	entry.getChild(ZXBM).setText((String)oneRow.get(ZXBM));
        	entry.addContent(new Element(ZXMC));
        	entry.getChild(ZXMC).setText((String)oneRow.get(ZXMC));
        	entry.addContent(new Element(SL));
        	entry.getChild(SL).setText((String)oneRow.get(SL));
			entry.addContent(new Element(DR));
        	entry.getChild(DR).setText("0");
    	}
    	return;
    }
 
}
