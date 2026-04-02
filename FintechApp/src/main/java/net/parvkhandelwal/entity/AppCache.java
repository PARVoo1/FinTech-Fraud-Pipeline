package net.parvkhandelwal.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="AppCache")
public class AppCache {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;
    private String apiKey;
    private String apiName;
}
