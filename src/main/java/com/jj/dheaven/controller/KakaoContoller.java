package com.jj.dheaven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.dheaven.domain.KakaoApi;
import com.jj.dheaven.domain.Member;
import com.jj.dheaven.dto.KaKaoJoinDto;
import com.jj.dheaven.보관용.KakaoProfile;
import com.jj.dheaven.보관용.OAuthToken;
import com.jj.dheaven.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class KakaoContoller {

    private final MemberService memberService;
    private final HttpServletRequest request;
    private final KakaoApi kakaoApi;

    
    //로그인 컨트롤러- 로그인 폼 메서드에 카카오 키도 같이 모델로 전달

    //다른 유형
    /*
    public OAuthToken getOAuthToken(String code){
    String reqUrl = "https://kauth.kakao.com/oauth/token";

    RestTemplate rt = new RestTemplate();

    //HttpHeader 오브젝트
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    //HttpBody 오브젝트
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", AuthConstUtil.KAKAO_API_KEY);
    params.add("redirect_uri", AuthConstUtil.KAKAO_REDIRECT_URL);
    params.add("code", code);

    //http 바디(params)와 http 헤더(headers)를 가진 엔티티
    HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
            new HttpEntity<>(params, headers);

    //reqUrl로 Http 요청 , POST 방식
    ResponseEntity<String> response =
            rt.exchange(reqUrl, HttpMethod.POST, kakaoTokenRequest, String.class);

    String responseBody = response.getBody();

    Gson gson = new Gson();
    OAuthToken oAuthToken = gson.fromJson(responseBody, OAuthToken.class);

    return oAuthToken;
}

     */




    //테스트 카카오 로그인창 로그인 응답
    //@GetMapping(value = "/auth/kakao/callback")
    @RequestMapping(value = "/auth/kakao/callback")
    public String kakaoLgoin(@RequestParam("code") String code, KaKaoJoinDto kaKaoJoinDto
    , RedirectAttributes redirectAttributes) {
        // 1. 인가 코드 받기 (@RequestParam String code)
        System.out.println("카카오 로그인 메서드 작동 ");
        System.out.println("카카오 로그인 인가코드: "+code);
        // 2. 토큰 받기
        String accessToken = kakaoApi.getAccessToken(code);
        System.out.println("카카오 로그인 토큰: "+accessToken);

        // 3. 사용자 정보 받기
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
        for (Map.Entry<String, Object> entry : userInfo.entrySet()) {
            System.out.println("카카오 userINfo 키벨류 : "+entry.getKey() + ": " + entry.getValue());
        }

        String email = (String)userInfo.get("email");
        String nickname = (String)userInfo.get("nickname");
        String name = (String) userInfo.get("name");
        String birthyear = (String) userInfo.get("birthyear");
        String birthday = (String) userInfo.get("birthday");



        System.out.println("email = " + email);
        System.out.println("nickname = " + nickname);
        System.out.println("name = " + name);
        System.out.println("birthyear = " + birthyear);
        System.out.println("birthday = " + birthday);

        String address = "주소를 나중에 수정해주세요";
        //kakao 가입 추가코드
        Member member = new Member(
                kaKaoJoinDto.getKakao_email(), kaKaoJoinDto.getKakao_token(),
                kaKaoJoinDto.getNickname(), kaKaoJoinDto.getName(),
                kaKaoJoinDto.getBirthdate(), address);

        memberService.saveMember(member);
        System.out.println("kakao 간편회원 가입 완료");
        redirectAttributes.addAttribute("joinMsg2","kakao 회원가입 완료. 로그인 하세요");


        return "redirect:/";
    }



    //@GetMapping(value = "/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(@RequestParam String code){
        //Data를 리턴해주는 컨트롤러 함수
        //System.out.println(code);  // return "카카오 인증 완료"+" 코드값 : "+code;

        // POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
        // 이 때 필요한 라이브러리가 RestTemplate, 얘를 쓰면 http 요청을 편하게 할 수 있다.
        RestTemplate rt = new RestTemplate();

        // HTTP POST를 요청할 때 보내는 데이터(body)를 설명해주는 헤더도 만들어 같이 보내줘야 한다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // body 데이터를 담을 오브젝트인 MultiValueMap를 만들어보자
        // body는 보통 key, value의 쌍으로 이루어지기 때문에 자바에서 제공해주는 MultiValueMap 타입을 사용한다.
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "ab62b7e0ca424144a4f5e9f13a156b72");
        params.add("redirect_uri", "http://localhost:8081/auth/kakao/callback");
        params.add("code", code);

        // 요청하기 위해 헤더(Header)와 데이터(Body)를 합친다.
        // kakaoTokenRequest는 데이터(Body)와 헤더(Header)를 Entity가 된다.
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token", // https://{요청할 서버 주소}
                HttpMethod.POST, // 요청할 방식
                kakaoTokenRequest, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );
        //return "카카오 토큰 요청 완료 : 토큰 요청에 대한 응답 : "+response;
        //여기까지 - code를 받는 인증처리 완료 / 액세스토큰을 받아 권한을 부여받는 과정 끝

        // 액세스 토큰으로 카카오쪽으로 카카오로 로그인한 회원의 개인정보를 요청하기

        // return response.getBody();

        //JSON을 담을 떄 사용하는 라이브러리 Gson, Json Simple, ObjectMappper...
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            System.out.println("json이름 달라서 파싱오류,getter setter 오류");
        }catch (JsonProcessingException e){
            e.printStackTrace();
            System.out.println("오류2");
        }
        System.out.println("카카오 액세스 토큰:"+oauthToken.getAccess_token());
        //return response.getBody();
        //여기까지 진행시 콘솔에 액세스 토큰이 뜨면 된다. => json데이터가 자바 오브젝트로 잘 변환됐다는 뜻


        RestTemplate rt2 = new RestTemplate();

        // httpheader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
        headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");


        System.out.println("확인1"+headers2);
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
        //Post방식으로 http 요청한다. 그리고 respoense 변수의 응답을 받는다.
        System.out.println("확인2"+kakaoProfileRequest2);
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me", // https://{요청할 서버 주소}
                HttpMethod.POST, // 요청할 방식
                kakaoProfileRequest2, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );
        System.out.println("response2 :"+response2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            System.out.println("json이름 달라서 파싱오류,getter setter 오류");
        }catch (JsonProcessingException e){
            e.printStackTrace();
            System.out.println("오류2");
        }

        //Mem 오브젝트
        System.out.println("카카오 아이디(시퀀스): "+kakaoProfile.getId());
        System.out.println("카카오 이메일:" +kakaoProfile.getKakao_account().getEmail());

        return response2.getBody();
    }

    //카카오 로그아웃




}
