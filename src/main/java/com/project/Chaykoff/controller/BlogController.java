package com.project.Chaykoff.controller;

import com.project.Chaykoff.constants.PaginationConstants;
import com.project.Chaykoff.dto.base.BaseResponse;
import com.project.Chaykoff.dto.request.BlogRequest;
import com.project.Chaykoff.dto.request.ProductRequest;
import com.project.Chaykoff.dto.response.BlogAllResponse;
import com.project.Chaykoff.dto.response.BlogResponse;
import com.project.Chaykoff.dto.response.ProductResponse;
import com.project.Chaykoff.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BlogResponse> addBlog(BlogRequest blogRequest,
                                @RequestParam("image") MultipartFile image) throws IOException {
        BlogResponse blogResponse=blogService.addBlog(blogRequest,image);
        return ResponseEntity.ok(blogResponse);
    }
    @PutMapping(path = "/{id}",consumes = {"multipart/form-data"})
    public ResponseEntity<BlogResponse> updateBlog(@PathVariable("id") Long blogId,
                                                         BlogRequest blogRequest,
                                                         @RequestParam(name = "image",required = false) MultipartFile image
                                                        ) throws IOException {
        return ResponseEntity.ok(blogService.updateBlog(blogId,blogRequest, image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable Long id) {
        BlogResponse blogResponse = blogService.getBlogById(id);
        return ResponseEntity.ok(blogResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<BlogAllResponse> getAllBlogs(@RequestParam(name = "pageNumber", defaultValue = PaginationConstants.PAGE_NUMBER) Integer page,
                                                          @RequestParam(name = "pageSize", defaultValue = PaginationConstants.PAGE_SIZE) Integer size){
        return ResponseEntity.ok(blogService.getAllBlogs(page,size));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteBlog(@PathVariable Long id){
        blogService.deleteBlog(id);
        return new ResponseEntity<>(new BaseResponse("delete is success"),HttpStatus.OK);
    }
}
