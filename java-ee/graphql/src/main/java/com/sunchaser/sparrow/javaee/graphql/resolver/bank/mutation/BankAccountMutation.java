package com.sunchaser.sparrow.javaee.graphql.resolver.bank.mutation;

import com.sunchaser.sparrow.javaee.graphql.domain.bank.BankAccount;
import com.sunchaser.sparrow.javaee.graphql.domain.bank.Currency;
import com.sunchaser.sparrow.javaee.graphql.domain.bank.input.CreateBankAccountInput;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/6
 */
@Component
@Slf4j
public class BankAccountMutation implements GraphQLMutationResolver {

    public BankAccount createBankAccount(CreateBankAccountInput input) {
        log.info("Creating bank account for {}", input.getFirstName());
        return BankAccount.builder()
                .id(UUID.randomUUID())
                .currency(Currency.USD)
                .build();
    }
}
