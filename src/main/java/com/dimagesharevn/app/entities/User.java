package com.dimagesharevn.app.entities;

import com.dimagesharevn.app.enumerations.UserType;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "User name")
    private String username;
    @Column
    @ApiModelProperty(notes = "Password after encrypted")
    private String encryptedPassword;
    @Column
    @ApiModelProperty(notes = "Name of user")
    private String name;
    @Column
    @ApiModelProperty(notes = "Email of user")
    private String email;
    @Column(nullable = false, name = "type")
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(notes = "Type of user")
    private UserType userType = UserType.NORMAL;
}
