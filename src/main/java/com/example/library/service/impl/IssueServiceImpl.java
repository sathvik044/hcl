package com.example.library.service.impl;

import com.example.library.dto.response.ApiResponse;
import com.example.library.entity.Book;
import com.example.library.entity.IssueRecord;
import com.example.library.entity.User;
import com.example.library.enums.BookStatus;
import com.example.library.enums.IssueStatus;
import com.example.library.exception.BadRequestException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.BookRepository;
import com.example.library.repository.IssueRecordRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.interfaces.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final IssueRecordRepository issueRecordRepository;

    @Override
    @Transactional
    public ApiResponse<Object> issueBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new BadRequestException("Book is not available for issue");
        }

        long activeIssues = issueRecordRepository.countByUserIdAndStatus(userId, IssueStatus.ACTIVE);
        if (activeIssues >= 3) {
            throw new BadRequestException("Member can issue a maximum of 3 books");
        }

        book.setStatus(BookStatus.ISSUED);
        bookRepository.save(book);

        IssueRecord record = IssueRecord.builder()
                .book(book)
                .user(user)
                .issueDate(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(14))
                .status(IssueStatus.ACTIVE)
                .build();

        issueRecordRepository.save(record);

        return ApiResponse.success("Book issued successfully", null);
    }

    @Override
    @Transactional
    public ApiResponse<Object> returnBook(Long issueRecordId) {
        IssueRecord record = issueRecordRepository.findById(issueRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue record not found"));

        if (record.getStatus() == IssueStatus.RETURNED) {
            throw new BadRequestException("Book already returned");
        }

        record.setStatus(IssueStatus.RETURNED);
        record.setReturnDate(LocalDateTime.now());
        issueRecordRepository.save(record);

        Book book = record.getBook();
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        return ApiResponse.success("Book returned successfully", null);
    }
}
