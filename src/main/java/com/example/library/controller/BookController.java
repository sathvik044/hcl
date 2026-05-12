package com.example.library.controller;

import com.example.library.dto.request.BookRequest;
import com.example.library.dto.response.ApiResponse;
import com.example.library.dto.response.BookResponse;
import com.example.library.service.interfaces.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@Valid @RequestBody BookRequest bookRequest) {
        BookResponse response = bookService.createBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book created successfully", response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest bookRequest) {
        BookResponse response = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok(ApiResponse.success("Book updated successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable Long id) {
        BookResponse response = bookService.getBookById(id);
        return ResponseEntity.ok(ApiResponse.success("Book fetched successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookResponse>>> getAllBooks(Pageable pageable) {
        Page<BookResponse> response = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(ApiResponse.success("Books fetched successfully", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("Book deleted successfully", null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> searchBooks(@RequestParam String title, Pageable pageable) {
        Page<BookResponse> response = bookService.searchBooks(title, pageable);
        return ResponseEntity.ok(ApiResponse.success("Search results", response));
    }
}
