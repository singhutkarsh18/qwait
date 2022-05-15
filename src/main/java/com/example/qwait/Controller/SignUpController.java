package com.example.qwait.Controller;


import com.example.qwait.DTOs.OTP;
import com.example.qwait.DTOs.PasswordDto;
import com.example.qwait.DTOs.RegistrationRequest;
import com.example.qwait.JWT.JwtUtil;
import com.example.qwait.Model.PasswordChangeDTO;
import com.example.qwait.Repository.UserRepository;
import com.example.qwait.Services.RegisterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.Map;

@RestController @AllArgsConstructor
@RequestMapping("/api/signup")@Slf4j
@CrossOrigin("*")
public class SignUpController {

    private RegisterService registerService;
    private UserRepository userRepository;
    private UserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request )
    {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(registerService.signUp(request));
        }
        catch (IllegalStateException e)
        {
            if(e.getLocalizedMessage().equals("Invalid email"))
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getLocalizedMessage());
            else if(e.getLocalizedMessage().equals("not verified"))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getLocalizedMessage());
            else if(e.getLocalizedMessage().equals("password not set"))
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getLocalizedMessage());
            else
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(e.getLocalizedMessage());

        }
    }
    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody OTP otp)
    {
        try{
            if(registerService.verifyAcc(otp.getUserOtp(), otp.getUsername()))
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("User verified");
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect OTP");
        }
        catch (NullPointerException n)
        {
            log.warn(n.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Null");
        }
        catch ( UsernameNotFoundException e)
        {
            log.warn(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
        }
    }
    @PostMapping("/setPassword")
    public ResponseEntity<?> setPassword(@RequestBody PasswordDto passwordDto)
    {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(registerService.createPassword(passwordDto));
        }
        catch(UsernameNotFoundException e1)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getLocalizedMessage());
        }
        catch (IllegalStateException e2)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e2.getLocalizedMessage());
        }
        catch (EntityNotFoundException e3)
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e3.getLocalizedMessage());
        }
        catch (UnsupportedOperationException e4)
        {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(e4.getLocalizedMessage());
        }
    }
    @PostMapping("/resend")
    public ResponseEntity<?> resend(@RequestBody Map<String,String> username)
    {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(registerService.resendOtp(username.get("username")));
        }
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
        }
    }
    @PostMapping("/forgot")
    public ResponseEntity<?> forgot(@RequestBody Map<String,String> username)
    {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(registerService.forgotPassword(username.get("username")));
        }
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
        }
        catch (IllegalStateException e1)
        {
            if(e1.getLocalizedMessage().equals("Invalid email"))
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e1.getLocalizedMessage());
            else if(e1.getLocalizedMessage().equals("not verified"))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e1.getLocalizedMessage());
            else if(e1.getLocalizedMessage().equals("password not set"))
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e1.getLocalizedMessage());
            else
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(e1.getLocalizedMessage());
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO, Principal principal)
    {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(registerService.changePassword(passwordChangeDTO,principal));
        }
        catch(UsernameNotFoundException e1)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getLocalizedMessage());
        }
        catch (IllegalArgumentException e2)
        {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e2.getLocalizedMessage());
        }
        catch (IllegalStateException e3)
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e3.getLocalizedMessage());
        }
        catch (RuntimeException e4)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e4.getLocalizedMessage());
        }
    }
}
