package kr.co.postofsale.common;

import kr.co.postofsale.member.MemberRepository;
import kr.co.postofsale.member.MemberRepositoryJDBC;
import kr.co.postofsale.member.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberServiceImpl memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemberRepositoryJDBC(dataSource);
    }
}
