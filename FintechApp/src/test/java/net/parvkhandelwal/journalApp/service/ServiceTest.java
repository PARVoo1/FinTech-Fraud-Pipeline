package net.parvkhandelwal.journalApp.service;

import net.parvkhandelwal.entity.User;
import net.parvkhandelwal.service.UserService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ServiceTest {
    @Autowired
    private UserService userService;

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    void testAdd(User user){

        assertTrue(userService.saveNewUser(user));
    }
    @ParameterizedTest
    @CsvSource({"Parv","Ishu","Dante","ramesh","suraj"})
    void check(String a){
        User user = userService.findByUsername(a);
        assertNotNull(user);
    }

}

