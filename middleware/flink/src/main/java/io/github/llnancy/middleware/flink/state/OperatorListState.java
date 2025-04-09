package io.github.llnancy.middleware.flink.state;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * List 算子状态
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/26
 */
public class OperatorListState {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);

        DataStreamSource<Integer> dss = env.fromData(1, 2, 3, 4);

        dss.map(new MyCountMapFunction())
           .print();

        env.execute();
    }

    static class MyCountMapFunction implements MapFunction<Integer, Long>, CheckpointedFunction {

        private static final long serialVersionUID = 8376165969513252489L;

        /**
         * 本地变量
         */
        private Long count = 0L;

        /**
         * 算子状态
         */
        private ListState<Long> listState;

        @Override
        public Long map(Integer value) throws Exception {
            return ++count;
        }

        /**
         * 本地变量持久化：将本地变量拷贝至算子状态中，开启 checkpoint 时会调用。
         */
        @Override
        public void snapshotState(FunctionSnapshotContext context) throws Exception {
            System.out.println("snapshotState");
            // 清空算子状态
            listState.clear();
            // 将本地变量拷贝至算子状态中
            listState.add(count);
        }

        /**
         * 初始化本地变量：程序启动或恢复时，从状态中将数据恢复至本地变量
         */
        @Override
        public void initializeState(FunctionInitializationContext context) throws Exception {
            System.out.println("initializeState");
            // 从上下文中初始化算子状态
            listState = context.getOperatorStateStore()
                    // .getUnionListState(new ListStateDescriptor<>("count-list-state", Types.LONG)) // 联合列表状态
                    .getListState(new ListStateDescriptor<>("count-list-state", Types.LONG));
            if (context.isRestored()) {
                // 将算子状态拷贝至本地变量
                for (Long l : listState.get()) {
                    count += l;
                }
            }
        }
    }
}
