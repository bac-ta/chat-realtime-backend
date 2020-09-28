package com.dimagesharevn.app.components.impls;

import com.dimagesharevn.app.components.OpenfireComponentFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("openfireComponentImpl")
public class OpenfireComponentImpl implements OpenfireComponentFactory {
    @Value("${openfire.secret-key}")
    private String secretKey;
    @Value("${openfire.xmpp-client-connection-port}")
    private Integer xmppClientConnectionPort;
    @Value("${openfire.xmpp-domain}")
    private String xmppDomain;
    @Value("${openfire.xmpp-client-bin-port}")
    private Integer xmppClientBinPort;
    @Value("${openfire.host}")
    private String host;


    @Override
    public String getSecretKey() {
        return secretKey;
    }

    @Override
    public Integer getXmppClientConnectionPort() {
        return xmppClientConnectionPort;
    }

    @Override
    public String getXmppDomain() {
        return xmppDomain;
    }

    @Override
    public Integer getXmppClientBinPort() {
        return xmppClientBinPort;
    }

    @Override
    public String getHost() {
        return host;
    }
}
