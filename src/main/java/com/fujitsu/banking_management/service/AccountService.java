
package com.fujitsu.banking_management.service;

import com.fujitsu.banking_management.dto.AccountResponse;
import com.fujitsu.banking_management.exception.ResourceNotFoundException;
import com.fujitsu.banking_management.entity.Account;
import com.fujitsu.banking_management.repository.AccountRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create Account
    public AccountResponse createAccount(Account account) {

        Account savedAccount = accountRepository.save(account);

        return convertToResponse(savedAccount);
    }

    // Get All Accounts
    public List<AccountResponse> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        return accounts.stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Get Account By ID
    public AccountResponse getAccountById(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with ID: " + id));

        return convertToResponse(account);
    }

    // Deposit Money
    public AccountResponse depositMoney(Long id, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with ID: " + id));

        account.setBalance(account.getBalance().add(amount));

        Account savedAccount = accountRepository.save(account);

        return convertToResponse(savedAccount);
    }

    // Withdraw Money
    public AccountResponse withdrawMoney(Long id, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with ID: " + id));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        Account savedAccount = accountRepository.save(account);

        return convertToResponse(savedAccount);
    }

    // Delete Account
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with ID: " + id));

        accountRepository.delete(account);
    }

    // Convert Entity to DTO
    public AccountResponse convertToResponse(Account account) {

        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountHolderName(),
                account.getBalance()
        );
    }
}
