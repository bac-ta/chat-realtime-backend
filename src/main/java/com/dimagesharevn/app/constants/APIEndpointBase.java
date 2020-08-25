package com.dimagesharevn.app.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author bac-ta
 */
@Component
public class APIEndpointBase {
    @Value("${openfire.rest-api-enpoint-base}")
    public static String OPENFIRE_REST_API_ENDPOINT_BASE;
    public static final String AUTH_ENDPOINT_BASE = "/auth";
    public static final String USER_ENDPOINT_BASE = "/user";
}
