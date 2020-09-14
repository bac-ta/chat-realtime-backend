package com.dimagesharevn.app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    private String sessionId;
    private String username;
    private String resource;
    private String node;
    private String sessionStatus;
    private String presenceStatus;
    private String presenceMessage;
    private int priority;
    private String hostAddress;
    private String hostName;
    private Long creationDate;
    private Long lastActionDate;
    private boolean secure;
}