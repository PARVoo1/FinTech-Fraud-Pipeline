package net.parvkhandelwal.listener;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.repository.TransactionRepository;
import net.parvkhandelwal.repository.UserRepository;
import net.parvkhandelwal.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionListener {

    private final ObjectMapper objectMapper=new ObjectMapper();
    private final TransactionRepository transactionRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

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

                if ("REJECTED".equalsIgnoreCase(finalStatus)) {



                    userRepository.findById(transaction.getUserId()).ifPresent(user -> {
                        String subject = "URGENT: Transaction Blocked";
                        String body = "Hello " + user.getUserName() + ",\n\n" +
                                "We blocked a suspicious transaction of $" + transaction.getAmount() + ".\n" +
                                "If this was not you, please secure your account.";

                        emailService.sendEmail(user.getEmail(), subject, body);
                        log.info("🚨 Fraud alert email sent to: " + user.getEmail());
                    });
                }
            });




        }catch (Exception e){
            log.error("error while listening to transaction event: "+e.getMessage());
        }

    }


}
