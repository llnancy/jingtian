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

    public MyServer getMyServer() {
        return myServer;
    }

    public void setMyServer(MyServer myServer) {
        this.myServer = myServer;
    }

    public static void main(String[] args) throws IOException, SAXException {
        DigesterParseXmlTest dpxt = new DigesterParseXmlTest();
        dpxt.parse();
        LOGGER.info("{}", JSON.toJSONString(dpxt.getMyServer()));
    }

    private void parse() throws IOException, SAXException {
        // 创建Digester对象
        Digester digester = createDigester();
        // 获取xml文件的输入流
        InputStream is = DigesterParseXmlTest.class.getResourceAsStream("/xml/server.xml");
        // 将当前类压入Digester的对象栈栈顶
        digester.push(this);
        // 执行解析
        digester.parse(is);
    }

    private Digester createDigester() {
        Digester digester = new Digester();
        // 设置false：不需要进行XML的DTD规则校验
        digester.setValidating(false);
        // 解析到Server节点时创建一个MyServer对象，然后压入栈顶
        digester.addObjectCreate("Server",
                "com.sunchaser.sparrow.javase.base.xml.digester.MyServer");
        // 根据Server节点的Attr属性调用MyServer类中对应的setter方法
        digester.addSetProperties("Server");
        // 将栈顶元素（即上面将创建的MyServer对象）作为入参传递给栈顶的下一个元素的setMyServer方法并调用该方法
        // 这里的栈顶的下一个元素是后面调用digester.push(this)方法推入的当前类对象DigesterParseXmlTest
        digester.addSetNext("Server",
                "setMyServer",
                "com.sunchaser.sparrow.javase.base.xml.digester.MyServer");

        // 解析到Server节点下的Service节点时，创建一个MyService对象，然后压入栈顶
        digester.addObjectCreate("Server/Service",
                "com.sunchaser.sparrow.javase.base.xml.digester.MyService");
        // 根据Server/Service节点的Attr属性调用MyService类中对应的setter方法
        digester.addSetProperties("Server/Service");
        // 将栈顶元素（即上面将创建的MyService对象）作为入参传递给栈顶的下一个元素（MyServer）的addMyService方法并调用该方法
        digester.addSetNext("Server/Service",
                "addMyService",
                "com.sunchaser.sparrow.javase.base.xml.digester.MyService");

        // 解析到Server节点下的Service节点下的Connector节点时，创建一个MyConnector对象，然后压入栈顶
        digester.addObjectCreate("Server/Service/Connector",
                "com.sunchaser.sparrow.javase.base.xml.digester.MyConnector");
        // 根据Server/Service/Connector节点的Attr属性调用MyConnector类中对应的setter方法
        digester.addSetProperties("Server/Service/Connector");
        // 将栈顶元素（即上面将创建的MyConnector对象）作为入参传递给栈顶的下一个元素（MyService）的addMyConnector方法并调用该方法
        digester.addSetNext("Server/Service/Connector",
                "addMyConnector",
                "com.sunchaser.sparrow.javase.base.xml.digester.MyConnector");

        // 这里由于会解析到Connector元素的结束标签，MyConnector元素会出栈。栈顶元素变为MyService

        // 解析到Server节点下的Service节点下的Engine节点时，创建一个MyEngine对象，然后压入栈顶
        digester.addObjectCreate("Server/Service/Engine",
                "com.sunchaser.sparrow.javase.base.xml.digester.MyEngine");
        // 根据Server/Service/Engine节点的Attr属性调用MyEngine类中对应的setter方法
        digester.addSetProperties("Server/Service/Engine");
        // 将栈顶元素（即上面将创建的MyEngine对象）作为入参传递给栈顶的下一个元素（MyService）的setMyEngine方法并调用该方法
        digester.addSetNext("Server/Service/Engine",
                "setMyEngine",
                "com.sunchaser.sparrow.javase.base.xml.digester.MyEngine");

        // 解析到Server节点下的Service节点下的Engine节点下的Host节点时，创建一个MyHost对象，然后压入栈顶
        digester.addObjectCreate("Server/Service/Engine/Host",
                "com.sunchaser.sparrow.javase.base.xml.digester.MyHost");
        // 根据Server/Service/Engine/Host节点的Attr属性调用MyHost类中对应的setter方法
        digester.addSetProperties("Server/Service/Engine/Host");
        // 将栈顶元素（即上面将创建的MyHost对象）作为入参传递给栈顶的下一个元素（MyEngine）的addMyHost方法并调用该方法
        digester.addSetNext("Server/Service/Engine/Host",
                "addMyHost",
                "com.sunchaser.sparrow.javase.base.xml.digester.MyHost");
        // 这里会依次解析到元素的结束标签：</Host>、</Engine>、</Service>、</Server>，栈中元素会依次进行出栈
        return digester;
    }
}
