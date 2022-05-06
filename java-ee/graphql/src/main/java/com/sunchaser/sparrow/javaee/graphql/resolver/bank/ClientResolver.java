package com.sunchaser.sparrow.javaee.graphql.resolver.bank;

import com.sunchaser.sparrow.javaee.graphql.domain.bank.BankAccount;
import com.sunchaser.sparrow.javaee.graphql.domain.bank.Client;
import graphql.GraphQLException;
import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/6
 */
@Slf4j
@Component
public class ClientResolver implements GraphQLResolver<BankAccount> {

    public DataFetcherResult<Client> client(BankAccount bankAccount) {
        log.info("Requesting client data for bank account id {}", bankAccount.getId());

        // throw new GraphQLException("Client unavailable");
        // throw new RuntimeException("Spring exception can not connect to database: (sql select *)");

        return DataFetcherResult.<Client>newResult()
                .data(Client.builder()
                        .id(UUID.randomUUID())
                        .firstName("SunChaser")
                        .lastName("LiLu")
                        .build()
                )
                .error(new GenericGraphQLError("Could not get sub-client id"))
                .build();
    }
}
