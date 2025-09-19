package com.rido.shop.controller;

import com.rido.shop.service.MemberService;
import com.rido.shop.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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

    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password"));

        //입력값과 DB내용을 비교해 로그인 시켜줌
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authToken);
        //Authentication에 유저정보 추가
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        //jwt 생성
        String jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println("jwt = " + jwt);

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(10);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return jwt;
    }

    @GetMapping("/my-page/jwt")
    @ResponseBody
    public String myPageJWT(Authentication auth) {

        User user = (User) auth.getPrincipal();
        System.out.println("user = " + user);
        System.out.println("username = " + user.getUsername());
        System.out.println("authorities = " + user.getAuthorities());

        return "마이페이지데이터";
    }

}
