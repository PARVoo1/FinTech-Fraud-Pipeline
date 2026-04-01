package net.parvkhandelwal.controller;


import lombok.RequiredArgsConstructor;
import net.parvkhandelwal.entity.Transaction;
import net.parvkhandelwal.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        try{
            Transaction processedTransaction = transactionService.processTransaction(transaction);
            return new ResponseEntity<>(processedTransaction,HttpStatus.CREATED);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public ResponseEntity<Page<Transaction>> getUserTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        try {
            return ResponseEntity.ok(transactionService.getTransactionHistory(page,size));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

}
