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
@Table(name = "ofMessageArchive")
@AllArgsConstructor
@NoArgsConstructor
public class MessageArchive {
    @Id
    @Column
    private Long messageID;
    @Column
    private Long conversationID;
    @Column
    private String fromJID;
    @Column
    private String toJID;
    @Column
    private Long sentDate;
    @Column
    private String body;
    @Column
    private String stanza;

}
