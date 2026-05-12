package com.example.library.controller;

import com.example.library.dto.response.ApiResponse;
import com.example.library.service.interfaces.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping("/issue")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> issueBook(@RequestParam Long bookId, @RequestParam Long userId) {
        ApiResponse<Object> response = issueService.issueBook(bookId, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/return/{issueId}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Object>> returnBook(@PathVariable Long issueId) {
        ApiResponse<Object> response = issueService.returnBook(issueId);
        return ResponseEntity.ok(response);
    }
}
