package com.dimagesharevn.app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryDTO {
    private String body;
    private Long sentDate;
}
