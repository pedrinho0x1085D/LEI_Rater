/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package android.com.pedrojose.rater.business;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *
 * @author PedroJosé
 */
public class GPXParser {

    private File fileToParse;

    public GPXParser(String filename) {
        this.fileToParse = new File(filename);
    }

    public ArrayList<GPXInstance> parse() {
        try {
            ArrayList<GPXInstance> toRet = new ArrayList<>();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileToParse);
            NodeList nList = doc.getElementsByTagName("trkpt");
            for (int counter = 0; counter < nList.getLength(); counter++){
                Node nNode = nList.item(counter);
                Element eElement = (Element) nNode;
                String tLat = eElement.getAttribute("lat");
                double dLat = Double.parseDouble(tLat);
                String tLon = eElement.getAttribute("lon");
                double dLon = Double.parseDouble(tLon);
                String tEle = eElement.getElementsByTagName("ele").item(0).getTextContent();
                double dEle = Double.parseDouble(tEle);
                toRet.add(new GPXInstance(dLat, dLon, dEle));
            } 
            return toRet;
        } catch(Exception e){}
        return new ArrayList<>();
    }
}
