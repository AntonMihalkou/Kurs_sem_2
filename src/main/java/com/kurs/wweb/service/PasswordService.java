package com.kurs.wweb.service;

import com.kurs.wweb.model.Password;
import com.kurs.wweb.model.User;
import com.kurs.wweb.repository.PasswordRepository;
import com.kurs.wweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс PasswordService представляет сервис для работы с учетными записями с паролями.
 */
@Service
public class PasswordService {
    /**
     * Резозиторий для работы с паролями пользователей.
     */
    @Autowired
    private PasswordRepository passwordRepository;

    /**
     * Создает новый объект PasswordService с указанным репозиторием учетных записей с паролями.
     * @param passwordRepository Репозиторий учетных записей с паролями.
     */
    public PasswordService(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    /**
     * Добавляет новую учетную запись с паролем в базу данных.
     * @param password Учетная запись с паролем, которую нужно добавить в базу данных.
     */
    public void addPassword(Password password) {
        passwordRepository.save(password);
    }

    /**
     * Возвращает учетную запись с паролем по заданному идентификатору.
     * @param id Идентификатор учетной записи     с паролем.
     * @return Учетная запись с паролем с заданным идентификатором.
     */
    public Password getPassword(Long id) {
        return passwordRepository.findById(id).get();
    }

    /**
     * Возвращает список учетных записей с паролями для заданного пользователя.
     * @param userId Идентификатор пользователя, для которого нужно вернуть список учетных записей с паролями.
     * @return Список учетных записей с паролями для заданного пользователя.
     */
    public List<Password> listPasswordsByUserId(Long userId) {
        return passwordRepository.findByUserId(userId);
    }

    /**
     * Обновляет учетную запись с паролем в базе данных.
     * @param password Обновленная учетная запись с паролем.
     */
    public void editPassword(Password password) {
        passwordRepository.save(password);
    }

    /**
     * Удаляет учетную запись с паролем из базы данных.
     * @param id Идентификатор удаляемой учетной записи с паролем.
     */
    public void deletePassword(final long id) {
        Password password = passwordRepository.findById(id).get();
        User user = password.getUser();
        user.getPasswords().remove(password);
        passwordRepository.delete(password);
    }
}
