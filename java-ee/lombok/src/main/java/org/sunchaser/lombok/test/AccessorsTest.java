package org.sunchaser.lombok.test;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/13
 */
@Setter
@Accessors(chain = true)
public class AccessorsTest {
    private String acc;
    private String ess;
    private String ors;
}
