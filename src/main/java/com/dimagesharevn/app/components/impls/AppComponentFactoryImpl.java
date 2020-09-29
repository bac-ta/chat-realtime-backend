package com.dimagesharevn.app.components.impls;

import com.dimagesharevn.app.components.AppComponentFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("appComponentFactoryImpl")
public class AppComponentFactoryImpl implements AppComponentFactory {
    @Value("${app.auth.token-secret}")
    private String tokenSecret;
    @Value("${app.auth.token-expiration-msec}")
    private Long tokenExpirationMsec;
    @Value("${app.query.record-limit}")
    private Integer recordLimit;
    @Value("${app.file-store.avatar}")
    private String fileStoreAvatar;
    @Value("${app.file-store.data}")
    private String fileStoreData;
    @Value("${server.port}")
    private Integer port;

    @Override
    public String getTokenSecret() {
        return tokenSecret;
    }

    @Override
    public Long getTokenExpirationMsec() {
        return tokenExpirationMsec;
    }

    @Override
    public Integer getRecordLimit() {
        return recordLimit;
    }

    @Override
    public String getFileStoreAvatar() {
        return fileStoreAvatar;
    }

    @Override
    public String getFileStoreData() {
        return fileStoreData;
    }

    @Override
    public Integer getPort() {
        return port;
    }
}
