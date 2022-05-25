package kr.co.postofsale.infrastructure.security;

import kr.co.postofsale.infrastructure.security.jwt.JwtAuthenticationFilter;
import kr.co.postofsale.infrastructure.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final HandlerExceptionResolver handlerExceptionResolver;

    private static final String[] AUTH_ARR = {
            "/pos",
            "/swagger/**",
            "/swagger-ui.html",
            "/swagger-resources/**"
    };

    /**
     * 보안 예외 처리
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(AUTH_ARR);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() //rest api만을 고려해 기본 설정은 해제
                .csrf().disable()      //csrf 보안 토큰 disable 처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //JWT 토큰 기반 인증이므로 세션 사용 안함

                .and()
                .authorizeRequests()
                .antMatchers("pos/**").permitAll()
                .antMatchers("/*/login", "/*/signUp").permitAll()

                .antMatchers(HttpMethod.OPTIONS).permitAll()    //CORS 프론트 단 (시큐리티) 따로 공부하기

                .and() //지정된 필터 앞에 커스텀 필터를 추가
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, handlerExceptionResolver),
                        UsernamePasswordAuthenticationFilter.class);  //jwt Token 필터를 id/pw 인증 필터 전에 넣음

    }

    // 암호화에 필요한 PasswordEncoder Bean 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}