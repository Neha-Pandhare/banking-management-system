
package com.fujitsu.banking_management.repository;

import com.fujitsu.banking_management.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository
        extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);
}
