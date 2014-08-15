import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DomParse {
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, SQLException {
		//parsing();
		sqliteQuery();
		}
	
	public static void parsing () throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, SQLException{
		File file = new File("F:\\ANDROÝD\\android_exercises\\SQLite_And_DOMParsing\\radios_2014August.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder db = dbFactory.newDocumentBuilder(); 
		Document document = db.parse(file); 
		document.getDocumentElement().normalize(); 
		NodeList liste = document.getElementsByTagName("r"); 
		
		for (int i = 0; i < liste.getLength(); i++) { 
			Node bag = liste.item(i); 
			if (bag.getNodeType()==Node.ELEMENT_NODE) { 
				Element eleman = (Element) bag; 
				/*Radio r = new Radio();
				r.setId( Integer.parseInt(eleman.getAttribute("i")));
				r.setName(eleman.getAttribute("n"));
				r.setUrl(eleman.getAttribute("l"));*/
				
				//ArrayList<Radio> listeSirket = new ArrayList<Radio>();
				//listeSirket.add(r);
				
				/*System.out.print( eleman.getAttribute("i")+" ");
				System.out.print( eleman.getAttribute("n")+" ");
				System.out.println( eleman.getAttribute("l")+" ");*/
				//sqliteInsert(Integer.parseInt(eleman.getAttribute("i")),eleman.getAttribute("n"),eleman.getAttribute("l"));
			}
		}
		
		
	}
	
	public static void sqliteInsert (int id, String name, String urlName) throws ClassNotFoundException, SQLException{
		Connection c = null;
		Statement stmt;
		ResultSet rs;
				
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:db/Radio.sqlite");
		c.setAutoCommit(false);
		stmt = c.createStatement();
		String sqlInsert ="INSERT INTO radio (id,NAME,URL) VALUES ("+ id+" ,'" + name +"', '" +urlName+ "');";
		stmt.executeUpdate(sqlInsert);
		
		
		stmt.close();
		c.commit();
		c.close();
	
	}

	public static void sqliteQuery () throws ClassNotFoundException, SQLException{
		ArrayList<Radio>liste = new ArrayList<Radio>();
		
		Connection c = null;
		Statement stmt;
		ResultSet rs;
				
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:db/Radio.sqlite");
		c.setAutoCommit(false);
		stmt = c.createStatement();
		String sqlQuery ="SELECT * FROM newRadioList; ";
		rs = stmt.executeQuery(sqlQuery);
		
		while (rs.next()) {
			Radio r1= new Radio();
			r1.setName(rs.getString("NAME"));
			r1.setUrl(rs.getString("URL"));
			liste.add(r1);
					
		}
		
		rs.close();
		stmt.close();
		c.close();
		
		for (int i = 0; i < liste.size(); i++) {
			System.out.print("Radyonun Adý :" + liste.get(i).getName()+ " ");
			System.out.println("Radyonun URL'si :" + liste.get(i).getUrl());
			
		}
		
	}
}
