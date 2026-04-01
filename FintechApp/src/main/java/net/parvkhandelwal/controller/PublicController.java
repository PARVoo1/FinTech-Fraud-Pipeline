package net.parvkhandelwal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.dto.UserDTO;
import net.parvkhandelwal.dto.UserLoginDto;
import net.parvkhandelwal.entity.User;
import net.parvkhandelwal.service.UserService;
import net.parvkhandelwal.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Tag(name="Public APIs")
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Slf4j
public class PublicController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Signup a new user")
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody UserDTO userDto) {
        try{
            User user = User.builder()
                    .userName(userDto.getUserName())
                    .userPassword(userDto.getUserPassword())
                    .email(userDto.getEmail())
                    .build();

            boolean isSaved=userService.saveNewUser(user);
            if(isSaved){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto user) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUserPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

    }


