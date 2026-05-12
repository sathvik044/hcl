package com.example.library.service.interfaces;

import com.example.library.dto.request.BookRequest;
import com.example.library.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse createBook(BookRequest bookRequest);
    BookResponse updateBook(Long id, BookRequest bookRequest);
    BookResponse getBookById(Long id);
    Page<BookResponse> getAllBooks(Pageable pageable);
    void deleteBook(Long id);
    Page<BookResponse> searchBooks(String title, Pageable pageable);
}
