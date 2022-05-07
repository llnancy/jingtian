package com.sunchaser.sparrow.javaee.graphql.resolver.bank.mutation;

import com.sunchaser.sparrow.javaee.graphql.domain.bank.BankAccount;
import com.sunchaser.sparrow.javaee.graphql.domain.bank.Currency;
import com.sunchaser.sparrow.javaee.graphql.domain.bank.input.CreateBankAccountInput;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.SelectedField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/6
 */
@Component
@Slf4j
@Validated
public class BankAccountMutation implements GraphQLMutationResolver {

    public BankAccount createBankAccount(@Valid CreateBankAccountInput input, DataFetchingEnvironment environment) {
        log.info("Creating bank account for {}", input.getFirstName());

        Set<String> requestedFields = environment.getSelectionSet()
                .getFields()
                .stream()
                .map(SelectedField::getName)
                .collect(Collectors.toSet());

        if (environment.getSelectionSet().contains("id")) {
            // do special stuff
        }

        return BankAccount.builder()
                .id(UUID.randomUUID())
                .currency(Currency.USD)
                .createdAt(ZonedDateTime.now())
                .createdOn(LocalDate.now())
                .build();
    }
}
