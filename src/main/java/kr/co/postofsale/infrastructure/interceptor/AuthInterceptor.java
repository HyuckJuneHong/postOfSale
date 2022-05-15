package kr.co.postofsale.infrastructure.interceptor;


import kr.co.postofsale.infrastructure.security.jwt.JwtTokenProvider;
import kr.co.postofsale.member.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //컨트롤러 호출 전 실행되는 메소드로 true 반환 시 메소드 살행 후 핸들러에 접근을 하고 false 반환 작업을 중단해 컨트롤러 및 핸들러 실행이 중단된다.
    @Override
    public boolean preHandle(HttpServletRequest request
            , @NotNull HttpServletResponse response, @NotNull Object object){

        String token = request.getHeader("Authorization");

        if(token == null){
            return true;
        }

        MemberEntity memberEntity = jwtTokenProvider.findMemberByToken(token);
        MemberThreadLocal.set(memberEntity);

        return true;
    }
}
