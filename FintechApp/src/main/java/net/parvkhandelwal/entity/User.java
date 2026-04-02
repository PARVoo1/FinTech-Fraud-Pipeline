package net.parvkhandelwal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;



import java.util.List;

@Entity
@Table(name="Users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private ObjectId userId;
    @Column(unique = true, nullable=false)
    private String userName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable=false)
    private String userPassword;
    private List<String> roles;

}
