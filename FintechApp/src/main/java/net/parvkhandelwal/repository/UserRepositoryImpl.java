package net.parvkhandelwal.repository;

import lombok.RequiredArgsConstructor;
import net.parvkhandelwal.entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserRepositoryImpl {

    private final MongoTemplate mongoTemplate;

    public List<User> getUserForSa(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"));
        return mongoTemplate.find(query, User.class);

    }


}
