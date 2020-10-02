package com.dimagesharevn.app.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RosterDTO {
    private List<RosterItemDTO> rosterItem;
}
