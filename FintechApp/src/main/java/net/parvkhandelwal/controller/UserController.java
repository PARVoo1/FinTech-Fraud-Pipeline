package net.parvkhandelwal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.parvkhandelwal.api.response.QuoteResponse;
import net.parvkhandelwal.api.response.WeatherResponse;
import net.parvkhandelwal.dto.UserDTO;
import net.parvkhandelwal.entity.User;
import net.parvkhandelwal.service.QuoteService;
import net.parvkhandelwal.service.UserService;
import net.parvkhandelwal.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name="User APIs")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final WeatherService weatherService;
    private final QuoteService quoteService;


    @Operation(summary = "Delete the user account")
    @DeleteMapping
    public ResponseEntity<Object> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Operation(summary = "Update the user account")
    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User userInDb= userService.findByUsername(userName);

        if (userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setUserPassword(user.getUserPassword());
            userService.saveNewUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Operation(summary = " Greetings")
    @GetMapping
    public ResponseEntity<Object> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        WeatherResponse city = weatherService.response("Alwar");
        QuoteResponse quote = quoteService.getQuote();

        if (city != null&& city.getMain()!=null) {
            double temp = city.getMain().getFeelsLike();
            String greetings="Hi "+user+" Weather feels like " +temp;
            if(quote!=null) {
                greetings +="\nQuote of the day: \"" + quote.getQ() + "\" \n-" + quote.getA();
                return new ResponseEntity<>(greetings,HttpStatus.OK);
            }
            greetings="Hi "+user+" Weather feels like " +temp;
            return  new ResponseEntity<>(greetings,HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
