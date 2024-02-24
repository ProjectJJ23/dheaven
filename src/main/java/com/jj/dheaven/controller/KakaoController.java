package com.jj.dheaven.controller;

import com.jj.dheaven.domain.Member;
import com.jj.dheaven.model.OAuthToken;
import com.jj.dheaven.service.KakaoService;
import com.jj.dheaven.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@AllArgsConstructor
@Controller
public class KakaoController {

    private final MemberService memberService;

    private final KakaoService kakaoService;

    private HttpSession session;

    private final HttpServletRequest request;

    //카카오 로그인 폼은 카카오에서 제공
    //카카오 최초 로그인: 정보제공동의  => 로그인하면서 DB에 정보 들어감
    //그 후로는 계속 토큰으로 로그인

    @RequestMapping(value = "/auth/kakao/callback", method = RequestMethod.GET)
    public String kakaoLogin(@RequestParam(value = "code",required = false) String code){
        //1. 인가코드 받기
        System.out.println("컨트롤러kakao인가코드: "+code);


        // 2. 카카오계정에 접근할 액세스코드 받기
       // String access_Token = kakaoService.getAccessToken(code);
       // System.out.println("컨트롤러 kako 액세스코드: "+access_Token);

        //다른방법 시도
        OAuthToken oAuthToken = kakaoService.getOAuthToken(code);
        String access_Token = oAuthToken.getAccess_token();
        String refresh_token = oAuthToken.getRefresh_token();

        System.out.println("컨트롤러 access_token: "+access_Token);
        System.out.println("컨트롤러 refresh_token: "+refresh_token);

        // 2-2. OAuthToken 클래스에 토큰,리프레쉬토큰, 유효기간 등을 담기



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
        session.setAttribute("sessionMemberK", kakaMember);
        session.setAttribute("sessionEmail",kakaMember.getEmail());
        session.setAttribute("sessionRole",kakaMember.getRole());
        session.setAttribute("sessionNickname",kakaMember.getNickname());
        System.out.println("sessionMember: " + session.getAttribute("sessionMemberK"));
        System.out.println("sessionEmail: " + session.getAttribute("sessionEmail"));

        //토큰 세션에 저장
        if (session.getAttribute("sessionMemberK") != null){
            session.setAttribute("kakaId", kakaMember.getKakaoID()); //카카오 회원넘버
            session.setAttribute("access_Token", access_Token);
            //session.setAttribute("refresh_token", refresh_token);
        }



        return "redirect:/";
    }

    @RequestMapping(value = "/kakao/logout")
    public String kakaoLogout(HttpSession session){
        //가지고 있는 토큰 부터 확인
        String access_Token = (String)session.getAttribute("access_Token");
        System.out.println("컨트롤러 로그아웃, 토큰 확인:"+access_Token);

        if(access_Token != null && !"".equals(access_Token)){
            kakaoService.kakaoLogout(access_Token);
            session.removeAttribute("access_Token");
            session.removeAttribute("kakaId");
            System.out.println("카카오 액세스토큰 삭제");
        }else{
            System.out.println("access_Token is null");
        }
        
        //세션 제거
        session = request.getSession(false); //세션이 없는 경우 새 세션 생성 x
        if(session != null){
            session.invalidate();
            System.out.println("카카오 로그아웃, 세션삭제=> Proceeding Call");
        }


        return "redirect:/";
    }







}
