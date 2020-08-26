package com.dimagesharevn.app.enumerations;


import lombok.Getter;

/**
 * @author bac-ta
 */
public enum UserType {
    ADMINITRATOR(1, "ADMINITRATOR"),
    MEMBER(2, "MEMBER");
    @Getter
    private final int value;
    @Getter
    private final String name;

    UserType(int value, final String name) {
        this.value = value;
        this.name = name;
    }
}