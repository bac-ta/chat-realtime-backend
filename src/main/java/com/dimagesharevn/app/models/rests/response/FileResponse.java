package com.dimagesharevn.app.models.rests.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {
    @JsonProperty("file_name")
    private String fileName;
    @JsonProperty("file_uri")
    private String fileUri;
    @JsonProperty("file_type")
    private String fileType;
    private long size;
}
