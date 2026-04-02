package net.parvkhandelwal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private String userId;
    @Column(unique = true, nullable=false)
    private String userName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable=false)
    private String userPassword;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

}
