package net.parvkhandelwal.repository;

import net.parvkhandelwal.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    Page<Transaction> findByUserIdOrderByTimestampDesc(String userId, Pageable pageable);
}
