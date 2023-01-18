package xml;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class DOMPARSER {

	private List<String> psovke;
	private Document dom;
	private String path="C:\\Users\\vukan\\eclipse-workspace-Integracije\\ponovoKlk\\src\\xml\\reci.xml";
	
	
	
	public DOMPARSER() {
		dom=loadDocument();
		this.psovke=new ArrayList<String>();
		
		ubaciUListu();
	}


	public void ubaciUListu() {
		Element root = dom.getDocumentElement();
		NodeList nl = root.getElementsByTagName("rec");
		for(int i = 0;i<nl.getLength();i++) {
			this.psovke.add(nl.item(i).getTextContent().toLowerCase());
		}
		
	}
	
	public void SaveDocument() {
		
		try {

            // Get a registry
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

            // Get a new instance of loader/saver
            DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            LSSerializer writer = impl.createLSSerializer();

            // Write the whole document in a string and print it
            String sadrzaj = writer.writeToString(this.dom);
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path),StandardCharsets.UTF_16BE);
            out.write(sadrzaj);
            out.close();
            

        // Print any errors
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	public synchronized boolean addRec(String name,String message) {
		Element root = dom.getDocumentElement();
		NodeList nl = root.getElementsByTagName("rec");
		for(int i=0;i<nl.getLength();i++) {
			if((nl.item(i).getTextContent().equalsIgnoreCase(message))) {
				return false;
			}
		}
		Element nova =dom.createElement("rec");
		nova.setAttribute("nadimak", name);
		nova.appendChild(dom.createTextNode(message.toLowerCase()));
		root.appendChild(nova);
		root.appendChild(dom.createTextNode("\n"));
		this.psovke.add(message.toLowerCase());
		SaveDocument();
		return true;
		
	}
	public List<String> getPsovke(){
		return this.psovke;
	}
	
	public synchronized boolean deleteRec(String message) {
		
		Element root = dom.getDocumentElement();
		NodeList nl = root.getElementsByTagName("rec");
		for(int i=0;i<nl.getLength();i++) {
			if((nl.item(i).getTextContent().equalsIgnoreCase(message))) {
				root.removeChild(nl.item(i).getNextSibling());
				root.removeChild(nl.item(i));
				this.psovke.remove(message.toLowerCase());
				SaveDocument();
				return true;
			}
		}
		return false;
	}

	public Document loadDocument() {

        // Get a factory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {

            // Get a new instance of document builder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the file and return DOM representation
            return builder.parse(path);

        // Print any errors
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
            return null;
        } catch (SAXException ex) {
            ex.printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
