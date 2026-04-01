package net.parvkhandelwal.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.service.OauthGoogleService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;




@RestController
@Tag(name = "Google Oauth APIs")
@RequestMapping("/auth/google")
@RequiredArgsConstructor
@Slf4j
public class OauthGoogle {

    private final OauthGoogleService oauthGoogleService;

    @GetMapping("/login")
    @Operation(summary = "Login with Google Oauth")
    public ResponseEntity<Object> login(@RequestParam String code) {
        return oauthGoogleService.login(code);


    }










}

