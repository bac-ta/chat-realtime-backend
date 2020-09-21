package com.dimagesharevn.app.models.rests.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResponse {
    private List<UserFindingResponse> userResponses;
    private List<RoomResponse> roomResponses;
}
