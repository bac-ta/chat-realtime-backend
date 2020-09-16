package com.dimagesharevn.app.models.rests.request;

import com.dimagesharevn.app.models.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomRequest {
    @NotBlank
    private String roomName;
    @NotBlank
    private String naturalName;
    private String description;
    private List<MemberDTO> members;
}
