package com.dimagesharevn.app.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RosterItemDTO {
    private String jid;
    private short subscriptionType;
    private List<String> groups;
}
