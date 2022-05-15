package com.example.qwait.Services;

import com.example.qwait.DTOs.Mail;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service@AllArgsConstructor@Slf4j
public class OtpService {

    private JavaMailSender javaMailSender;


    public void sendMail(Mail mail) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail.getRecipient());
        msg.setSubject(mail.getSubject());
        msg.setText(mail.getMessage());
        this.javaMailSender.send(msg);
        log.info("Otp sent - {}", new Date());
    }

    private static final Integer EXPIRE_MINS = 5;

    private LoadingCache<String, Integer> otpCache;

    public OtpService(){
        super();
        otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }
    public int generateOTP(String key){

        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        otpCache.put(key, otp);
        return otp;
    }
    public int getOtp(String key){
        try{
            return otpCache.get(key);
        }catch (Exception e){
            return 0;
        }
    }
    public void clearOTP(String key){
        otpCache.invalidate(key);
    }
    public boolean otpExpired(int otp,String key)
    {
        return otp == this.getOtp(key);
    }
}
