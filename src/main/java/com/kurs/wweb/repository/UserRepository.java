package com.kurs.wweb.repository;

import com.kurs.wweb.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для пользователей системы.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

   /**
    * Возвращает пользователя с указанным именем пользователя.
    * @param username имя пользователя.
    * @return объект Optional, содержащий пользователя с указанным именем, если такой пользователь существует, иначе пустой объект Optional.
    */
   Optional<User> findByUsername(String username);
}