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
    @Value("${openfire.port}")
    private Integer port;


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

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public String getOpenfireRestApiEndPointBase() {
        StringBuilder builder = new StringBuilder();
        builder.append("http://").append(host).append(":").append(port).append("/plugins/restapi/v1");
        return builder.toString();
    }
}
