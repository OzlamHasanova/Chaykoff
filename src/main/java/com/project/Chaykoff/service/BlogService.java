package com.project.Chaykoff.service;

import com.project.Chaykoff.dto.request.BlogRequest;
import com.project.Chaykoff.dto.response.BlogAllResponse;
import com.project.Chaykoff.dto.response.BlogResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BlogService {
    BlogResponse addBlog(BlogRequest blogRequest, MultipartFile image) throws IOException;

    BlogResponse getBlogById(Long id);

    BlogAllResponse getAllBlogs(Integer page, Integer size);

    void deleteBlog(Long id);

    BlogResponse updateBlog(Long blogId, BlogRequest blogRequest, MultipartFile image) throws IOException;
}
