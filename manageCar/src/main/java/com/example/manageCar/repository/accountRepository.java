package com.example.manageCar.repository;

import com.example.manageCar.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface accountRepository extends JpaRepository<Account,Integer> {
    @Query("""
        SELECT a
        FROM Account a
        WHERE a.userID = :userID
        """)
    List<Account> findDetailsByUserID(@Param("userID") Integer userID);

    Optional<Account> findByAccount(String account);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByNumberPhone(String numberPhone);

    boolean existsByAccount(String account);

    boolean existsByEmail(String email);

    boolean existsByNumberPhone(String numberPhone);
}
