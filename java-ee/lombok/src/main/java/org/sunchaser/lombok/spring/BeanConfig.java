package org.sunchaser.lombok.spring;

import org.springframework.context.annotation.Bean;
import org.sunchaser.lombok.spring.di.MovieFinder;
import org.sunchaser.lombok.spring.di.SimpleMovieLister;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/13
 */
public class BeanConfig {
    @Bean
    public MovieFinder movieFinder() {
        return new MovieFinder();
    }

    @Bean
    public SimpleMovieLister simpleMovieLister(MovieFinder movieFinder) {
        return new SimpleMovieLister(movieFinder);
    }
}
