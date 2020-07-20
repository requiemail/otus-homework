package ru.otus.homework.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
