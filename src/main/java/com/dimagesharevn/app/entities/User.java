package com.dimagesharevn.app.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ofUser")
@Data
@Entity
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
}
