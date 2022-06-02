package kr.co.postofsale.sale;

import kr.co.postofsale.infrastructure.exception.BadRequestException;
import kr.co.postofsale.infrastructure.interceptor.MemberThreadLocal;
import kr.co.postofsale.member.MemberEntity;
import kr.co.postofsale.member.MemberRepository;
import kr.co.postofsale.member.enumClass.MemberRole;
import kr.co.postofsale.product.ProductEntity;
import kr.co.postofsale.product.ProductRepository;
import kr.co.postofsale.sale.enumClass.SalePayment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService{

    private final SaleRepository saleRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    /**
     * 판매 서비스
     * @param add
     */
    @Override
    @Transactional
    public void addBuy(SaleDto.ADD add) {

        MemberEntity memberEntity = MemberThreadLocal.get();

        if(!memberRepository.existByIdentity(memberEntity.getIdentity())){
            throw new BadRequestException("구매하려면 로그인을 하셔야 합니다.");
        }

        Optional<ProductEntity> productEntity = productRepository.findByName(add.getBuyProductName());

        if(!productEntity.isPresent()){
            throw new BadRequestException("해당 상품은 존재하지 않습니다.");
        }

        if(productEntity.get().getAmount() < add.getBuyAmount()){
            throw new BadRequestException("현재 상품은 수량이 부족합니다. 더 적은 수량을 적어주세요.");
        }

        SaleEntity saleEntity = SaleEntity.builder()
                .buyIdentity(memberEntity.getIdentity())
                .buyProductName(add.getBuyProductName())
                .buyAmount(add.getBuyAmount())
                .totalPrice(productEntity.get().getPrice()* add.getBuyAmount())
                .salePayment(SalePayment.of(add.getSalePayment()))
                .build();

        saleEntity.setInsertDate(new Timestamp(System.currentTimeMillis()));
        ProductEntity product = productEntity.get();
        product.updateAmount(product.getAmount() - add.getBuyAmount());

        saleRepository.save(saleEntity);
        productRepository.saleProduct(product);
    }

    /**
     * 자신의 모든 구매 정보 조회
     * @return
     */
    @Override
    public List<SaleDto.READ> getSaleMySelf() {

        MemberEntity memberEntity = MemberThreadLocal.get();

        List<SaleEntity> saleEntities = saleRepository.findByIdentity(memberEntity.getIdentity());
        List<SaleDto.READ> readList = new ArrayList<>();

        for(SaleEntity sale : saleEntities){
            SaleDto.READ read = SaleDto.READ.builder()
                    .buyIdentity(sale.getBuyIdentity())
                    .buyProductName(sale.getBuyProductName())
                    .buyAmount(sale.getBuyAmount())
                    .salePayment(sale.getSalePayment())
                    .build();
            readList.add(read);
        }
        return readList;
    }

    /**
     * 해당 아이디 구매 정보 조회
     * @param identity
     * @return
     */
    @Override
    public List<SaleDto.READ> getSaleIdentity(String identity) {

        MemberEntity memberEntity = MemberThreadLocal.get();

        if(memberEntity.getMemberRole().equals(MemberRole.ROLE_MEMBER)) {
            throw new BadRequestException("권한이 없습니다. (매니저 혹은 관리자만 가능)");
        }

        List<SaleEntity> saleEntities = saleRepository.findByIdentity(identity);
        List<SaleDto.READ> readList = new ArrayList<>();

        for(SaleEntity sale : saleEntities){
            SaleDto.READ read = SaleDto.READ.builder()
                    .buyIdentity(sale.getBuyIdentity())
                    .buyProductName(sale.getBuyProductName())
                    .buyAmount(sale.getBuyAmount())
                    .salePayment(sale.getSalePayment())
                    .build();
            readList.add(read);
        }
        return readList;
    }

    /**
     * 해당 상품 구매 정보 조회
     * @param productName
     * @return
     */
    @Override
    public List<SaleDto.READ> getSaleProductName(String productName) {

        MemberEntity memberEntity = MemberThreadLocal.get();

        if(memberEntity.getMemberRole().equals(MemberRole.ROLE_MEMBER)) {
            throw new BadRequestException("권한이 없습니다. (매니저 혹은 관리자만 가능)");
        }

        List<SaleEntity> saleEntities = saleRepository.findByProductName(productName);
        List<SaleDto.READ> readList = new ArrayList<>();

        for(SaleEntity sale : saleEntities){
            SaleDto.READ read = SaleDto.READ.builder()
                    .buyIdentity(sale.getBuyIdentity())
                    .buyProductName(sale.getBuyProductName())
                    .buyAmount(sale.getBuyAmount())
                    .salePayment(sale.getSalePayment())
                    .build();
            readList.add(read);
        }
        return readList;
    }

    /**
     * 모든 구매 정보 조회
     * @return
     */
    @Override
    public List<SaleDto.READ> getSaleAll() {

        MemberEntity memberEntity = MemberThreadLocal.get();

        if(memberEntity.getMemberRole().equals(MemberRole.ROLE_MEMBER)) {
            throw new BadRequestException("권한이 없습니다. (매니저 혹은 관리자만 가능)");
        }

        List<SaleEntity> saleEntities = saleRepository.findAll();
        List<SaleDto.READ> readList = new ArrayList<>();

        for(SaleEntity sale : saleEntities){
            SaleDto.READ read = SaleDto.READ.builder()
                    .buyIdentity(sale.getBuyIdentity())
                    .buyProductName(sale.getBuyProductName())
                    .buyAmount(sale.getBuyAmount())
                    .salePayment(sale.getSalePayment())
                    .build();
            readList.add(read);
        }
        return readList;
    }

    /**
     * 자신의 모든 구매 정보 삭제
     * @param password
     */
    @Override
    @Transactional
    public void deleteMySelf(String password) {

        MemberEntity memberEntity = MemberThreadLocal.get();
        if(!passwordEncoder.matches(password, memberEntity.getPassword())){
            throw new BadRequestException("비밀번호 일치하지 않음");
        }

        saleRepository.findByIdentity(memberEntity.getIdentity());

    }

    /**
     * 모든 구매 정보 삭제
     */
    @Override
    @Transactional
    public void deleteAll() {
        MemberEntity memberEntity = MemberThreadLocal.get();
        if(memberEntity.getMemberRole().equals(MemberRole.ROLE_MEMBER)){
            throw new BadRequestException("관리자 및 매니저만 모든 구매 정보를 삭제할 수 있습니다.");
        }

        saleRepository.deleteAll();
    }
}
