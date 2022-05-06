package com.sunchaser.sparrow.javaee.graphql.domain.bank;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/6
 */
@Builder
@Value
public class BankAccount {
    UUID id;
    Client client;
    Currency currency;
}
