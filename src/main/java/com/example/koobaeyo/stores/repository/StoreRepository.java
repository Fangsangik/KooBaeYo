package com.example.koobaeyo.stores.repository;

import com.example.koobaeyo.stores.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("SELECT COUNT(s) FROM Store s WHERE s.owner.id = :ownerId AND s.isOpen = TRUE")
    Long countOpenStoresByOwner(@Param("ownerId") Long ownerId);

    List<Store> findAllByName(String storeName);
}
