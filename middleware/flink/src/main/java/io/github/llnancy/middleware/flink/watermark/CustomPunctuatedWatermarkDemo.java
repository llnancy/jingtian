package io.github.llnancy.middleware.flink.watermark;

import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkOutput;

import java.util.Objects;

/**
 * 自定义标记水位线生成器
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2025/2/25
 */
public class CustomPunctuatedWatermarkDemo {

    public static class Event {

        private Long timestamp;

        private Boolean watermarkFlag;

        public Event() {
        }

        public Event(Long timestamp, Boolean watermarkFlag) {
            this.timestamp = timestamp;
            this.watermarkFlag = watermarkFlag;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public Boolean getWatermarkFlag() {
            return watermarkFlag;
        }

        public void setWatermarkFlag(Boolean watermarkFlag) {
            this.watermarkFlag = watermarkFlag;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Event event = (Event) o;
            return Objects.equals(timestamp, event.timestamp) && Objects.equals(watermarkFlag, event.watermarkFlag);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(timestamp);
            result = 31 * result + Objects.hashCode(watermarkFlag);
            return result;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "timestamp=" + timestamp +
                    ", watermarkFlag=" + watermarkFlag +
                    '}';
        }
    }

    public static class CustomPunctuatedWatermarkGenerator implements WatermarkGenerator<Event> {

        @Override
        public void onEvent(Event event, long eventTimestamp, WatermarkOutput output) {
            // 根据流事件数据携带的 Watermark 标记或根据自定义逻辑立即发出 Watermark
            if (event.getWatermarkFlag()) {
                output.emitWatermark(new Watermark(event.getTimestamp()));
            }
        }

        @Override
        public void onPeriodicEmit(WatermarkOutput output) {

        }
    }
}
