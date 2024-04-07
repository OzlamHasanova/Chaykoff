package com.project.Chaykoff.service.impl;

import com.project.Chaykoff.constants.PaginationConstants;
import com.project.Chaykoff.dto.request.BlogRequest;
import com.project.Chaykoff.dto.response.BlogAllResponse;
import com.project.Chaykoff.dto.response.BlogResponse;
import com.project.Chaykoff.exception.BlogNotFoundException;
import com.project.Chaykoff.exception.ProductNotFoundException;
import com.project.Chaykoff.model.Blog;
import com.project.Chaykoff.model.product.Image;
import com.project.Chaykoff.repository.BlogRepository;
import com.project.Chaykoff.service.BlogService;
import com.project.Chaykoff.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final FileService fileService;
    private static final String BLOG_IMAGES_FOLDER_NAME = "Blog-images";


    @Override
    public BlogResponse addBlog(BlogRequest blogRequest, MultipartFile image) throws IOException {
        Blog blog= new Blog();
        blog.setTitle(blogRequest.getTitle());
        blog.setDescription(blogRequest.getDescription());

        String uploadedImageURL = fileService.uploadImage(image,BLOG_IMAGES_FOLDER_NAME);
        Image uploadedImage = new Image(uploadedImageURL);
        blog.setMainImage(uploadedImage);

        blogRepository.save(blog);
        return mapToBlogResponse(blog);
    }

    @Override
    public BlogResponse getBlogById(Long id) {
        Blog blog=blogRepository.findById(id).orElseThrow(
                ()->new BlogNotFoundException("Blog not found this id"+id));
        return mapToBlogResponse(blog);
    }



    @Override
    public BlogAllResponse getAllBlogs(Integer page, Integer size) {
        page = Math.max(page, Integer.parseInt(PaginationConstants.PAGE_NUMBER));
        size = size < 1 ? Integer.parseInt(PaginationConstants.PAGE_SIZE) : size;
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Blog> blogList=blogRepository.findAll(pageable);
        return new BlogAllResponse(
                blogList
                        .getContent()
                        .stream()
                        .map(this::mapToBlogResponse)
                        .toList(),
                blogList.getTotalPages(),
                blogList.getTotalElements(),
                blogList.hasNext());
    }

    @Override
    public void deleteBlog(Long id) {
        Blog blog=blogRepository.findById(id).get();
        blogRepository.delete(blog);
    }

    @Override
    public BlogResponse updateBlog(Long blogId, BlogRequest blogRequest, MultipartFile mainImage) throws IOException {
        Blog blog=blogRepository.findById(blogId).get();
        blog.setTitle(blogRequest.getTitle());
        blog.setDescription(blogRequest.getDescription());

        if (mainImage != null) {
            String uploadedImageURL = fileService.uploadImage(mainImage,BLOG_IMAGES_FOLDER_NAME);
            Image uploadedImage = new Image(uploadedImageURL);
            blog.setMainImage(uploadedImage);
        }
        blogRepository.save(blog);
        return mapToBlogResponse(blog);
    }

    private BlogResponse mapToBlogResponse(Blog blog) {
        BlogResponse response = BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .description(blog.getDescription())
                .mainImageURL(blog.getMainImage() != null ? blog.getMainImage().getImageUrl() : null)
                .build();
        return response;
    }
}
