package com.sunchaser.sparrow.javase.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * FileChannel TransferTo
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/6/10
 */
@Slf4j
public class TestFileChannelTransferTo {

    public static void main(String[] args) {
        try (FileChannel from = new FileInputStream(Utils.path("from.txt")).getChannel();
             FileChannel to = new FileOutputStream(Utils.path("to.txt")).getChannel()) {
            // 底层使用操作系统的零拷贝
            // 一次最多传输2G数据
            // from.transferTo(0, from.size(), to);
            long size = from.size();
            long transfer = size;
            while (transfer > 0) {
                LOGGER.info("position:{}, transfer:{}", (size - transfer), transfer);
                transfer -= from.transferTo((size - transfer), transfer, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
