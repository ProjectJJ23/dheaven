package com.jj.dheaven.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jj.dheaven.domain.Member;
import com.jj.dheaven.domain.Roles;
import com.jj.dheaven.model.OAuthToken;
import com.jj.dheaven.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


//@Transactional
@RequiredArgsConstructor
@Service
public class KakaoService {

    private final MemberRepository memberRepository;


    // private properties에 보관할 보안이 필요한 정보들
    @Value("${kakao.api_key}")
    private String kakaoApiKey;

    //로그인 리라이렉트
    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.logoutRedirect_uri}")
    private String kakaoLogoutRedirectUri;
    public String getKakaoApiKey(){
        return this.kakaoApiKey;
    }
    public String getKakaoRedirectUri(){
        return this.kakaoRedirectUri;
    }
    public String getKakaoLogoutRedirectUri(){return this.kakaoLogoutRedirectUri;}




    // 2-2. OAuthToken 클래스에 토큰,리프레쉬토큰, 유효기간 등을 담기
    public OAuthToken getOAuthToken(String code){
        //인가코드 확인
        System.out.println("인가코드 테스트:"+code);

        // POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
        //라이브러리 RestTemplate,이걸 쓰면 http 요청을 편하게 할 수 있다.
        RestTemplate restTemplate = new RestTemplate();

        // HTTP POST를 요청할 때 보내는 데이터(body)를 설명해주는 헤더도 만들어 같이 보내줘야 한다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // body 데이터를 담을 오브젝트인 MultiValueMap를 만들어보자
        // body는 보통 key, value의 쌍으로 이루어지기 때문에 자바에서 제공해주는 MultiValueMap 타입을 사용한다.
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoApiKey);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code",code);

        // 요청하기 위해 헤더(Header)와 데이터(Body)를 합친다.
        // kakaoTokenRequest는 데이터(Body)와 헤더(Header)를 Entity가 된다.
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token", // https://{요청할 서버 주소}
                HttpMethod.POST, // 요청할 방식
                kakaoTokenRequest, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );
        //return "카카오 토큰 요청 완료 : 토큰 요청에 대한 응답 : "+response;
        //여기까지 - code를 받는 인증처리 완료 / 액세스토큰을 받아 권한을 부여받는 과정 끝

        // 액세스 토큰으로 카카오쪽으로 카카오로 로그인한 회원의 개인정보를 요청하기
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


        return oauthToken;
    }

        //getOAuthToken을 쓰면 딱히 필요없음
        public String getAccessToken(String authorize_code){
        //HashMap<String,String> toekns = new HashMap<>();

        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");

            sb.append("&client_id="+kakaoApiKey); //본인이 발급받은 key
            sb.append("&redirect_uri="+kakaoRedirectUri); // 본인이 설정한 주소

            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode 겟토큰메서드: " + responseCode);

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
           System.out.println("response body 겟토큰메서드 result: " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("겟토큰 access_token : " + access_Token);
            System.out.println("겟토큰 refresh_token : " + refresh_Token);
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }





    //HashMap<String, Object> ,String refresh_Token)
    public Member getKakaoUserInfo(String access_Token){

        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //카카오 정보를 받을 변수 선언
        String kakao_email = "";
        String kakao_nickname = "";
        String kakao_name = "";
        Long kakao_id = null;
        LocalDate kakao_birthdate=null;


        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode 겟유저메서드 : " + responseCode);
            //이부분 오류

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body겟유저메서드 result: " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String id = element.getAsJsonObject().get("id").getAsString();
            kakao_id = Long.valueOf(id);
            System.out.println("kakao 서비스:id :"+kakao_id);
            kakao_nickname = properties.getAsJsonObject().get("nickname").getAsString();
            kakao_email = kakao_account.getAsJsonObject().get("email").getAsString();
            kakao_name = kakao_account.getAsJsonObject().get("name").getAsString();
            String kakao_birthyear = kakao_account.getAsJsonObject().get("birthyear").getAsString();
            String kakao_birthday = kakao_account.getAsJsonObject().get("birthday").getAsString();

            //카카오 생년, 월일 따로 받아와져서 합치고 날짜형으로 변환
            kakao_birthdate = parseBirthdate(kakao_birthyear,kakao_birthday);


        } catch (IOException e) {
            e.printStackTrace();
        }
        //카카오 회원정보를 DB에 담기
        // 회원정보가 이미 있는지 확인 (메일,생년월일 복합키)
        //Member kakaoMember = memberRepository.findByEmail(kakao_email);
        //Member kakaoMember = memberRepository.findByEmailAndBirthdate(kakao_email,kakao_birthdate);
        Member kakaoMember = memberRepository.findByKakaoIDAndEmail(kakao_id, kakao_email);
        System.out.println("카카오 회원가입 여부:" +kakaoMember);

        //비회원이면 DB에 넣기 /간편가입
        if(kakaoMember ==null){ //여기서 자동으로

                kakaoMember = Member.builder()
                        .email(kakao_email)
                        .nickname(kakao_nickname)
                        .name(kakao_name)
                        .birthdate(kakao_birthdate)
                        .role(Roles.MEMBER)
                        .kakaoID(kakao_id)
                        .build();
                memberRepository.save(kakaoMember);
                //System.out.println("카카오 회원가입");

        }else { //이미 회원이면
            //System.out.println("카카오 이미 가입되어 있음 :" +kakaoMember);

        }
        return kakaoMember;
    }


    protected LocalDate parseBirthdate(String birthyear, String birthday){
        String birthdateStr = birthyear + birthday;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
        //System.out.println("kakaoDto-카카오생년월일 합치기 확인: "+birthdate);
        return birthdate;
    }



    //로그아웃
    public void kakaoLogout(String access_Token) {

        String reqURL = "https://kapi.kakao.com/v1/user/logout";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode(); //200떠야
            System.out.println("responseCode: 카카오로그아웃 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("result: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }






}
