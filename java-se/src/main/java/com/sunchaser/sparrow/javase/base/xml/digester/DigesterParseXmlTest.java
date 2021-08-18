package com.sunchaser.sparrow.javase.base.xml.digester;

import com.alibaba.fastjson.JSON;
import com.sunchaser.sparrow.javase.base.xml.SAXParseXmlTest;
import org.apache.commons.digester3.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/18
 */
public class DigesterParseXmlTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DigesterParseXmlTest.class);
    private MyServer myServer;

    public DigesterParseXmlTest() throws IOException, SAXException {
        Digester digester = createDigester();
        InputStream is = SAXParseXmlTest.class.getResourceAsStream("/xml/server.xml");
        digester.push(this);
        digester.parse(is);
    }

    public MyServer getMyServer() {
        return myServer;
    }

    public DigesterParseXmlTest setMyServer(MyServer myServer) {
        this.myServer = myServer;
        return this;
    }

    public static void main(String[] args) throws IOException, SAXException {
        DigesterParseXmlTest digesterParseXmlTest = new DigesterParseXmlTest();
        LOGGER.info("{}", JSON.toJSONString(digesterParseXmlTest.getMyServer()));
    }

    private Digester createDigester() {
        Digester digester = new Digester();
        // 设置false：不需要进行XML的DTD规则校验
        digester.setValidating(false);
        // 解析到Server节点时创建一个MyServer对象
        digester.addObjectCreate("Server",
                                "com.sunchaser.sparrow.javase.base.xml.digester.MyServer");
        // 根据Server节点的Attr属性调用MyServer类中对应的setter方法
        digester.addSetProperties("Server");
        // 将Server节点对应的对象（即上面将创建的MyServer对象）作为入参传递给栈顶元素的setMyServer方法
        // 这里的栈顶元素是下面调用digester.push(this)方法推入的当前类对象DigesterParseXmlTest
        digester.addSetNext("Server",
                        "setMyServer",
                            "com.sunchaser.sparrow.javase.base.xml.digester.MyServer");

        // 解析到Server节点下的Service节点时，创建一个MyService对象
        digester.addObjectCreate("Server/Service",
                                "com.sunchaser.sparrow.javase.base.xml.digester.MyService");
        // 根据Server/Service节点的Attr属性调用MyService类中对应的setter方法
        digester.addSetProperties("Server/Service");
        // 将Server/Service节点对应的对象（即上面将创建的MyService对象）作为入参传递给栈顶元素（MyServer）的addMyService方法
        digester.addSetNext("Server/Service",
                        "addMyService",
                            "com.sunchaser.sparrow.javase.base.xml.digester.MyService");

        // 解析到Server节点下的Service节点下的Connector节点时，创建一个MyConnector对象
        digester.addObjectCreate("Server/Service/Connector",
                                "com.sunchaser.sparrow.javase.base.xml.digester.MyConnector");
        // 根据Server/Service/Connector节点的Attr属性调用MyConnector类中对应的setter方法
        digester.addSetProperties("Server/Service/Connector");
        // 将Server/Service/Connector节点对应的对象（即上面将创建的MyConnector对象）作为入参传递给栈顶元素（MyService）的addMyConnector方法
        digester.addSetNext("Server/Service/Connector",
                "addMyConnector",
                "com.sunchaser.sparrow.javase.base.xml.digester.MyConnector");

        // 解析到Server节点下的Service节点下的Engine节点时，创建一个MyEngine对象
        digester.addObjectCreate("Server/Service/Engine",
                                "com.sunchaser.sparrow.javase.base.xml.digester.MyEngine");
        // 根据Server/Service/Engine节点的Attr属性调用MyEngine类中对应的setter方法
        digester.addSetProperties("Server/Service/Engine");
        // 将Server/Service/Engine对应的对象（即上面将创建的MyEngine对象）作为入参传递给栈顶元素（MyService）的setMyEngine方法
        digester.addSetNext("Server/Service/Engine",
                            "setMyEngine",
                                "com.sunchaser.sparrow.javase.base.xml.digester.MyEngine");
        return digester;
    }
}
