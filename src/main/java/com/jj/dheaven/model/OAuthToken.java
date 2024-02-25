package com.jj.dheaven.model;

import lombok.Data;

@Data
public class OAuthToken {
    //카카오 에서 받아오는 json을 담을 자바 클래스

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;

    /*{
"access_token":"JN1CPTYCD5Ykr-CRqOrI2pdmkxGCNLfXxdQKPXOaAAABjbb8KOltZc76WqiBKA",
"token_type":"bearer",
"refresh_token":"cSAt1hRYnJcUbR-vApC5j3L8TmPgynISuY0KPXOaAAABjbb8KOZtZc76WqiBKA",
"expires_in":21599,
"scope":"birthday account_email birthyear profile_nickname name shipping_address",
"refresh_token_expires_in":5183999
}*/



}
