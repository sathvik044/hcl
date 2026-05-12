package com.example.library.repository;

import com.example.library.entity.IssueRecord;
import com.example.library.enums.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {
    List<IssueRecord> findByUserIdAndStatus(Long userId, IssueStatus status);
    long countByUserIdAndStatus(Long userId, IssueStatus status);
    List<IssueRecord> findByBookIdAndStatus(Long bookId, IssueStatus status);
}
