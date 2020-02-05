package com.authservice.authservice.rest;

import com.authservice.authservice.dto.AuthenticationRequestDto;
import com.authservice.authservice.model.User;
import com.authservice.authservice.security.jwttoken.JwtTokenProvider;
import com.authservice.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/" )
public class AuthenticationRestController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public ResponseEntity login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        String username = authenticationRequestDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequestDto.getPassword()));
        User user = userService.findByUserName(username);
        if (user == null ) throw new UsernameNotFoundException("User with " + username + "not found");
        String token = jwtTokenProvider.createToken(username, user.getRoles());
        Map<Object, Object> response = new HashMap<>();
        //создать отдельную сущность AuthenticationResponse
        response.put("username", username);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
