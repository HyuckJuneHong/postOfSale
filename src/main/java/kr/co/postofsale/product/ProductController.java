package kr.co.postofsale.product;

import io.swagger.annotations.ApiOperation;
import kr.co.postofsale.common.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pos/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @ApiOperation("상품 이름 중복 확인")
    @PostMapping("/name/check")
    public ResponseFormat checkName(@RequestParam("name") String name){
        return ResponseFormat.ok(productService.checkName(name));
    }

    @ApiOperation("상품 생성")
    @PostMapping("/newInsert")
    public ResponseFormat newInsert(@RequestBody ProductDto.INSERT create){
        productService.newInsert(create);
        return ResponseFormat.ok();
    }

    @ApiOperation("상품 삭제")
    @DeleteMapping("/delete")
    public ResponseFormat delete(@RequestParam("name") String name){
        productService.deleteProduct(name);
        return  ResponseFormat.ok();
    }

    @ApiOperation("상품 정보 조회")
    @GetMapping("/read")
    public ResponseFormat<ProductDto.READ> getProductName(@RequestParam("name") String name){
        return ResponseFormat.ok(productService.getProductName(name));
    }

    @ApiOperation("모든 상품 조회")
    @GetMapping("productAll")
    public ResponseFormat<List<ProductDto.READ>> getProductAll(){
     return ResponseFormat.ok(productService.getProductAll());
    }

    @ApiOperation("상품 정보 수정")
    @PutMapping
    public ResponseFormat updateProduct(@RequestBody ProductDto.UPDATE update){
        productService.updateProduct(update);
        return ResponseFormat.ok();
    }
}
