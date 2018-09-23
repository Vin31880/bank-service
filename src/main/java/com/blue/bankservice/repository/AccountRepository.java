package com.blue.bankservice.repository;

import com.blue.bankservice.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountTypeAndCustomer_CustomerId(String accountType, Long customerId);
}
