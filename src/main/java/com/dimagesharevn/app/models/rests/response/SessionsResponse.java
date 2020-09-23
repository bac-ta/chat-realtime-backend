package com.dimagesharevn.app.models.rests.response;

import com.dimagesharevn.app.models.dtos.SessionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionsResponse {

    public List<SessionDTO> sessions;


}
