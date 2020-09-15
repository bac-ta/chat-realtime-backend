package com.dimagesharevn.app.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "ofGroupProp")
@AllArgsConstructor
@NoArgsConstructor
public class GroupProp implements Serializable {
    @Id
    @Column(length = 50)
    private String groupName;
    @Id
    @Column(length = 100)
    private String name;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String propValue;
}
