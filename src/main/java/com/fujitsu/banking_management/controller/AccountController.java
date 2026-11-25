
package com.fujitsu.banking_management.controller;

import com.fujitsu.banking_management.dto.AccountRequest;
import com.fujitsu.banking_management.dto.AccountResponse;
import com.fujitsu.banking_management.entity.Account;
import com.fujitsu.banking_management.service.AccountService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Create Account
    @PostMapping
    public AccountResponse createAccount(
            @Valid @RequestBody AccountRequest request) {

        Account account = new Account(
                request.getAccountNumber(),
                request.getAccountHolderName(),
                request.getBalance()
        );

        return accountService.createAccount(account);
    }

    // Get All Accounts
    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Get Account By ID
    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    // Deposit Money
    @PostMapping("/{id}/deposit")
    public AccountResponse depositMoney(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {

        return accountService.depositMoney(id, amount);
    }

    // Withdraw Money
    @PostMapping("/{id}/withdraw")
    public AccountResponse withdrawMoney(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {

        return accountService.withdrawMoney(id, amount);
    }

    // Delete Account
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {

        accountService.deleteAccount(id);

        return "Account deleted successfully";
    }
}
