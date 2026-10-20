package com.findit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.findit.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByTypeIgnoreCase(String type);

    List<Item> findByStatusIgnoreCase(String status);

    List<Item> findByCategoryIgnoreCase(String category);

    List<Item> findByTypeIgnoreCaseAndStatusIgnoreCase(String type, String status);

    List<Item> findByTypeIgnoreCaseAndCategoryIgnoreCase(String type, String category);

    List<Item> findByUser_Id(Long userId);

    @Query("SELECT i FROM Item i WHERE " +
           "(:type IS NULL OR LOWER(i.type) = LOWER(:type)) AND " +
           "(:category IS NULL OR LOWER(i.category) = LOWER(:category)) AND " +
           "(:status IS NULL OR LOWER(i.status) = LOWER(:status)) AND " +
           "(:search IS NULL OR LOWER(i.itemName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "  OR LOWER(i.description) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "  OR LOWER(i.location) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Item> searchItems(
            @Param("type") String type,
            @Param("category") String category,
            @Param("status") String status,
            @Param("search") String search);
}
