package ru.practicum.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.category.model.Category;
import ru.practicum.user.model.User;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    List<User> findAllByIdIn(List<Long> ids, Pageable pageable);
    Optional<User> findByName(String name);
}
