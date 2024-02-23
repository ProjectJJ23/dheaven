package com.jj.dheaven.model;

import lombok.Data;

@Data
public class OAuthToken {
    //Data를 오브젝트에 담는다?
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

    /*
    {
"id":3388888,
"connected_at":"2024-02-17T12:13:15Z",
"properties":{
"nickname":"J"
},
"kakao_account":{
"profile_nickname_needs_agreement":false,
"profile":{
"nickname":"J"
},
"name_needs_agreement":false,
"name":"김지윤",
"has_email":true,
"email_needs_agreement":false,
"is_email_valid":true,
"is_email_verified":true,
"email":"@naver.com",
"has_birthyear":true,
"birthyear_needs_agreement":false,
"birthyear":"1990",
"has_birthday":true,
"birthday_needs_agreement":false,
"birthday":"1212",
"birthday_type":"SOLAR"
}
}
     */

}
