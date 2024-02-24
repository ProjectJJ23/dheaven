package com.jj.dheaven.controller;

import com.jj.dheaven.보관용.KakaoApi;
import com.jj.dheaven.domain.Member;
import com.jj.dheaven.domain.Roles;
import com.jj.dheaven.dto.MemberJoinDto;
import com.jj.dheaven.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberController {

    // 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/**허용
    // 그냥 주소가 / 이면 index.jsp 허용
    // static 이하에 있는 /js/**, /css/**, /image/** 허용
    private final MemberService memberService;
    private final HttpServletRequest request;
    private final KakaoApi kakaoApi;



    @GetMapping(value = "/join")
    public String join(Model model){
        model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
        model.addAttribute("redirectUri", kakaoApi.getKakaoRedirectUri());
        //System.out.println("kakao key 회원가입 컨트롤러:"+kakaoApi.getKakaoApiKey());
        return "member/join";
    }

    //이메일로 가입하기 폼
    @GetMapping(value = "/email_join")
    public String emailJoin(Model model, MemberJoinDto memberJoinDto){
        model.addAttribute("memberJoinDto", memberJoinDto);

        return "member/joinForm";
    }

    //ENDPOINT는 API가 서버에서 리소스에 접근할 수 있도록 가능하게 하는 URL
    //이메일 중복체크를 위한 엔드포인트
    @GetMapping("/mem-emails/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable(value = "email") String email){
        System.out.println("컨트롤러 엔드포인트: email값:"+email);
        return ResponseEntity.ok(memberService.checkEmailDuplicate(email));
        //결과물 항상 200 상태다
    }
    //닉네임 중복체크를 위한 엔드포인트
    @GetMapping("/mem-nicknames/{nickname}/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable(value = "nickname") String nickname){
        System.out.println("컨트롤러 엔드포인트: nickname값:"+nickname);
        return ResponseEntity.ok(memberService.checkNicknameDuplicate(nickname));
        //결과물 항상 200 상태다
    }
    // 주소검색 폼
    @GetMapping(value = "/email_join/address_form")
    public String join_map(){
        return "member/address_form";
    }


    @PostMapping(value = "/joinComplete")
    public String joinComplete(MemberJoinDto memberJoinDto, RedirectAttributes redirectAttributes){
            Member member = Member.builder()
                    .email(memberJoinDto.getEmail()).password(memberJoinDto.getPassword())
                    .name(memberJoinDto.getName()).nickname(memberJoinDto.getNickname())
                    .birthdate(memberJoinDto.getBirthdate()).address(memberJoinDto.getAddress())
                    .role(Roles.MEMBER).build();

            memberService.saveMember(member);
            System.out.println("일반 회원 가입 완료");
            redirectAttributes.addAttribute("joinMsg","회원가입 완료. 로그인 하세요");

        return "redirect:/loginForm";
    }


    //회원정보 찾기 비번 찾기
    @GetMapping(value = "/findmem")
    public String findInfo(){
        return "member/findInfo";
    }


    //마이페이지
    @GetMapping("/mypage")
    public String mypage(@RequestParam String email){
        return "member/mypage";
    }

    //임시 마이페이지 (프론트 작업용)
   // @GetMapping("/mypage")
    public String mypage2(){
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


    //지도 api
 /*   @GetMapping(value = "/email_join/map")
    public String join_map(){
        return "member/map";
    }*/








}
