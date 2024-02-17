package com.jj.dheaven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.dheaven.domain.Member;
import com.jj.dheaven.dto.MemberJoinDto;
import com.jj.dheaven.model.KakaoProfile;
import com.jj.dheaven.model.OAuthToken;
import com.jj.dheaven.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberController {

    // 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/**허용
    // 그냥 주소가 / 이면 index.jsp 허용
    // static 이하에 있는 /js/**, /css/**, /image/** 허용
    private final MemberService memberService;



    @GetMapping(value = "/join")
    public String join(){
        return "member/join";
    }

    //이메일로 가입하기 폼
    @GetMapping(value = "/email_join")
    public String emailJoin(Model model, MemberJoinDto memberJoinDto){
        model.addAttribute("memberJoinDto", memberJoinDto);
        return "member/joinForm";
    }

    //이메일 중복체크를 위한 엔드포인트
    @GetMapping("/mem-emails/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email){
        System.out.println("컨트롤러 엔드포인트: email값:"+email);
        return ResponseEntity.ok(memberService.checkEmailDuplicate(email));
        //결과물 항상 200 상태다
    }

    //닉네임 중복체크를 위한 엔드포인트
    @GetMapping("/mem-nicknames/{nickname}/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname){
        System.out.println("컨트롤러 엔드포인트: nickname값:"+nickname);
        return ResponseEntity.ok(memberService.checkNicknameDuplicate(nickname));
        //결과물 항상 200 상태다
    }


    @PostMapping(value = "/joinComplete")
    public String emailJoinComplete(MemberJoinDto memberJoinDto, RedirectAttributes redirectAttributes){
            Member member = new Member(
                    memberJoinDto.getEmail(), memberJoinDto.getPassword(),
                    memberJoinDto.getNickname(), memberJoinDto.getName(),
                    memberJoinDto.getBirthdate(), memberJoinDto.getAddress()
            );
            memberService.saveMember(member);
            System.out.println("회원 가입 완료");
            redirectAttributes.addAttribute("JoinMsg","회원가입 완료. 로그인 하세요");

        return "redirect:/loginForm";
    }



    // 주소검색 폼
    @GetMapping(value = "/email_join/address_form")
    public String join_map2(){
        return "member/address_form";
    }


    //지도 api
 /*   @GetMapping(value = "/email_join/map")
    public String join_map(){
        return "member/map";
    }*/

    @GetMapping(value = "/loginForm")
    public String login(){
        return "member/loginForm";
    }


    //카카오 간편로그인
    @GetMapping(value = "/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code){
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
        params.add("client_id", "29f8f3ec28cad5cb6c458bb52e5d7541");
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



    //회원정보 찾기 비번 찾기
    @GetMapping(value = "/findmem")
    public String findInfo(){
        return "member/findInfo";
    }


    //마이페이지
    @GetMapping(value = "/mypage")
    public String mypage(){
        return "member/mypage";
    }

    //내정보 디테일
    @GetMapping(value = "/mydetail")
    public String mydetail(){
        return "member/mydetail";
    }


    //마이 리스트 (펀딩리스트)
    @GetMapping(value = "/mylist")
    public String mylist(){
        return "member/mylist";
    }


    //탈퇴페이지
    @GetMapping(value = "/memdelete")
    public String deletemem(){
        return "member/memdelete";
    }

    //회원정보 수정페이지
    @GetMapping(value = "/memmodi")
    public String modifymem(){
        return "member/memmodify";
    }

    //비번수정
    @GetMapping(value = "/pwmodi")
    public String modifypw(){
        return "member/pwmodify";
    }






}
