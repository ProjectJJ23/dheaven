package com.jj.dheaven.보관용;

import com.jj.dheaven.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Map;

//@Controller
@RequiredArgsConstructor
public class KakaoContollerM {

    private final MemberService memberService;
    private final HttpServletRequest request;
    private final KakaoApi kakaoApi;


    //카카오 간편가입 로직 분리하기

   // @GetMapping("/auth/kakao/check-email")
    public ResponseEntity<Boolean> kakaoCheckEmailDuplicate(String email){
        System.out.println("카카오 엔드포인트: email값:"+email);
        memberService.checkEmailDuplicate(email);


        return ResponseEntity.ok(memberService.checkEmailDuplicate(email));
        //결과물 항상 200 상태다
    }
    //닉네임 중복체크를 위한 엔드포인트
   // @GetMapping("/auth/kakao/check-nickname")
    public ResponseEntity<Boolean> kakaoCheckNicknameDuplicate(String nickname){
        System.out.println("카카오: nickname값:"+nickname);
        memberService.checkNicknameDuplicate(nickname);
        return ResponseEntity.ok(memberService.checkNicknameDuplicate(nickname));
        //결과물 항상 200 상태다
    }


    //@PostMapping(value = "/auth/kakao/joinComplete")
    public String kakaoJoin(KaKaoJoinDto kaKaoJoinDto,RedirectAttributes redirectAttributes) {


        //받아온 dto확인
        System.out.println("dto 받아왔는지 확인 "+kaKaoJoinDto.getKakao_email());
        System.out.println("dto 받아왔는지 확인2 "+kaKaoJoinDto.getKakao_token());
        //이메일 중복 확인

        //둘 중 하나 있으면 이메일 ,닉네임 중복이니 이메일 서비스로 가입해달라고 하기
        //만약 가입 되어있으면
        if(memberService.checkEmailDuplicate(kaKaoJoinDto.getKakao_email())){
            redirectAttributes.addAttribute("alreadyJoin", "이미 가입된 계정입니다.");
            return "redirect:/auth/kakao/callback";
        }else{

            //테이블 넣기
            //String address = "주소를 나중에 작성해주세요";
            //kakao 가입 추가코드
         /*   Member member = new Member(
                    kaKaoJoinDto.getKakao_email(), kaKaoJoinDto.getKakao_token(),
                    kaKaoJoinDto.getNickname(), kaKaoJoinDto.getName(),
                    kaKaoJoinDto.getBirthdate(), null);*/

            //memberService.saveMember(member);
            System.out.println("kakao 간편회원 가입 완료: kakajoin메서드");


            return "redirect:/auth/kakao/callback";

        }


    }



    //테스트 카카오 로그인창 로그인 응답
    //@GetMapping(value = "/auth/kakao/callback")
   // @RequestMapping(value = "/auth/kakao/callback")
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
        System.out.println("birthday = " + birthday);  //여기까지 완료

        kaKaoJoinDto.setKakao_token(accessToken);
        kaKaoJoinDto.setKakao_email(email);
        kaKaoJoinDto.setNickname(nickname);
        kaKaoJoinDto.setName(name);
        LocalDate kakao_birthdate =  kaKaoJoinDto.parseBirthdate(birthyear,birthday);
        System.out.println("날짜 합친거 확인: "+kakao_birthdate);
        kaKaoJoinDto.setBirthdate(kakao_birthdate); //여까지 완료


        kakaoJoin(kaKaoJoinDto,redirectAttributes);

        redirectAttributes.getAttribute("alreadyJoin");
        System.out.println("alreday join: "+redirectAttributes.getAttribute("alreadyJoin"));


        kakaoApi.kakaoLogout(accessToken);


        redirectAttributes.addAttribute("joinMsg","회원가입 완료. 로그인 하세요");

        //


        return "redirect:/loginForm";
    }





    //카카오 로그아웃
    //@GetMapping(value = "/auth/kakao/logout")
    public String kakaoLout(@RequestParam("accessToken") String accessToken) {
            kakaoApi.kakaoLogout(accessToken);
            System.out.println("카카오 로그아웃 메서드 발동");
            System.out.println("카카오로그아웃 accessToken: "+accessToken);

            return "redirect:/";
    }



}
