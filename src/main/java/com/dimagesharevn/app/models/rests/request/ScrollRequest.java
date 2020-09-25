package com.dimagesharevn.app.models.rests.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScrollRequest {

    private String fromJID;
    private String toJID;
    private String sentDate;
}
