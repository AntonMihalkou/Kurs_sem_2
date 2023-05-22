package com.kurs.wweb.repository;

import com.kurs.wweb.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для паролей пользователей.
 */
@Repository
public interface PasswordRepository extends CrudRepository<Password, Long>, JpaRepository<Password, Long> {

    /**
     * Возвращает пароль с указанным идентификатором.
     * @param aLong идентификатор пароля.
     * @return объект Optional, содержащий пароль с указанным идентификатором, если он существует, иначе пустой объект Optional.
     */
    @Override
    Optional<Password> findById(Long aLong);

    /**
     * Возвращает список паролей для пользователя с указанным идентификатором.
     * @param userId идентификатор пользователя.
     * @return список паролей для пользователя с указанным идентификатором.
     */
    List<Password> findByUserId(Long userId);
}