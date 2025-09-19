package com.rido.shop.service;

import com.rido.shop.domain.Member;
import com.rido.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(String username, String password, String displayName) {

        //비밀번호 인코더
        String encodedPassword = passwordEncoder.encode(password);

        Member member = new Member(username, encodedPassword, displayName);

        memberRepository.save(member);
    }

    public boolean isAlreadyUser(String username) {
        Optional<Member> findUser = memberRepository.findByUsername(username);
        if(findUser.isPresent()) {
            return true;
        }else {
            return false;
        }
    }
}
