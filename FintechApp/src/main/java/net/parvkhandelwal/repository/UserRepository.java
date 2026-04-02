package net.parvkhandelwal.repository;

import net.parvkhandelwal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, ObjectId> {
    User findByUserName(String userName);
    void deleteByUserName(String userName);

}
