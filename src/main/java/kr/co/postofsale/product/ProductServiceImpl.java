package kr.co.postofsale.product;

import kr.co.postofsale.infrastructure.exception.BadRequestException;
import kr.co.postofsale.infrastructure.interceptor.MemberThreadLocal;
import kr.co.postofsale.member.MemberEntity;
import kr.co.postofsale.member.MemberRepository;
import kr.co.postofsale.member.enumClass.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepositoryImpl;
    private final MemberRepository memberRepositoryImpl;

    /**
     * 상품 이름 체크
     * @param name
     * @return
     */
    @Override
    public Boolean checkName(String name) {

        Boolean exist = productRepositoryImpl.existByName(name);
        return exist;
    }

    @Override
    public void newInsert(ProductDto.CREATE create) {

    }

    @Override
    public ProductDto.READ getProductName(String name) {
        return null;
    }

    @Override
    public List<ProductDto.READ> getProductAll() {
        return null;
    }

    @Override
    @Transactional
    public void updateAmount(ProductDto.UPDATE_AMOUNT update_amount) {
    }

    /**
     * 상품 수정
     * @param update
     */
    @Override
    public void updateProduct(ProductDto.UPDATE update) {

        MemberEntity memberEntity = MemberThreadLocal.get();

        if(memberEntity.getMemberRole().equals(MemberRole.ROLE_MEMBER)){
            throw new BadRequestException("관리자 및 매니저만 상품을 삭제할 수 있습니다.");
        }

        Optional<ProductEntity> productEntity = productRepositoryImpl.findByName(update.getName());

        if(!productEntity.isPresent()){
            throw new BadRequestException("삭제할 상품은 이미 존재하지 않는 상품입니다.");
        }

        ProductEntity product = productEntity.get();
        product.updateProduct(update);
        productRepositoryImpl.update(product);
    }

    /**
     * 상품 삭제
     * @param name
     */
    @Override
    @Transactional
    public void deleteProduct(String name) {

        MemberEntity memberEntity = MemberThreadLocal.get();

        if(memberEntity.getMemberRole().equals(MemberRole.ROLE_MEMBER)){
            throw new BadRequestException("관리자 및 매니저만 상품을 삭제할 수 있습니다.");
        }

        Optional<ProductEntity> productEntity = productRepositoryImpl.findByName(name);

        if(!productEntity.isPresent()){
            throw new BadRequestException("삭제할 상품은 이미 존재하지 않는 상품입니다.");
        }

        productRepositoryImpl.deleteByName(productEntity.get().getName());

    }
}
