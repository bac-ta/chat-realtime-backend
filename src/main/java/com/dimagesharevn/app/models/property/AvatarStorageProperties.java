package com.dimagesharevn.app.models.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AvatarStorageProperties {
    @Value("${app.file-store.avatar")
    private String avatar_name;
}
