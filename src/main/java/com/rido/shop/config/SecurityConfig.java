package com.rido.shop.config;


import com.rido.shop.util.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //다른 서버에서 내 api 호출을 막는 것
        http.csrf((csrf) -> csrf.disable());
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll()
        );

        http.addFilterBefore(new JwtFilter(), ExceptionTranslationFilter.class);


        //(JWT) 1.세션데이터 생성 비활성화
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

//        http.formLogin((formLogin) -> formLogin.loginPage("/login")
//                .defaultSuccessUrl("/")
//                //.failureUrl("/fail")
//        );

        http.logout(logout -> logout.logoutUrl("/logout"));


        return http.build();
    }
}