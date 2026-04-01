package net.parvkhandelwal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.parvkhandelwal.entity.User;
import net.parvkhandelwal.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();


    private final UserRepository userRepository;
    public boolean saveNewUser(User user) {
        try{
            user.setUserPassword(encoder.encode(user.getUserPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.info("Error occurred in {}",user.getUserName(),e);
            return false;
        }

    }



    public void deleteByUserName(String userName) {

        userRepository.deleteByUserName(userName);
    }
    public User findByUsername(String userName)
    {
        return userRepository.findByUserName(userName);
    }
}
