package com.dimagesharevn.app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryDTO {
    private Long messageID;
    private Long conversationID;
    private Long sentDate;
    private String body;
}
