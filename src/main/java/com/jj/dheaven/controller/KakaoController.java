package com.jj.dheaven.controller;

import com.jj.dheaven.domain.Member;
import com.jj.dheaven.service.KakaoService;
import com.jj.dheaven.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KakaoController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private HttpSession session;

    //카카오 로그인 폼은 카카오에서 제공
    //카카오 최초 로그인: 정보제공동의  => 로그인하면서 DB에 정보 들어감
    //그 후로는 계속 토큰으로 로그인

    @RequestMapping(value = "/auth/kakao/callback", method = RequestMethod.GET)
    public String kakaoLogin(@RequestParam(value = "code",required = false) String code){
        //1. 인가코드 받기
        System.out.println("컨트롤러kakao인가코드: "+code);
        // 2. 카카오계정에 접근할 액세스코드 받기
        String access_Token = kakaoService.getAccessToken(code);
        System.out.println("컨트롤러 kako 액세스코드: "+access_Token);

        //3. 액세스코드로 접근한 카카오 회원의 정보를 가져오기
        Member kakaMember = kakaoService.getKakaoUserInfo(access_Token);
        System.out.println("카카오컨트롤러에 회원정보받기-------------");
        System.out.println("카카오 메일 : " + kakaMember.getEmail());
        System.out.println("카카오 닉네임 : " + kakaMember.getNickname());
        System.out.println("카카오 이름 : " + kakaMember.getName());
        System.out.println("카카오 생년월일 : " + kakaMember.getBirthdate());
        System.out.println("카카오 권한 : " + kakaMember.getRole()); //널
        System.out.println("카카오컨트롤러에 회원정보받기---------");
        
        //세션영역
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
