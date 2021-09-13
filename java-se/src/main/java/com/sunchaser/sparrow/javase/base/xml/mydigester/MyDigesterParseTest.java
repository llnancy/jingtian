package com.sunchaser.sparrow.javase.base.xml.mydigester;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/19
 */
public class MyDigesterParseTest {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        InputStream is = MyDigesterParseTest.class.getResourceAsStream("/xml/FooBar.xml");
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();

        MyDigester myDigester = new MyDigester();

        // 策略工厂
        ProcessorFactory pf = ProcessorFactory.newInstance();

        /**
        // Foo策略
        FooProcessor fooProcessor = new FooProcessor();
        fooProcessor.setMyDigester(myDigester);
        pf.addProcessor("Foo", fooProcessor);

        // Bar策略
        BarProcessor barProcessor = new BarProcessor();
        barProcessor.setMyDigester(myDigester);
        pf.addProcessor("Bar", barProcessor);
        **/

        // 设置策略工厂
        myDigester.setProcessorFactory(pf);

        // 指定策略
        myDigester.addObjectCreate("Foo", "com.sunchaser.sparrow.javase.base.xml.mydigester.Foo");
        myDigester.addObjectCreate("Bar", "com.sunchaser.sparrow.javase.base.xml.mydigester.Bar");
        myDigester.addSetProperties("Bar");
        myDigester.addInvokeMethod("Bar", "addBar", "com.sunchaser.sparrow.javase.base.xml.mydigester.Bar");

        // 进行解析
        saxParser.parse(is, myDigester);
    }
}
