
package com.fujitsu.banking_management.dto;

import java.math.BigDecimal;

public class AccountResponse {

    private Long id;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;

    public AccountResponse(Long id, String accountNumber,
            String accountHolderName, BigDecimal balance) {

        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
