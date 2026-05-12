package com.example.library.service.impl;

import com.example.library.dto.request.BookRequest;
import com.example.library.dto.response.BookResponse;
import com.example.library.entity.Book;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import com.example.library.service.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookResponse createBook(BookRequest bookRequest) {
        Book book = BookMapper.toEntity(bookRequest);
        return BookMapper.toResponseDTO(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setIsbn(bookRequest.getIsbn());
        book.setCategory(bookRequest.getCategory());
        
        return BookMapper.toResponseDTO(bookRepository.save(book));
    }

    @Override
    public BookResponse getBookById(Long id) {
        return bookRepository.findById(id)
                .map(BookMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Override
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(BookMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookResponse> searchBooks(String title, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable).map(BookMapper::toResponseDTO);
    }
}
