package com.dimagesharevn.app.models.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class FileStorageProperties {
    @Value("${app.file-store.profile}")
    private String avatar_name;
}
