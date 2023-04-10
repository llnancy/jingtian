package io.github.llnancy.jingtian.javase.base.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/17
 */
public class SAXParseXmlTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SAXParseXmlTest.class);

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        InputStream is = SAXParseXmlTest.class.getResourceAsStream("/xml/server.xml");
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(is, new DefaultHandler() {
            @Override
            public void startDocument() throws SAXException {
                super.startDocument();
                LOGGER.info("startDocument");
            }

            @Override
            public void endDocument() throws SAXException {
                super.endDocument();
                LOGGER.info("endDocument");
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);
                LOGGER.info("startElement: uri={}, localName={}, qName={}, attributes={}", uri, localName, qName, attributes);
                for (int i = 0; i < attributes.getLength(); i++) {// 循环输出Attr属性
                    LOGGER.info("attributes: localName={}, qName={}, type={}, uri={}, value={}",
                            attributes.getLocalName(i),
                            attributes.getQName(i),
                            attributes.getType(i),
                            attributes.getURI(i),
                            attributes.getValue(i)
                    );
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                super.endElement(uri, localName, qName);
                LOGGER.info("endElement: uri={}, localName={}, qName={}", uri, localName, qName);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                super.characters(ch, start, length);// 解析到字符
                LOGGER.info("characters: ch={}", new String(ch, start, length));
            }

            @Override
            public void error(SAXParseException e) throws SAXException {
                super.error(e);
                LOGGER.error("error", e);
            }
        });
    }
}
