package org.sunchaser.lombok.test;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/12
 */
@Getter
@Setter
public class GetterAndSetterTest {

    @Getter
    private String getField;

    @Setter
    private String setField;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private String otherField;

    @Getter
    @Setter
    private final String fs = null;
}
