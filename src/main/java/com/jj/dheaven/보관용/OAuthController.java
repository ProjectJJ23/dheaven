package com.jj.dheaven.보관용;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@RequiredArgsConstructor
//@RequestMapping("/oauth")
public class OAuthController {

    @Value("ab62b7e0ca424144a4f5e9f13a156b72")
    String clientId;
    @Value("http://localhost:8081/auth/kakao/callback")
    String redirectUri;
    @Value("${kakao.client.secret}")
    String clientSecret;

    /**
     * 카카오 로그인 요청
     * @return
     */
    @GetMapping(value="/kakao")
    public String kakaoConnect() {
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id="+clientId);
        url.append("&redirect_uri="+redirectUri);
        url.append("&response_type=code");
        return "redirect:" + url.toString();
    }



}
