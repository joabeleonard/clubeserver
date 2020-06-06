package com.example.polls.controller;

import com.example.polls.exception.AppException;
import com.example.polls.model.Cliente;
import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import com.example.polls.model.User;
import com.example.polls.model.PreUser;
import com.example.polls.payload.ApiResponse;
import com.example.polls.payload.ClientRequest;
import com.example.polls.payload.JwtAuthenticationResponse;
import com.example.polls.payload.LoginRequest;
import com.example.polls.payload.SignUpRequest;
import com.example.polls.payload.PreUserRequest;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.repository.PreUserRepository;
import com.example.polls.security.JwtTokenProvider;
import com.example.polls.service.ClientService;
import com.example.polls.service.PreUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PreUserRepository preUserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private PreUserService preUserService;
    
    
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ClientRequest clientRequest) {

        if(userRepository.existsByEmail(clientRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "E-mail já cadastrado!"),
                    HttpStatus.BAD_REQUEST);
        }

        Cliente cliente = clientService.createClient(clientRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{clientId}")
                .buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Cliente cadastrado com Sucesso."));
 
    }




@PostMapping("/signuppreuser")
public ResponseEntity<?> registerPreClient(@Valid @RequestBody PreUserRequest preUserRequest) {

    if(preUserRepository.existsByEmail(preUserRequest.getEmail())) {
        return new ResponseEntity(new ApiResponse(false, "E-mail já cadastrado!"),
                HttpStatus.BAD_REQUEST);
    }

    PreUser preUser = preUserService.createPreUser(preUserRequest);
    
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{preUserId}")
            .buildAndExpand(preUser.getId()).toUri();

    return ResponseEntity.created(location)
            .body(new ApiResponse(true, "Cliente cadastrado com Sucesso."));

}

}
