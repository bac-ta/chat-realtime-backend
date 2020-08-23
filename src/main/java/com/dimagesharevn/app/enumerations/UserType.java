package com.dimagesharevn.app.enumerations;


import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.utils.ExceptionHandler;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author bac-ta
 */
public enum UserType {
    ADMINITRATOR(1, "ADMINITRATOR"),
    NORMAL(2, "NORMAL");
    @Getter
    private int value;
    @Getter
    private String name;

    UserType(int value, final String name) {
        this.value = value;
        this.name = name;
    }

    public static UserType findByValue(int value) {
        return Arrays.stream(UserType.values())
                .filter(userType -> userType.getValue() == value)
                .findFirst().orElseThrow(() -> new ExceptionHandler(APIMessage.USER_TYPE_INVALID));
    }
}
