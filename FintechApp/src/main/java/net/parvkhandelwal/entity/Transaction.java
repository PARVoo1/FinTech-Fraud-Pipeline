package net.parvkhandelwal.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transactions")
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    private String id;

    private String userId;

    @NotBlank
    private String accountId;

    @NonNull
    private Double amount;

    @NotBlank
    private String locationIp;

    private String riskStatus;

    private LocalDateTime timestamp;


}
