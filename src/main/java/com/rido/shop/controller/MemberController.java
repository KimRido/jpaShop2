package com.rido.shop.controller;

import com.rido.shop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String registerPage(Authentication auth) {
        //만약에 로그인중이면 list페이지로 보낸다
        if(auth != null && auth.isAuthenticated()) {
            return "redirect:/list";
        }

        return "register";
    }

    @PostMapping("/register")
    public String register(String username, String password, String displayName, Model model) {

        if(memberService.isAlreadyUser(username)) {
            model.addAttribute("errorMessage", "이미 존재하는 회원입니다.");
            return "register";
        }

        memberService.saveUser(username, password, displayName);
        return "redirect:/list";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth) {
        System.out.println("auth = " + auth);
        return "mypage";
    }
}
