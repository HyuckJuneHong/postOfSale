package kr.co.postofsale.sale;


import io.swagger.annotations.ApiOperation;
import kr.co.postofsale.common.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pos/sale")
public class SaleController {

    @Autowired
    private SaleServiceImpl saleService;

    @ApiOperation("구매할 상품 추가")
    @PostMapping("buy")
    public ResponseFormat addBuy(@RequestBody SaleDto.ADD add) {
        saleService.addBuy(add);
        return ResponseFormat.ok();
    }

    @ApiOperation("자신이 구매한 상품 목록")
    @GetMapping("/buy/mySelf")
    public ResponseFormat<List<SaleDto.READ>> getSaleMySelf(){
        return ResponseFormat.ok(saleService.getSaleMySelf());
    }

    @ApiOperation("해당 아이디로 구매한 상품 목록")
    @GetMapping("/buy/identity")
    public ResponseFormat<List<SaleDto.READ>> getSaleIdentity(@RequestParam("identity") String identity){
        return ResponseFormat.ok(saleService.getSaleIdentity(identity));
    }

    @ApiOperation("제품 이름으로 구매된 상품 목록")
    @GetMapping("/buy/productName")
    public ResponseFormat<List<SaleDto.READ>> getSaleProductName(@RequestParam("productName") String productName){
        return ResponseFormat.ok(saleService.getSaleProductName(productName));
    }

    @ApiOperation("모든 상품 구매 목록")
    @GetMapping("/buy/all")
    public ResponseFormat<List<SaleDto.READ>> getSaleAll(){
        return ResponseFormat.ok(saleService.getSaleAll());
    }
}
