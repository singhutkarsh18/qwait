package com.example.qwait.Controller;

import com.example.qwait.JWT.JwtRequest;
import com.example.qwait.JWT.JwtUtil;
import com.example.qwait.Repository.UserRepository;
import com.example.qwait.Services.RegisterService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")@Slf4j
@AllArgsConstructor@RequestMapping("/api")
public class JwtAuthenticationController {


    private JwtUtil jwtUtil;


    private UserDetailsService userDetailsService;

    private UserRepository userRepository;
    private RegisterService registerService;
    @PostMapping("/authenticate")
    public ResponseEntity<?> createStudentAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {

        try {
            boolean auth = registerService.authenticateStudent(authenticationRequest.getUsername(), authenticationRequest.getPassword());

                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(authenticationRequest.getUsername());

                final String access_token = jwtUtil.generateAccessToken(userDetails);
                final String refresh_token = jwtUtil.generateRefreshToken(userDetails);
                Map<String, String> token = new HashMap<>();
                token.put("access_token", access_token);
                token.put("refresh_token", refresh_token);
                return ResponseEntity.ok(token);

        }
        catch (UsernameNotFoundException e1)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getLocalizedMessage());
        }
        catch (IllegalStateException e2)
        {
            if(e2.getLocalizedMessage().equals("not verified"))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e2.getLocalizedMessage());
            else if(e2.getLocalizedMessage().equals("password not set"))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e2.getLocalizedMessage());
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e2.getLocalizedMessage());
        }
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
        try {
            DefaultClaims claims = (DefaultClaims) request.getAttribute("claims");
            String refreshToken = request.getHeader("refresh-token");
            if (userRepository.findByUsername(jwtUtil.getUsernameFromToken(refreshToken)).isEmpty())
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not present");
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.getUsernameFromToken(refreshToken));

            String access_token = jwtUtil.generateAccessToken(userDetails);
            Map<String, String> token = new HashMap<>();
            token.put("access_token", access_token);
            return ResponseEntity.ok(token);
        }
        catch(ExpiredJwtException e1)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e1.getLocalizedMessage());
        }
        catch (SignatureException e2)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e2.getLocalizedMessage());
        }
    }
    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(Principal principal)
    {
        try{
            Map<String,String> m = new HashMap<>();
            m.put("name",userRepository.findByUsername(principal.getName()).get().getName());
            m.put("username",userRepository.findByUsername(principal.getName()).get().getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(m);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error");
        }
    }



}