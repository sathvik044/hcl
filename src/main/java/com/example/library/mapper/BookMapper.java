package com.example.library.mapper;

import com.example.library.dto.request.BookRequest;
import com.example.library.dto.response.BookResponse;
import com.example.library.entity.Book;
import com.example.library.enums.BookStatus;

public class BookMapper {

    public static Book toEntity(BookRequest request) {
        if (request == null) return null;
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .category(request.getCategory())
                .status(BookStatus.AVAILABLE)
                .build();
    }

    public static BookResponse toResponseDTO(Book book) {
        if (book == null) return null;
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .status(book.getStatus())
                .category(book.getCategory())
                .build();
    }
}
