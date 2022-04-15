package com.sunchaser.sparrow.javase.shell;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.sunchaser.sparrow.javase.shell.ShellCommandExecutor.Result;
import static com.sunchaser.sparrow.javase.shell.ShellCommandExecutor.ShellCommandExecutors;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/2/21
 */
@Slf4j
public class ShellCommandExecutorTest {
    public static void main(String[] args) {
        List<String> commands = Lists.newArrayList(
                "acorn@^8.6.0",
                "antd@^4.16.13"
        );
        for (String command : commands) {
            Result execute = ShellCommandExecutors.execute("npm install " + command);
            execute.print();
        }
    }
}
