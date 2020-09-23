package com.dimagesharevn.app.enumerations;


import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.utils.ResourceNotFoundExceptionHandler;
import lombok.Getter;

import java.util.Arrays;

public enum FileType {
    JPG(0, "jpg"),
    JPEG(1, "jpeg"),
    PNG(2, "png"),
    GIFT(3, "gif"),
    TXT(3, "txt"),
    CSV(4, "csv"),
    DOCX(5, "docx"),
    EXCEL(6, "xlsx"),
    PDF(7, "pdf");

    @Getter
    private int value;
    @Getter
    private String name;

    FileType(int value, final String name) {
        this.value = value;
        this.name = name;
    }


    public static FileType findByValue(int value) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getValue() == value)
                .findFirst().orElseThrow(() -> new ResourceNotFoundExceptionHandler(APIMessage.FILE_TYPE_INVALID));
    }

    public static FileType findByName(String name) {
        return Arrays.stream(FileType.values()).filter(fileType -> fileType.getName().equals(name))
                .findFirst().orElseThrow(() -> new ResourceNotFoundExceptionHandler(APIMessage.FILE_TYPE_INVALID));
    }

}
