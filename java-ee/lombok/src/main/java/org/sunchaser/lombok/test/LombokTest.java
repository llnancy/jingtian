package org.sunchaser.lombok.test;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/12
 */
public class LombokTest {
    public static void main(String[] args) {
        GetterAndSetterTest gas = new GetterAndSetterTest();
        gas.setSetField("setter");
        String getter = gas.getGetField();
        System.out.println(getter);
        ToStringTest tst = new ToStringTest();
        System.out.println(tst);
        AccessorsTest at = new AccessorsTest();
        at.setAcc("acc");
        at.setEss("ess");
        at.setOrs("ors");
        at.setOrs("ors")
                .setEss("ess")
                .setAcc("acc");
    }
}
