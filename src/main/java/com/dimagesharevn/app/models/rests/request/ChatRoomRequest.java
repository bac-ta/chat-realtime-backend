package com.dimagesharevn.app.models.rests.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class ChatRoomRequest {
    @NotBlank
    private String naturalName;
    private Set<String> members;

}
