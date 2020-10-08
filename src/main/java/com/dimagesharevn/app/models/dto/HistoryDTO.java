package com.dimagesharevn.app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryDTO {
    private Long messageID;
    private Long conversationID;
    private Long sentDate;
    private String userNameFrom;
    private String userNameTo;
    private String body;
}
