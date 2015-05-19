package logicbeans.technicsprocess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XMLProcess {
	Document doc = null;
	Element ufinterface;
	Element bill,billhead,billbody;
	
    public XMLProcess() {
    	ufinterface = new Element("ufinterface");
        ufinterface.setAttribute("roottag","bill");
        ufinterface.setAttribute("billtype","BOM");
        ufinterface.setAttribute("replace","Y");
        ufinterface.setAttribute("receiver","0001");
        ufinterface.setAttribute("sender","0001");
        ufinterface.setAttribute("isexchange","Y");
        ufinterface.setAttribute("filename","bom.xml");
        ufinterface.setAttribute("proc","add");
                
        doc = new Document(ufinterface);
    }
    
    public String getXML(){
        Format format = Format.getCompactFormat();
        format.setEncoding("gb2312");
        format.setIndent("    ");
        format.setLineSeparator("\r\n");
        format.setTextMode(Format.TextMode.NORMALIZE);
        XMLOutputter output = new XMLOutputter(format);
        return output.outputString(doc);
    }
    
    public boolean outputFile(String fileName){
    	boolean isSuccess = false;
    	
    	File outFile = new File(fileName);
    	if(outFile.exists())	outFile.delete();
    	ufinterface.setAttribute("filename",fileName);
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
			out.write(getXML());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		isSuccess = true;
        return isSuccess;
    }
    
}