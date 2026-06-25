package com.example.manageCar.repository;

import com.example.manageCar.model.Account;
import com.example.manageCar.model.CTCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface accountRepository extends JpaRepository<Account,Integer> {
    @Query("""
        SELECT a
        FROM Account a
        WHERE a.userID = :userID
        """)
    List<Account> findDetailsByUserID(@Param("userID") Integer userID);

}
