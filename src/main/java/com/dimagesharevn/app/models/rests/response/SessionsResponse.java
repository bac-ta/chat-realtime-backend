package com.dimagesharevn.app.models.rests.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionsResponse {

    public List<SessionResponse> sessions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class SessionResponse {
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


}
