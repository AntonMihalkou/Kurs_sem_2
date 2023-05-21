package com.kurs.wweb.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    @ManyToOne
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

}