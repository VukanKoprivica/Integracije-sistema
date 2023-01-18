package xml;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class XMLDOM {

	private List<String> sadrzaj;
	private String path="C:\\Users\\vukan\\eclipse-workspace-Integracije\\IntegracijeKlk\\src\\xml\\reci.xml";
	private Document dom;
	public XMLDOM() {
		sadrzaj=new ArrayList<String>();
		dom=loadDocument();
		parseToList();
	}
	
	private Document loadDocument() {
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			return builder.parse(path);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void parseToList() {
		
		Element root = dom.getDocumentElement();
		NodeList nl = root.getElementsByTagName("rec");
		for(int i =0;i<nl.getLength();i++) {
			
			sadrzaj.add(nl.item(i).getTextContent().toLowerCase());
		}
		
	}
	
	public synchronized void addRec(String ime,String rec) {
		Element root = dom.getDocumentElement();
		Element recE = dom.createElement("rec");
		recE.setAttribute("nadimak",ime.toLowerCase());
		recE.appendChild(dom.createTextNode(rec.toLowerCase()));
		root.appendChild(recE);
		sadrzaj.add(rec.toLowerCase());
		SaveDocument();
		
		
	}
	
	public synchronized boolean deleteRec(String rec) {
		Element root = dom.getDocumentElement();
		NodeList nl = dom.getElementsByTagName("rec");
		for(int i =0;i<nl.getLength();i++) {
			if(nl.item(i).getTextContent().equalsIgnoreCase(rec)) {
				
				root.removeChild((Element)nl.item(i));
				sadrzaj.remove(rec.toLowerCase());
				SaveDocument();
				
				return true;
				
				
			}
			
		}
		
		
		
		return false;
	}
	
	public List<String> getListu(){
		return this.sadrzaj;
	}
	
	private String SaveDocument() {
		
		try {
			DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
			LSSerializer write = impl.createLSSerializer();
			
			String text = write.writeToString(dom);
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),StandardCharsets.UTF_16BE);
			out.write(text);
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	
}
