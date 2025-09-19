package com.rido.shop.repository;

import com.rido.shop.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findPageBy(Pageable page);
    Page<Item> findByTitleContains(String searchText, Pageable page);

    @Query(value = "SELECT * FROM item WHERE MATCH(title) AGAINST(:searchText IN BOOLEAN MODE)",
           countQuery = "SELECT COUNT(*) FROM item WHERE MATCH(title) AGAINST(:searchText IN BOOLEAN MODE)",
           nativeQuery = true)
    Page<Item> findItemFulltext(@Param("searchText") String searchText, Pageable page);
}
