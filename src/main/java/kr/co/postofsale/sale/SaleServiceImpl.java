package kr.co.postofsale.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService{

    private final SaleRepository saleRepository;

    @Override
    public void addBuy(SaleDto.ADD add) {

    }

    @Override
    public List<SaleDto.READ> getSaleMySelf() {
        return null;
    }

    @Override
    public List<SaleDto.READ> getSaleIdentity(String identity) {
        return null;
    }

    @Override
    public List<SaleDto.READ> getSaleProductName(String productName) {
        return null;
    }

    @Override
    public List<SaleDto.READ> getSaleAll() {
        return null;
    }

    @Override
    public void deleteSale(SaleDto.DELETE delete) {

    }
}
