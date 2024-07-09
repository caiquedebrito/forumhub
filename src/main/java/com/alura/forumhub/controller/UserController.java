package com.alura.forumhub.controller;

import com.alura.forumhub.domain.user.*;
import com.alura.forumhub.infra.security.TokenJWTData;
import com.alura.forumhub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationData data) {
        var tokenJWT = getToken(data.email(), data.password());

        return ResponseEntity.ok(new TokenJWTData(tokenJWT));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserData data, UriComponentsBuilder uriBuilder) {
        var user = new User(data);
        userRepository.save(user);

        var tokenJWT = getToken(data.email(), data.password());

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new CreatedUserData(user, tokenJWT));
    }

    private String getToken(String email, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(email, password);

        var authentication = manager.authenticate(authToken);

        return tokenService.generateToken((User) authentication.getPrincipal());
    }
}
