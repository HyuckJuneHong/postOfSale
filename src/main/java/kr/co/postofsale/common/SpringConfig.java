package kr.co.postofsale.common;

import kr.co.postofsale.member.MemberRepository;
import kr.co.postofsale.member.MemberRepositoryJDBC;
import kr.co.postofsale.member.MemberServiceImpl;
import kr.co.postofsale.product.ProductRepository;
import kr.co.postofsale.product.ProductRepositoryJDBC;
import kr.co.postofsale.product.ProductServiceImpl;
import kr.co.postofsale.sale.SaleRepository;
import kr.co.postofsale.sale.SaleRepositoryJDBC;
import kr.co.postofsale.sale.SaleService;
import kr.co.postofsale.sale.SaleServiceImpl;
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

//    @Bean
//    public ProductServiceImpl productService(){
//        return new ProductServiceImpl(productRepository());
//    }
//
//    @Bean
//    public ProductRepository productRepository(){
//        return new ProductRepositoryJDBC(dataSource);
//    }
//
//    @Bean
//    public SaleServiceImpl saleService(){
//        return new SaleServiceImpl(saleRepository());
//    }
//
//    @Bean
//    public SaleRepository saleRepository(){
//        return new SaleRepositoryJDBC(dataSource);
//    }
}
