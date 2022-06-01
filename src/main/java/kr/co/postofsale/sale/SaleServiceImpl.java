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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService{

    private final SaleRepository saleRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

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

    @Override
    public void deleteSale(SaleDto.DELETE delete) {

    }
}
