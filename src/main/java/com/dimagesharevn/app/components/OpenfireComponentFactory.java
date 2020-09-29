package com.dimagesharevn.app.components;

import org.springframework.stereotype.Component;

@Component("openfireComponentFactory")
public interface OpenfireComponentFactory {
    String getSecretKey();

    Integer getXmppClientConnectionPort();

    String getXmppDomain();

    Integer getXmppClientBinPort();

    String getHost();

    Integer getPort();

    String getOpenfireRestApiEndPointBase();
}
