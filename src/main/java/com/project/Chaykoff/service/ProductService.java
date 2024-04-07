package com.project.Chaykoff.service;

import com.project.Chaykoff.dto.request.ProductRequest;
import com.project.Chaykoff.dto.request.ProductVariationRequest;

import com.project.Chaykoff.dto.response.ProductAllResponse;
import com.project.Chaykoff.dto.response.ProductResponse;
import com.project.Chaykoff.model.product.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductResponse addProduct(ProductRequest productRequest, MultipartFile image, List<MultipartFile> images) throws IOException;

    ProductResponse getProductById(Long id);

    ProductAllResponse getAllProducts(int pageNumber, int pageSize);
    public void deleteProduct(Long id);

    ProductResponse updateProduct(Long productId,ProductRequest productRequest, MultipartFile image, List<MultipartFile> images) throws IOException;
}
