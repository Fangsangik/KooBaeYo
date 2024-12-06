package com.example.koobaeyo.stores.repository;

import com.example.koobaeyo.stores.entity.Store;
import com.example.koobaeyo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 가게 리포지토리
 */
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("SELECT COUNT(s) FROM Store s WHERE s.owner.id = :ownerId AND s.isOpen = TRUE")
    Long countOpenStoresByOwner(@Param("ownerId") Long ownerId);

    @Query("SELECT s FROM Store s WHERE s.name =:storeName AND (s.owner = :owner OR s.isOpen = TRUE)")
    List<Store> findAllByNameUsingOwner(@Param("storeName") String storeName, @Param("owner") User owner);

    @Query("SELECT s FROM Store s WHERE s.name = :storeName AND s.isOpen = TRUE")
    List<Store> findAllByNameIsOpen(@Param("storeName") String storeName);

    @Query("SELECT s FROM Store s WHERE s.id = :storeId AND s.isOpen = TRUE")
    Optional<Store> findByIdIsOpen(@Param("storeId") Long storeId);

    //가게 상세 조회에서 사장님도 오픈되있거나 내 가게이거나인 가게조회
    @Query("SELECT s FROM Store s WHERE s.id = :storeId AND (s.owner = :owner OR s.isOpen = TRUE)")
    Optional<Store> findByIdUsingOwner(@Param("storeId") Long storeId, @Param("owner") User owner);
}
