package net.parvkhandelwal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    @NotBlank(message = "Account ID cannot be blank")
    private String accountId;
    @NonNull
    private Double amount;
}
