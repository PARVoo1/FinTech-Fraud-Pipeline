package net.parvkhandelwal.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.dto.TransactionDTO;
import net.parvkhandelwal.entity.Transaction;
import net.parvkhandelwal.repository.TransactionRepository;
import net.parvkhandelwal.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final KafkaTemplate <String, String> kafkaTemplate;
    private final UserRepository userRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final String TOPIC = "fraud-detection-queue";

    public Transaction processTransaction(TransactionDTO transaction, String userIp){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        String userId = userRepository.findByUserName(name).getUserId().toString();
        Transaction newTransaction = new Transaction();
        newTransaction.setAccountId(transaction.getAccountId());
        newTransaction.setAmount(transaction.getAmount());
        newTransaction.setTimestamp(LocalDateTime.now());
        newTransaction.setRiskStatus("pending");
        newTransaction.setUserId(userId);
        newTransaction.setLocationIp(userIp);

        Transaction savedTransaction = transactionRepository.save(newTransaction);

        try{
            String transactionJson= objectMapper.writeValueAsString(savedTransaction);
            kafkaTemplate.send(TOPIC,transactionJson);
            log.info("Transaction sent to Kafka: "+newTransaction.getId());
        }catch (Exception e){
            log.error("Error sending transaction to Kafka: "+e.getMessage());
            savedTransaction.setRiskStatus("FAILED_SYSTEM_ERROR");
            transactionRepository.save(savedTransaction);

            throw new RuntimeException("Error processing transaction. Please try again later.");

        }
        return savedTransaction;

    }

    public Page<Transaction> getTransactionHistory(int page,int size){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        String userId = userRepository.findByUserName(name).getUserId().toString();
        Pageable pageable = PageRequest.of(page, size);

        return transactionRepository.findByUserIdOrderByTimestampDesc(userId, pageable);
    }






}
