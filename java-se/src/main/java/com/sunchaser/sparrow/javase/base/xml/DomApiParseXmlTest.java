package com.sunchaser.sparrow.javase.base.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 使用DOM API解析xml
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/10
 */
public class DomApiParseXmlTest {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        InputStream is = DomApiParseXmlTest.class.getResourceAsStream("/xml/server.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(is);

        System.out.println("Root Element: " + document.getNodeName());

        Node firstChild = document.getFirstChild();
        Element element = (Element) firstChild;

        System.out.println("Element: " + firstChild.getNodeName() + ", Attribute: " +
                element.getAttributes().getNamedItem("port") + ", " +
                element.getAttributes().getNamedItem("shutdown")
        );

        NodeList serviceList = element.getElementsByTagName("Service");
        for (int i = 0; i < serviceList.getLength(); i++) {
            Element service = (Element) serviceList.item(i);
            System.out.println("Element: " + service.getNodeName() + ", Attribute: " + service.getAttributes().getNamedItem("name"));

            NodeList connectorList = service.getElementsByTagName("Connector");
            for (int j = 0; j < connectorList.getLength(); j++) {
                Node connector = connectorList.item(j);
                System.out.println("Element: " + connector.getNodeName() + ", Attribute: " +
                        connector.getAttributes().getNamedItem("port") + ", " +
                        connector.getAttributes().getNamedItem("protocol") + ", " +
                        connector.getAttributes().getNamedItem("connectionTimeout") + ", " +
                        connector.getAttributes().getNamedItem("redirectPort") + ", "
                );
            }

            NodeList engineList = service.getElementsByTagName("Engine");
            for (int k = 0; k < engineList.getLength(); k++) {
                Element engine = (Element) engineList.item(k);
                System.out.println("Element: " + engine.getNodeName() + ", Attribute: " +
                        engine.getAttributes().getNamedItem("name") + ", " +
                        engine.getAttributes().getNamedItem("defaultHost") + ", "
                );

                NodeList hostList = engine.getElementsByTagName("Host");
                for (int m = 0; m < hostList.getLength(); m++) {
                    Node host = hostList.item(m);
                    System.out.println("Element: " + host.getNodeName() + ", Attribute: " +
                            host.getAttributes().getNamedItem("name") + ", " +
                            host.getAttributes().getNamedItem("appBase") + ", " +
                            host.getAttributes().getNamedItem("unpackWARs") + ", " +
                            host.getAttributes().getNamedItem("autoDeploy") + ", "
                    );
                }
            }
        }
    }
}
