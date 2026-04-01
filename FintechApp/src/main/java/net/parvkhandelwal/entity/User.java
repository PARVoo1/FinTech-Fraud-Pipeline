package net.parvkhandelwal.entity;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document (collection = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    private ObjectId userId;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    private String email;
    @NonNull
    private String userPassword;
    private List<String> roles;

}
