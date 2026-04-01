package net.parvkhandelwal.listener;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.repository.TransactionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionListener {

    private final ObjectMapper objectMapper=new ObjectMapper();
    private final TransactionRepository transactionRepository;

    @KafkaListener(topics = "fraud-results-queue",groupId = "fintech-group")
    public void listen(String message) {
        try{
            JsonNode jsonNode=objectMapper.readTree(message);
            String transactionId = jsonNode.get("transactionId").asText();
            String finalStatus=jsonNode.get("status").asText();

            transactionRepository.findById(transactionId).ifPresent(transaction -> {
                transaction.setRiskStatus(finalStatus);
                transactionRepository.save(transaction);
                log.info("💾 Transaction " + transactionId + " updated with status: " + finalStatus);
            });


        }catch (Exception e){
            log.error("error while listening to transaction event: "+e.getMessage());
        }

    }


}
