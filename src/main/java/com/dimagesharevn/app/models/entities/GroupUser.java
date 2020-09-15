package com.dimagesharevn.app.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ofGroupUser")
@AllArgsConstructor
@NoArgsConstructor
public class GroupUser implements Serializable {
    @Column(length = 50)
    @Id
    private String groupName;
    @Column(length = 100)
    @Id
    private String username;
    @Id
    private byte administrator;

}
