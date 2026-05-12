package com.example.library.service.interfaces;

import com.example.library.dto.response.ApiResponse;

public interface IssueService {
    ApiResponse<Object> issueBook(Long bookId, Long userId);
    ApiResponse<Object> returnBook(Long issueRecordId);
}
