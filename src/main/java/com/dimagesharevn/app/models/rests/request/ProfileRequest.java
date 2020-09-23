package com.dimagesharevn.app.models.rests.request;

import com.dimagesharevn.app.constants.APIMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    private String name;
    private String description;
    private String filename;
}
