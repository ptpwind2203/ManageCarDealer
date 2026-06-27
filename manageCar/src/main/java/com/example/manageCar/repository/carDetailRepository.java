package com.example.manageCar.repository;

import com.example.manageCar.model.CTCar;
import com.example.manageCar.model.CTCarID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface carDetailRepository extends JpaRepository<CTCar, CTCarID> {

    @Query("""
        SELECT c
        FROM CTCar c
        JOIN FETCH c.color
        JOIN FETCH c.version
        WHERE c.carID = :carID
    """)
    List<CTCar> findCTCarDetail(@Param("carID") int carID);

    @Query("""
        SELECT c
        FROM CTCar c
        JOIN FETCH c.color
        JOIN FETCH c.version
        WHERE c.carID = :carID
    """)
    List<CTCar> findByCarIDWithDetails(@Param("carID") int carID);

    @Query("""
        SELECT c
        FROM CTCar c
        WHERE c.carID = :carID
    """)
    List<CTCar> findDetailsByCarID(@Param("carID") Integer carID);

    Optional<CTCar> findByCarIDAndColorIDAndVersionID(
            Integer carID,
            Integer colorID,
            Integer versionID
    );

    boolean existsByCarIDAndColorIDAndVersionID(
            int carID,
            int colorID,
            int versionID
    );

    List<CTCar> findByCarID(Integer carID);
}