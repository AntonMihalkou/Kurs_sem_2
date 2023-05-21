package com.kurs.wweb.repository;

import com.kurs.wweb.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordRepository extends CrudRepository<Password, Long>,JpaRepository<Password, Long> {

    @Override
    Optional<Password> findById(Long aLong);

    List<Password> findByUserId(Long userId);
}