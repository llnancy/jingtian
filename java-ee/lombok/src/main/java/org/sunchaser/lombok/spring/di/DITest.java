package org.sunchaser.lombok.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.sunchaser.lombok.spring.BeanConfig;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/13
 */
public class DITest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
        Object bean = applicationContext.getBean("simpleMovieLister");
        System.out.println(bean);
    }

}
