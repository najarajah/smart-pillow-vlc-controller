package ch.inf.usi.ubicomp.vlc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ParseVLCStatus {
	
	private DocumentBuilder db;
	private InputStream is;
	private Document dom;
	private Element root;
	
	public ParseVLCStatus(){
		try {
			is = new ByteArrayInputStream(VLCController.getStatus().getBytes("UTF-8"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			dom = db.parse(is);
			root = dom.getDocumentElement();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Element getElement(String elementName){
		reloadXML();
		NodeList nl = root.getElementsByTagName(elementName);
		Element el = (Element)nl.item(0);
		//System.out.println(el.getFirstChild().getNodeValue());
		return el;
		
	}
	
	public int getVolume(){
		Element el = getElement("volume");
		return Integer.parseInt(el.getFirstChild().getNodeValue());	
	}
	
	public int getLenght(){
		Element el = getElement("length");
		return Integer.parseInt(el.getFirstChild().getNodeValue());	
	}
	
	private String getState(){
		Element el = getElement("state");
		return el.getFirstChild().getNodeValue();	
	}
	
	public boolean isPlaying() {
		return getState().equals("playing");
	}
	
	public boolean isStopped(){
		return getState().equals("stop");
	}
	
	public int getPosition(){
		Element el = getElement("position");
		return Integer.parseInt(el.getFirstChild().getNodeValue());
	}
	
	public void reloadXML(){
		try {
			is = new ByteArrayInputStream(VLCController.getStatus().getBytes("UTF-8"));
			dom = db.parse(is);
			root = dom.getDocumentElement();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	/*public static void main(String[] args){
		
		ParseVLCStatus vlcParser = new ParseVLCStatus("<root> <volume>200</volume></root>");
		System.out.println(vlcParser.getVolume());
		
	}*/
	
	
}

