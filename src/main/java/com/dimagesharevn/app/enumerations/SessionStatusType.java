package com.dimagesharevn.app.enumerations;

import lombok.Getter;

public enum SessionStatusType {
    DETACHED(0, "Detached"),
    AUTHENTICATED(1, "Authenticated");
    @Getter
    private final int value;
    @Getter
    private final String name;

    SessionStatusType(int value, final String name) {
        this.value = value;
        this.name = name;
    }
}
