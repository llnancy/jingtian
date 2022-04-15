package org.sunchaser.lombok.test;

import lombok.Data;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/12
 */
@Data(staticConstructor = "of")
public class DataTest {
    private String s;
    private String c;
}
