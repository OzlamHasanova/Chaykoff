package com.project.Chaykoff.controller;

import com.project.Chaykoff.dto.base.BaseResponse;
import com.project.Chaykoff.constants.PaginationConstants;
import com.project.Chaykoff.dto.request.ProductRequest;
import com.project.Chaykoff.dto.response.ProductAllResponse;
import com.project.Chaykoff.dto.response.ProductResponse;
import com.project.Chaykoff.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponse> addProduct(
            ProductRequest productRequest,
            @RequestParam(name = "image",required = false) MultipartFile image,
            @RequestParam(value = "images",required = false) List<MultipartFile> images) throws IOException {

        return ResponseEntity.ok(productService.addProduct(productRequest, image,images));
    }

    @PutMapping(path = "/{id}",consumes = {"multipart/form-data"})
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long productId,
            ProductRequest productRequest,
            @RequestParam(name = "image",required = false) MultipartFile image,
            @RequestParam(value = "images",required = false) List<MultipartFile> images) throws IOException {
        return ResponseEntity.ok(productService.updateProduct(productId,productRequest, image,images));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<ProductAllResponse> getAllProducts(@RequestParam(name = "pageNumber", defaultValue = PaginationConstants.PAGE_NUMBER) Integer page,
                                                             @RequestParam(name = "pageSize", defaultValue = PaginationConstants.PAGE_SIZE) Integer size){
        return ResponseEntity.ok(productService.getAllProducts(page,size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(new BaseResponse("delete is success"),HttpStatus.OK);
    }


}
