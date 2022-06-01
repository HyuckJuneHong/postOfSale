package kr.co.postofsale.infrastructure.config;


import kr.co.postofsale.infrastructure.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final long MAX_AGE_SECOND = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("/**")//CORS를 적용할 URL패턴을 정의 //자원 공유를 허락할 Origin을 지정.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //허용할 HTTP method를 지정
                .allowedHeaders("*")                //허용할 헤더 지정
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECOND);            //원하는 시간만큼 pre-flight 리퀘스트를 캐싱.
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("pos/member/login");   //로그인 쪽은 예외처리
    }

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }
}
