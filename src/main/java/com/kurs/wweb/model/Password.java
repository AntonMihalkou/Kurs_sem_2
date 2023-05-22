package com.kurs.wweb.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Класс Password представляет учетную запись с паролем, связанную с пользователем.
 */
@Data
@Entity
public class Password {

    /**
     * Идентификатор учетной записи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Название учетной записи.
     */
    private String name;

    /**
     * Имя пользователя учетной записи.
     */
    private String username;

    /**
     * Пароль учетной записи.
     */
    private String password;

    /**
     * Пользователь, связанный с учетной записью с паролем.
     */
    @ManyToOne
    private User user;

    /**
     * Устанавливает пользователя, связанного с учетной записью с паролем.
     * @param user Пользователь, связанный с учетной записью с паролем.
     */
    public void setUser(User user) {
        this.user = user;
    }

}