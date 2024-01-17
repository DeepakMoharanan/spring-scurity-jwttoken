package com.Book.controller;

import com.Book.entity.User;
import com.Book.payload.LoginDto;
import com.Book.payload.SignUpDto;
import com.Book.repository.RoleRepository;
import com.Book.repository.UserRepository;
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

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    //http://localhost:8080/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return  new ResponseEntity<>("Email id exists:"+signUpDto.getEmail(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return  new ResponseEntity<>("Email id exists:"+signUpDto.getUsername(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        User saveduser = userRepository.save(user);
        return new ResponseEntity<>("user registered successfully",HttpStatus.OK);

    }
   // http://localhost:8080/api/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }
}
