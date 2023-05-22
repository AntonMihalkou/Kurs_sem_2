package com.kurs.wweb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий пользователя системы.
 */
@Data
@Entity
public class User {
    /**
     * Идентификатор учетной записи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Имя пользователя учетной записи.
     */
    private String username;
    /**
     * Пароль учетной записи.
     */
    private String password;
    /**
     * Доступ учётной записи.
     */
    private boolean enabled;
    /**
     * Картинка учётной записи.
     */
    private String image;

    /**
     * Возвращает значение, указывающее, активен ли пользователь в системе.
     * @return {@code true}, если пользователь активен, {@code false} в противном случае.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Устанавливает значение, указывающее, активен ли пользователь в системе.
     * @param enabled значение, указывающее, активен ли пользователь в системе.
     */
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Список паролей, связанных с этим пользователем.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Password> passwords = new ArrayList<>();
}