package com.sunchaser.sparrow.javaee.graphql.resolver.bank.query;

import com.sunchaser.sparrow.javaee.graphql.context.CustomGraphQLContext;
import com.sunchaser.sparrow.javaee.graphql.domain.bank.BankAccount;
import com.sunchaser.sparrow.javaee.graphql.domain.bank.Currency;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/6
 */
@Component
@Slf4j
public class BankAccountResolver implements GraphQLQueryResolver {

    public BankAccount bankAccount(UUID id, DataFetchingEnvironment environment) {
        log.info("Retrieving bank account id: {}", id);

        /*
        Client clientA = Client.builder()
                .id(UUID.randomUUID())
                .firstName("SunChaser")
                .lastName("LiLu1")
                .build();

        Client clientB = Client.builder()
                .id(UUID.randomUUID())
                .firstName("SunChaser")
                .lastName("LiLu2")
                .build();

        clientA.setClient(clientB);
        clientB.setClient(clientA);

        Client client = Client.builder()
                .id(UUID.randomUUID())
                .firstName("SunChaser")
                .lastName("LiLu")
                .build();
         **/

        CustomGraphQLContext context = environment.getContext();

        log.info("User ID: {}", context.getUserId());

        return BankAccount.builder()
                .id(id)
                .currency(Currency.USD)
                .build();
    }
}
