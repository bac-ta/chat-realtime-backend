package com.dimagesharevn.app.entities;

import com.dimagesharevn.app.enumerations.UserType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ofUser")
@Data
public class User {
    @Column
    @Id
    private String username;
    @Column
    private String encryptedPassword;
    @Column
    private String name;
    @Column
    private String email;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserType userType = UserType.NORMAL;
}
