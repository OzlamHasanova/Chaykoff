package com.project.Chaykoff.service.impl;

import com.project.Chaykoff.constants.PaginationConstants;
import com.project.Chaykoff.dto.request.ProductRequest;
import com.project.Chaykoff.dto.response.ProductAllResponse;
import com.project.Chaykoff.dto.response.ProductResponse;
import com.project.Chaykoff.exception.ProductNotFoundException;
import com.project.Chaykoff.model.product.Image;
import com.project.Chaykoff.model.product.Ingredient;
import com.project.Chaykoff.model.product.Product;
import com.project.Chaykoff.repository.IngredientRepository;
import com.project.Chaykoff.repository.ProductRepository;
import com.project.Chaykoff.service.FileService;
import com.project.Chaykoff.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FileService fileService;
    private final IngredientRepository ingredientRepository;
    private static final String PRODUCT_IMAGES_FOLDER_NAME = "Product-images";

    @Override
    public ProductResponse addProduct(ProductRequest productRequest, MultipartFile mainImage, List<MultipartFile> images) throws IOException {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());

        String uploadedImageURL = fileService.uploadImage(mainImage,PRODUCT_IMAGES_FOLDER_NAME);
        Image uploadedImage = new Image(uploadedImageURL);
        product.setMainImage(uploadedImage);

        Set<Image> productVarImages = new HashSet<>();
        for (MultipartFile image : images) {
            String uploadedImagesURL = fileService.uploadImage(image,PRODUCT_IMAGES_FOLDER_NAME);
            Image uploadedImages = new Image(uploadedImagesURL);
            productVarImages.add(uploadedImages);
        }
        product.setImages(productVarImages);

        Set<Ingredient> ingredients = new HashSet<>();
        for (String ingredientName : productRequest.getIngredients()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientName);
            ingredientRepository.save(ingredient);
            ingredients.add(ingredient);
        }
        product.setIngredients(ingredients);
        productRepository.save(product);
        return mapToProductResponse(product);
    }


    @Override
    public ProductResponse getProductById(Long id) {
        Product product=productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found with id "+id));
        ProductResponse productResponse=mapToProductResponse(product);
        return productResponse;
    }
    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .title(product.getTitle())
                .price(product.getPrice())
                .mainImageURL(product.getMainImage() != null ? product.getMainImage().getImageUrl() : null)
                .imageURLs(product.getImages().stream().map(Image::getImageUrl).collect(Collectors.toSet()))
                .ingredients(product.getIngredients().stream().map(Ingredient::getName).collect(Collectors.toSet()))
                .build();
        return response;
    }



    @Override
    public ProductAllResponse getAllProducts(int pageNumber, int pageSize) {
        pageNumber = Math.max(pageNumber, Integer.parseInt(PaginationConstants.PAGE_NUMBER));
        pageSize = pageSize < 1 ? Integer.parseInt(PaginationConstants.PAGE_SIZE) : pageSize;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<Product> productList=productRepository.findAll(pageable);
        return new ProductAllResponse(
                productList
                        .getContent()
                        .stream()
                        .map(this::mapToProductResponse)
                        .toList(),
                productList.getTotalPages(),
                productList.getTotalElements(),
                productList.hasNext());
    }

    @Override
    public void deleteProduct(Long id) {
        Product product=productRepository.findById(id).get();
        productRepository.delete(product);
    }

    @Override
    public ProductResponse updateProduct(Long productId,ProductRequest productRequest, MultipartFile mainImage, List<MultipartFile> images) throws IOException {
        Product product=productRepository.findById(productId).orElseThrow();
        product.setName(productRequest.getName());
        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());

        if (mainImage != null) {
            String uploadedImageURL = fileService.uploadImage(mainImage,PRODUCT_IMAGES_FOLDER_NAME);
            Image uploadedImage = new Image(uploadedImageURL);
            product.setMainImage(uploadedImage);
        }

        if (images != null && !images.isEmpty()) {
            Set<Image> productVarImages = new HashSet<>();
            for (MultipartFile image : images) {
                String uploadedImagesURL = fileService.uploadImage(image,PRODUCT_IMAGES_FOLDER_NAME);
                Image uploadedImages = new Image(uploadedImagesURL);
                productVarImages.add(uploadedImages);
            }
            product.getImages().addAll(productVarImages);
        }

        Set<Ingredient> ingredients = new HashSet<>();
        for (String ingredientName : productRequest.getIngredients()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientName);
            ingredientRepository.save(ingredient);
            ingredients.add(ingredient);
        }
        product.setIngredients(ingredients);
        productRepository.save(product);
        return mapToProductResponse(product);
    }
}
