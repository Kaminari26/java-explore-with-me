package ru.practicum.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.category.model.Category;

import org.springframework.data.domain.Pageable;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Page<Category> findAll(Pageable pageable);
}