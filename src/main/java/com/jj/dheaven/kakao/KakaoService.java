package com.jj.dheaven.kakao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jj.dheaven.domain.Member;
import com.jj.dheaven.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.ProtocolException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


//@Transactional
@RequiredArgsConstructor
@Service
public class KakaoService {

    private final MemberRepository memberRepository;


    public HashMap<String, String>  getAccessToken(String authorize_code){
        HashMap<String,String> toekns = new HashMap<>();

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

            sb.append("&client_id=ab62b7e0ca424144a4f5e9f13a156b72"); //본인이 발급받은 key
            sb.append("&redirect_uri=http://localhost:8081/auth/kakao/callback"); // 본인이 설정한 주소

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
           // System.out.println("response body 겟토큰메서드 : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();


            //System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        toekns.put("access_Token",access_Token);
        toekns.put("refresh_Token",refresh_Token);

        return toekns;
    }

    //HashMap<String, Object>
    public Member getKakaoUserInfo(String access_Token,String refresh_Token){

        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //카카오 정보를 받을 변수 선언
        String kakao_email = "";
        String kakao_nickname = "";
        String kakao_name = "";
        String kakao_birthyear = "";
        String kakao_birthday = "";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode 겟유저메서드 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            //System.out.println("response body겟유저메서드 : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            kakao_email = kakao_account.getAsJsonObject().get("email").getAsString();
            kakao_nickname = kakao_account.getAsJsonObject().get("nickname").getAsString();
            kakao_name = properties.getAsJsonObject().get("name").getAsString();
            kakao_birthyear = kakao_account.getAsJsonObject().get("birthyear").getAsString();
            kakao_birthday = kakao_account.getAsJsonObject().get("birthday").getAsString();

            //맵
       /*     userInfo.put("kakao_email", kakao_email);
            userInfo.put("kakao_nickname", kakao_nickname);
            userInfo.put("kakao_name",kakao_name);
            userInfo.put("kakao_birthyear", kakao_birthyear);
            userInfo.put("kakao_birthday", kakao_birthday);
*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        //카카오 회원정보를 DB에 담기
        // 회원정보가 이미 있는지 확인 메일로 체크
        Member kakaoMember = memberRepository.findByEmail(kakao_email);
        System.out.println("카카오 회원가입 여부:" +kakaoMember);

        //비회원이면 DB에 넣기 /간편가입
        if(kakaoMember ==null){
            //카카오 생년, 월일 따로 받아와져서 합치고 날짜형으로 변환
            LocalDate kakao_birthdate = parseBirthdate(kakao_birthyear,kakao_birthday);

            kakaoMember = Member.builder()
                    .email(kakao_email)
                    .access_token(access_Token)
                    .refresh_token(refresh_Token)
                    .nickname(kakao_nickname)
                    .name(kakao_name)
                    .birthdate(kakao_birthdate)
                    .build();
            System.out.println("카카오 회원가입");
        }else { //이미 회원이면

            System.out.println("카카오 이미 가입되어 있음 :" +kakaoMember);
        }
        return kakaoMember;
    }

    protected LocalDate parseBirthdate(String birthyear, String birthday){
        String birthdateStr = birthyear + birthday;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
        System.out.println("kakaoDto-카카오생년월일 합치기 확인: "+birthdate);
        return birthdate;
    }








}
