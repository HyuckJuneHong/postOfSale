package kr.co.postofsale.infrastructure.security.jwt;


import kr.co.postofsale.infrastructure.exception.JwtTokenExpiredException;
import kr.co.postofsale.infrastructure.exception.JwtTokenInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/** extends OncePerRequestFilter
 * - 동일한 request 안에서 한번만 필터링을 할 수 있게 해주기 위해 상속
 * - doFilterInternal 메소드 제공
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final HandlerExceptionResolver handlerExceptionResolver;

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    //Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록한다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("메세지 ~!~!~!");

        // 헤더에서 JWT를 받아옴.
        final Optional<String> token = jwtTokenProvider.resolveToken(request);

        try {
            // 유효한 토큰인지 확인합니다.
            if(token.isPresent() && jwtTokenProvider.isUsable(token.get())) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication auth = jwtTokenProvider.getAuthentication(token.get());
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtTokenExpiredException | JwtTokenInvalidException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
