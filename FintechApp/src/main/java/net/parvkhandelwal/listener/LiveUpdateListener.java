package net.parvkhandelwal.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.repository.TransactionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LiveUpdateListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final TransactionRepository transactionRepository;
    private final ObjectMapper objectMapper=new ObjectMapper();

    @KafkaListener(topics = "fraud-results-queue",groupId = "websocket-group")
    public void broadcastLiveUpdate(String message){
        try{
            JsonNode jsonNode=objectMapper.readTree(message);
            String transactionId=jsonNode.get("transactionId").asText();
            String finalStatus=jsonNode.get("status").asText();

            transactionRepository.findById(transactionId).ifPresent(transaction -> {
                transaction.setRiskStatus(finalStatus);

                String radioChannel="/topic/transactions/"+transaction.getUserId();
                messagingTemplate.convertAndSend(radioChannel,transaction);

                log.info("Live update pushed to on channel "+ radioChannel);
            });
        }catch(Exception e){
            log.error("Websocket broadcast failed"+ e.getMessage());
        }


    }

}
