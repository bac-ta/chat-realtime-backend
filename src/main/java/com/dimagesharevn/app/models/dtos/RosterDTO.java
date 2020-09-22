package com.dimagesharevn.app.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RosterDTO {
    private List<RosterItem> rosterItem;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class RosterItem {
        private String jid;
        private String subscriptionType;
        private List<String> groups;
    }
}
