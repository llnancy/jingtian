package io.github.llnancy.jingtian.javase.base.xml.mydigester;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/8/22
 */
public class ProcessorFactory {
    private final Map<String, List<Processor>> importHfMap = Maps.newHashMap();

    public void addProcessor(String qName, Processor processor) {
        List<Processor> processorList = matchProcessor(qName);
        if (processorList == null || processorList.size() == 0) {
            processorList = new ArrayList<>();
        }
        processorList.add(processor);
        importHfMap.put(qName, processorList);
    }

    public void addProcessorList(String qName, List<Processor> processorList) {
        List<Processor> mpl = matchProcessor(qName);
        if (mpl == null || mpl.size() == 0) {
            mpl = new ArrayList<>();
        }
        mpl.addAll(processorList);
        importHfMap.put(qName, mpl);
    }

    public List<Processor> matchProcessor(String qName) {
        return importHfMap.get(qName);
    }

    public static ProcessorFactory newInstance() {
        return new ProcessorFactory();
    }
}
