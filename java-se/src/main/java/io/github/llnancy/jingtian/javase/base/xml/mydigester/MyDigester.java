package io.github.llnancy.jingtian.javase.base.xml.mydigester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Stack;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/19
 */
public class MyDigester extends DefaultHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyDigester.class);

    /*=======================stack methods start=======================*/
    private final Stack<Object> stack = new Stack<>();

    public <T> void push(T obj) {
        stack.push(obj);
    }

    @SuppressWarnings("unchecked")
    public <T> T pop() {
        return (T) stack.pop();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        return (T) stack.get(index);
    }

    @SuppressWarnings("unchecked")
    public <T> T peek() {
        return (T) stack.peek();
    }

    /**
     * peek指定位置的栈元素
     * @param n 0是栈顶元素，1是栈顶的前一个元素...
     * @param <T> 栈中元素类型
     * @return 位于n位置的栈中元素
     */
    @SuppressWarnings("unchecked")
    public <T> T peek(int n) {
        int index = (stack.size() - 1) - n;
        return (T) stack.get(index);
    }
    /*=======================stack methods end=======================*/

    private ProcessorFactory processorFactory = null;

    public ProcessorFactory getProcessorFactory() {
        return processorFactory;
    }

    public void setProcessorFactory(ProcessorFactory processorFactory) {
        this.processorFactory = processorFactory;
    }

    public void addProcessor(String qName, Processor processor) {
        processor.setMyDigester(this);
        getProcessorFactory().addProcessor(qName, processor);
    }

    public void addObjectCreate(String qName, String className) {
        addProcessor(qName, new ObjectCreateProcessor(className));
    }

    public void addSetProperties(String qName) {
        addProcessor(qName, new SetPropertiesProcessor());
    }

    public void addInvokeMethod(String qName, String methodName, String paramType) {
        addProcessor(qName, new InvokeMethodProcessor(methodName, paramType));
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        LOGGER.info("MyDigester.startDocument");
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        LOGGER.info("MyDigester.endDocument");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        /**
        if ("Foo".equals(qName)) {
            Foo foo = new Foo();
        } else if ("Bar".equals(qName)) {
            Bar bar = new Bar();
        }
        **/

        /**
        LOGGER.info("MyDigester.startElement qName={}", qName);
        if ("Foo".equals(qName)) {
            Foo foo = new Foo();
            stack.push(foo);
            LOGGER.info("stack push foo:{}", foo);
        } else if ("Bar".equals(qName)) {
            Bar bar = new Bar();
            for (int i = 0; i < attributes.getLength(); i++) {
                if ("name".equals(attributes.getQName(i))) {
                    bar.setName(attributes.getValue(i));
                }
            }
            Foo foo = (Foo) stack.get(0);
            foo.addBar(bar);
            stack.push(bar);
            LOGGER.info("stack push bar:{}", bar);
        }
         **/
        List<Processor> processorList = processorFactory.matchProcessor(qName);
        for (Processor processor : processorList) {
            try {
                processor.processStart(qName, attributes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        /**
        if ("Foo".equals(qName)) {
            Foo pop = (Foo) stack.pop();
            LOGGER.info("endElement, stack pop Foo:{}", pop);
        } else if ("Bar".equals(qName)) {
            Bar pop = (Bar) stack.pop();
            LOGGER.info("endElement, stack pop Bar:{}", pop);
        }
         **/
        List<Processor> processorList = processorFactory.matchProcessor(qName);
        for (Processor processor : processorList) {
            try {
                processor.processEnd(qName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }
}
