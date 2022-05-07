package com.sunchaser.sparrow.javaee.graphql.domain.bank.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/5/6
 */
@Data
public class CreateBankAccountInput {
    @NotBlank
    private String firstName;
    private Integer age;
}
