package com.jj.dheaven.controller;

import com.jj.dheaven.domain.KakaoApi;
import com.jj.dheaven.domain.Member;
import com.jj.dheaven.dto.MemberLoginDto;
import com.jj.dheaven.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final HttpServletRequest request; //ip
    private final KakaoApi kakaoApi;

    @GetMapping(value = "/loginForm")
    public String login(Model model , MemberLoginDto memberLoginDto){
        model.addAttribute("memberLoginDto",memberLoginDto);
        model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
        model.addAttribute("redirectUri", kakaoApi.getKakaoRedirectUri());
        //System.out.println("kakao key 로그인 컨트롤러:"+kakaoApi.getKakaoApiKey());
        return "member/loginForm";
    }


    //로그인
    @PostMapping("/loginCheck")
    public String loginCheck(MemberLoginDto memberLoginDto, Model model,HttpSession session
    ,RedirectAttributes redirectAttributes){
        String reip = request.getRemoteAddr(); //ip 전달
        //System.out.println("ip테스트:"+reip);
        model.addAttribute("reip", reip);


        Member member = memberService.findMember(memberLoginDto.getEmail());
       // Member member = memberService.login(memberLoginDto.getEmail(), memberLoginDto.getPassword());
        if (member != null && member.getPassword().equals(memberLoginDto.getPassword())
        ){
            // 세션을 생성하기 전에 기존의 세션 파기
            request.getSession().invalidate();
            session = request.getSession(true);

            session.setAttribute("sessionMember", member);
            session.setAttribute("sessionEmail",member.getEmail());
            session.setAttribute("sessionRole",member.getRole());
            session.setAttribute("sessionNickname",member.getNickname());
            //System.out.println("테스ㅡ"+member.getEmail());
            System.out.println("회원 아이디: "+memberLoginDto.getEmail());
            //System.out.println("회원 비번: "+memberLoginDto.getPassword());
            System.out.println("sessionMember: " + session.getAttribute("sessionMember"));
           //System.out.println("sessionEmail: " + session.getAttribute("sessionEmail"));
            session.setMaxInactiveInterval(1800); // Session이 30분동안 유지
            // 로그인 성공 여부를 모델에 추가
           // model.addAttribute("loginSuccess", true);

            return "redirect:/";
        }else{
            redirectAttributes.addAttribute("loginError",
                    "메일 또는 비밀번호가 일치하지 않습니다");
            return "redirect:/loginForm";
        }


    }//login


    //로그아웃
    @GetMapping("/logout")
    public ModelAndView logoutProcess(HttpSession session, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        session = request.getSession(false);
        // Session이 없으면 null return
        if(session != null){
            session.invalidate();
            System.out.println("로그아웃, 세션삭제=> Proceeding Call");
        }
        modelAndView.setView(new RedirectView("/"));
        return modelAndView;
    }




}//class
