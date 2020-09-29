package com.dimagesharevn.app.components;

import org.springframework.stereotype.Component;

@Component("AppComponentFactory")
public interface AppComponentFactory {
    String getTokenSecret();

    Long getTokenExpirationMsec();

    Integer getRecordLimit();

    String getFileStoreAvatar();

    String getFileStoreData();

    Integer getPort();
}
