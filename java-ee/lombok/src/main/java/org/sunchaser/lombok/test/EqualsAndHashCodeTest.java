package org.sunchaser.lombok.test;

import lombok.EqualsAndHashCode;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/12
 */
@EqualsAndHashCode(exclude = {"ex"})
public class EqualsAndHashCodeTest {
    private String equal;
    private String hc;
    transient String tr;
    private String ex;
}
