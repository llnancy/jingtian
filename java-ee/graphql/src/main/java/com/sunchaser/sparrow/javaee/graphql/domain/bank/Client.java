package com.sunchaser.sparrow.javaee.graphql.domain.bank;

import lombok.Builder;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/6
 */
@Setter
@Builder
public class Client {
    UUID id;
    String firstName;
    List<String> middleNames;
    String lastName;
}
