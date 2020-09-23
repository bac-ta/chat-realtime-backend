package com.dimagesharevn.app.models.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class FileStorageProperties {
    @Value("${app.file-store.data}")
    private String upload_dir;
}
