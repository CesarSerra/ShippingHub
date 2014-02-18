package com.java102.cesar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ReadXML 
{
	public static ArrayList<Person> readFromXML(String file)
	{
		try {
			File xmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			ArrayList<Person> personList = new ArrayList<Person>();
			
			NodeList nList = doc.getElementsByTagName("Person");
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);
				
				String firstName = "";
				String lastName = "";
				String address = "";
				String city = "";
				String state = "";
				String zip = "";
				String date = "";
				NodeList childs = nNode.getChildNodes();
				for(int tempLoop = 0; tempLoop< childs.getLength(); tempLoop++)
				{

					if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("firstname"))
					{
						firstName = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("lastname"))
					{
						lastName = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("address"))
					{
						address = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("city"))
					{
						city = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("state"))
					{
						state = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("zip"))
					{
						zip = childs.item(tempLoop).getTextContent();
					}
					else if(childs.item(tempLoop).getNodeName().equalsIgnoreCase("date"))
					{
						date = childs.item(tempLoop).getTextContent();
					}				
				}
				
				Address newAddress = new Address(address, city, state, zip);
				Person newPerson = new Person(firstName, lastName, date, newAddress);
			
				personList.add(newPerson);
			}
			
			return personList;
					
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return null;
	}
	
	public static boolean commitToFile(ArrayList<Person> people, String filePath) 
	{

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			Document personDoc;
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			personDoc = docBuilder.newDocument();

			Element rootElement = personDoc.createElement("People");
			
			final int count = people.size();
			for (int i = 0; i < count; ++i) {
				final Person person = people.get(i);
				
				Element personElement = personDoc.createElement("Person");
				
				
				Element firstNameElement = personDoc.createElement("FirstName");
                firstNameElement.setTextContent(person.getFirstName());
                
				Element lastNameElement = personDoc.createElement("LastName");
				lastNameElement.setTextContent(person.getLastName());
                
				Element addressElement = personDoc.createElement("Address");
				addressElement.setTextContent(person.getAddress().getAddress());
                
				Element cityElement = personDoc.createElement("City");
				cityElement.setTextContent((person.getAddress().getCity()));
				
				Element stateElement = personDoc.createElement("State");
				stateElement.setTextContent(person.getAddress().getState());
				
				Element zipElement = personDoc.createElement("Zip");
				zipElement.setTextContent(person.getAddress().getZip());
				
				Element dateElement = personDoc.createElement("Date");
				dateElement.setTextContent(person.getDate());
                
                
				personElement.appendChild(firstNameElement);
				personElement.appendChild(lastNameElement);
				personElement.appendChild(addressElement);
				personElement.appendChild(cityElement);
				personElement.appendChild(stateElement);
				personElement.appendChild(zipElement);
				personElement.appendChild(dateElement);
				rootElement.appendChild(personElement);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(rootElement);
			
			StreamResult result = new StreamResult(new File(filePath));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);

		} catch (Exception e) {
			return false;
		}

		return true;
	}

}