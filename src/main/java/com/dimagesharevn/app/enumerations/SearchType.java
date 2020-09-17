package com.dimagesharevn.app.enumerations;

import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.utils.ResourceNotFoundExceptionHandler;
import lombok.Getter;

import java.util.Arrays;

public enum SearchType {
    ALL(0, "ALL"),
    USER(1, "USER"),
    ROOM(2, "ROOM");
    @Getter
    private final int value;
    @Getter
    private final String name;

    SearchType(int value, final String name) {
        this.value = value;
        this.name = name;
    }

    public static SearchType findByValue(int value) {
        return Arrays.stream(SearchType.values())
                .filter(searchType -> searchType.getValue() == value)
                .findFirst().orElseThrow(() -> new ResourceNotFoundExceptionHandler(APIMessage.SEARCH_TYPE_INVALID));
    }
}
