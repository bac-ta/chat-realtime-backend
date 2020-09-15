package com.dimagesharevn.app.models.rests.response;

import com.dimagesharevn.app.models.entities.Group;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResponse {
    private List<UserFindingResponse> userResponses;
    private List<Group> groupResponses;
}
