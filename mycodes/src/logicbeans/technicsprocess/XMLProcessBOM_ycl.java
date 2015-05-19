package logicbeans.technicsprocess;
import java.util.ArrayList;
import java.util.HashMap;

import org.jdom.Element;

public class XMLProcessBOM_ycl extends XMLProcess {
	public static final String BBLX = "bblx";		//版本类型，有效版本 0，无效版本 1
	public static final String BOMLX = "bomlx";	//BOM类型
	public static final String CLF = "clf";		//材料费
	public static final String CREATOR = "creator";	//创建者ID
	public static final String CREATORNAME = "creatorName";		//创建者名称
	public static final String DESIGNERID = "designerid";	//设计师

	public static final String MEMO = "memo";		//备注
	public static final String VERSION  = "version";	//版本号
	public static final String SL = "sl";		//数量
	public static final String WLBM = "wlbm";	//物料编码
	public static final String WLMC = "wlmc";		//物料名称
	
	public static final String ZXBM = "ZXBM";		//子项编码
	public static final String ZXMC = "ZXMC";	//子项名称

	public static final String DR = "dr";		//处理标志 0-新增，1修改，2删除
	
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
