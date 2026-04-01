package net.parvkhandelwal.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "app_cache")
public class AppCache {
    @Id
    private String id;
    private String apiKey;
    private String apiName;
}
