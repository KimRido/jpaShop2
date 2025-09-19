package com.rido.shop.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAwareImpl")
//(JpaAuditing) 4. 작성자, 수정자를 위해 AuditorAwareImpl 구현하기
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //로그인 상태가 아니면 null반환
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty(); // 또는 Optional.of("system")
        }

        //로그인한 사용자의 ID 추출
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails user) {
            return Optional.ofNullable(user.getUsername());
        }

        //추출된게 String이라면
        if (principal instanceof String s) { // "anonymousUser" 등
            return "anonymousUser".equals(s) ? Optional.empty() : Optional.of(s);
        }
        
        return Optional.empty();
    }
}
