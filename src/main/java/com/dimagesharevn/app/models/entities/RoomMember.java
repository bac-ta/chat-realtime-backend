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
@Table(name = "ofMucMember")
@AllArgsConstructor
@NoArgsConstructor
public class RoomMember implements Serializable {
    @Id
    private Long roomID;
    @Column(length = 50)
    private String jid;
}
