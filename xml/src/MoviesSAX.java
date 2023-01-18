import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MoviesSAX extends DefaultHandler {

    private void parseDocument() {

        // Get a factory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {

            // Get a new instance of parser
            SAXParser parser = factory.newSAXParser();

            // Parse the file and also register this class for call backs
            parser.parse("src/movies3.xml", this);

        // Print any errors
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private List<String> path = new ArrayList<String>();
    private String year;
    private boolean firstActor;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        // Add new note to the path
        path.add(qName);

        // Set movie fields
        if (qName.equals("movie")) {
            year = attributes.getValue("year");
            firstActor = true;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        // Remove current node from the path
        path.remove(path.size() - 1);

        // Clear movie attributes 
        if (qName.equals("movie")) {
            year = null;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        // Get current node and text content
        String node = path.get(path.size() - 1);
        String text = new String(ch, start, length);

        // Print title and year
        if (node.equals("title")) {
            System.out.println();
            System.out.println(text + "(" + year +")");

        // Print genre
        } else if (node.equals("genre")) {
            System.out.println("Genre: " + text);

        // Print cast
        } else if (node.equals("actor")) {
            if (firstActor) {
                System.out.print("Cast: ");
                firstActor = false;
            } else {
                System.out.print(", ");
            }
            System.out.print(text);

        // Print plot
        } else if (node.equals("plot")) {
            System.out.println();
            System.out.println("Plot: " + text);

        // Ignore other elements
        } else {
        }

    }

    public static void main(String[] args) {
        MoviesSAX app = new MoviesSAX();
        app.parseDocument();
    }
}
