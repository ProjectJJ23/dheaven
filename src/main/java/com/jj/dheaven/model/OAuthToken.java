package com.jj.dheaven.model;

import lombok.Data;

@Data
public class OAuthToken {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;

    /*access_token: "VPCYc_rLZrdnKuS1AMB8xu7OkhHuKVfvYj4KKwzUAAABjUW7uLptZc76WqiBKA",
token_type: "bearer",
refresh_token: "5o9-UDQCevglKm4eK10NnjU_w8zoV9LHm9YKKwzUAAABjUW7uLdtZc76WqiBKA",
expires_in: 21599,
scope: "account_email profile_nickname",
refresh_token_expires_in: 5183999*/

}
