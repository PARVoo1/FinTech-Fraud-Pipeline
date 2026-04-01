package net.parvkhandelwal.journalApp.service;

import static org.mockito.Mockito.*;

import net.parvkhandelwal.entity.User;
import net.parvkhandelwal.repository.UserRepository;
import net.parvkhandelwal.service.UserDetailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class UserDetailsImplTest {

    @InjectMocks
    UserDetailServiceImpl userDetailServiceImpl;
    @Mock
    UserRepository userRepository;


    @Test
    void loadByUserNameTest() {

        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Ram").userPassword("39u42094ujv").roles(new ArrayList<>()).build());

        UserDetails user = userDetailServiceImpl.loadUserByUsername("Ram");
        Assertions.assertNotNull(user);


    }

}
