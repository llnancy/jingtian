package com.sunchaser.sparrow.javaee.graphql.domain.bank;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
    LocalDate createdOn;
    ZonedDateTime createdAt;
}
