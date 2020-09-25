package com.dimagesharevn.app.models.dtos;

import com.dimagesharevn.app.models.rests.request.ChatRoomRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoomDTO extends ChatRoomRequest {
    private boolean persistent = true;
    private boolean publicRoom = true;
}
