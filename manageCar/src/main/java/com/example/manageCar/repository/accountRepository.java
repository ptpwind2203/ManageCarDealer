package com.example.manageCar.repository;

import com.example.manageCar.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface accountRepository extends JpaRepository<Account,Integer> {
}
