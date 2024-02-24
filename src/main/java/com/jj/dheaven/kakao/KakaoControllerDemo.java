package com.jj.dheaven.kakao;

import com.jj.dheaven.domain.Member;
import com.jj.dheaven.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class KakaoControllerDemo {

    private final MemberService memberService;
    private final KakaoService kakaoService;
    private final HttpSession session;

    //카카오 로그인 폼은 카카오에서 제공

    @RequestMapping(value = "/auth/kakao/callback", method = RequestMethod.GET)
    public String kakaoLogin(@RequestParam(value = "code",required = false) String code){
        //1. 인가코드 받기
        System.out.println("kakao인가코드: "+code);
        // 2. 카카오계정에 접근할 액세스코드 받기
        String access_Token = kakaoService.getAccessToken(code).get("access_Token");
        String refresh_Token = kakaoService.getAccessToken(code).get("refresh_Token");
        System.out.println("kako 액세스코드: "+access_Token);
        System.out.println("kako 리프레쉬코드: "+refresh_Token);

        //3. 액세스코드로 접근한 카카오 회원의 정보를 가져오기
        Member kakaMember = kakaoService.getKakaoUserInfo(access_Token,refresh_Token);
        System.out.println("카카오컨트롤러에 회원정보받기-------------");
        System.out.println("카카오access_Token : " + kakaMember.getAccess_token());
        System.out.println("카카오refresh_Token : " + kakaMember.getRefresh_token());
        System.out.println("카카오 메일 : " + kakaMember.getEmail());
        System.out.println("카카오 닉네임 : " + kakaMember.getNickname());
        System.out.println("카카오 이름 : " + kakaMember.getName());
        System.out.println("카카오 생년월일 : " + kakaMember.getBirthdate());
        System.out.println("카카오 권한 : " + kakaMember.getRole());
        System.out.println("카카오컨트롤러에 회원정보받기---------");
       // System.out.println("카카오 닉네임 : " + kakaMember.get("nickname"));
       // System.out.println("카카오 메일 : " + kakaMember.get("email"));
        session.invalidate();
        session.setAttribute("sessionMember", kakaMember);
        session.setAttribute("sessionEmail",kakaMember.getEmail());
        session.setAttribute("sessionRole",kakaMember.getRole());
        session.setAttribute("sessionNickname",kakaMember.getNickname());

        System.out.println("sessionMember: " + session.getAttribute("sessionMember"));
        System.out.println("sessionEmail: " + session.getAttribute("sessionEmail"));

        return "redirect:/";
    }







}
