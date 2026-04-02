package net.parvkhandelwal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(name="Transactions")
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String userId;

    @NotBlank(message = "Account ID cannot be blank")
    private String accountId;

    @NonNull
    private Double amount;

    private String locationIp;

    private String riskStatus;

    private LocalDateTime timestamp;


}
