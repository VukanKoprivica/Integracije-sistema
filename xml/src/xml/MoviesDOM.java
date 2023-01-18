package xml;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class MoviesDOM {
	public String sadrzaj;

    public Document loadDocument() {

        // Get a factory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {

            // Get a new instance of document builder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the file and return DOM representation
            return builder.parse("C:\\Users\\vukan\\eclipse-workspace-Integracije\\xml\\src\\Movies3.xml");

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

    public String saveDocument(Document dom) {
        try {

            // Get a registry
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

            // Get a new instance of loader/saver
            DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            LSSerializer writer = impl.createLSSerializer();

            // Write the whole document in a string and print it
            
            return sadrzaj = writer.writeToString(dom);
           // FileWriter out = new FileWriter("C:\\Users\\vukan\\eclipse-workspace-Integracije\\xml\\src\\Movies4.xml");
           // out.write(sadrzaj);
           // out.close();
            

        // Print any errors
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        
        return "prazan";
    }

    private void printMovies(Document dom) {

        // Get the root element
        Element root = dom.getDocumentElement();

        // Find all "movie" elements and print them
        NodeList nl = root.getElementsByTagName("movie");
        for (int i = 0; i < nl.getLength(); i++) {
            printMovie((Element) nl.item(i));
        }

    }

    private void printMovie(Element item) {

        // Print title and year
        String year = item.getAttribute("year");
        Element title = (Element) item.getElementsByTagName("title").item(0);
        System.out.println();
        System.out.println(title.getTextContent() + " (" + year + ")");

        // Print genre
        Element genre = (Element) item.getElementsByTagName("genre").item(0);
        System.out.println("Genre: " + genre.getTextContent());

        // Print cast
        NodeList nla = item.getElementsByTagName("actor");
        System.out.print("Cast: ");
        System.out.print(((Element)nla.item(0)).getTextContent());
        
        for(int i = 1;i<nla.getLength();i++) {
        	System.out.print(", ");
        	System.out.print(((Element)nla.item(i)).getTextContent());
        	
        	
        }
        System.out.println();

        // Print plot
        Element plot = (Element) item.getElementsByTagName("plot").item(0);
        System.out.println("Plot: " + plot.getTextContent());

    }
    
    public void update(Element item,String change) {
    	Element genre = (Element) item.getElementsByTagName("genre").item(0);
    	
    	genre.setTextContent(change);
    	
    	
    	
    	
    	
    }
    
    public void deleteMovie(Document dom,int index) {
    	Element root = dom.getDocumentElement();
    	NodeList nl = root.getElementsByTagName("movie");
    	nl.item(index).setNodeValue(null);
    	
    }
    
    public void addMovie(Document dom) {
    	Element root = dom.getDocumentElement();
    	
    	Element movie =dom.createElement("movie");
    	Element title =dom.createElement("title");
    	title.appendChild(dom.createTextNode("Naslov"));
    	Element genre =dom.createElement("genre");
    	genre.appendChild(dom.createTextNode("Action")); 
    	movie.appendChild(dom.createTextNode("\n\t\t"));
    	movie.appendChild(title);
    	movie.appendChild(dom.createTextNode("\n\t\t"));
    	movie.appendChild(genre);
    	Element actor =dom.createElement("actor");
    	actor.appendChild(dom.createTextNode("Jovana Stanojev"));
    	movie.appendChild(dom.createTextNode("\n\t\t"));
    	movie.appendChild(actor);
    	Element actor2 =dom.createElement("actor");
    	actor2.appendChild(dom.createTextNode("Vukan Koprivica"));
    	movie.appendChild(dom.createTextNode("\n\t\t"));
    	movie.appendChild(actor2);
    	Element plot =dom.createElement("plot");
    	plot.appendChild(dom.createTextNode("Sex"));
    	movie.appendChild(dom.createTextNode("\n\t\t"));
    	movie.appendChild(plot);
    	
    	
    	
    	movie.appendChild(dom.createTextNode("\n\t"));
    	
    	movie.setAttribute("year", "2022");
    	root.appendChild(dom.createTextNode("\n\t"));
    	
    	root.appendChild(movie);
    	root.appendChild(dom.createTextNode("\n\n"));
    }

    public static void main(String[] args) {
        MoviesDOM app = new MoviesDOM();
        Document dom = app.loadDocument();
        
        if (dom != null) {
        	//app.addMovie(dom);
        	app.printMovies(dom);
 //       	Element root = dom.getDocumentElement();
      //  	NodeList nl = root.getElementsByTagName("movie");
        //	app.deleteMovie(dom, 2);
        	//app.update(((Element)nl.item(7)), "Porn");
            
            app.saveDocument(dom);
        }
    }
}
