package com.dimagesharevn.app.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ofMucRoom")
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @Column(length = 50)
    private String name;
    private String naturalName;
    private String description;
}
