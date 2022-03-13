package com.yuvaraj.securityservice;

import com.yuvaraj.securityservice.helpers.TokenType;
import com.yuvaraj.securityservice.services.JwtGenerationService;
import com.yuvaraj.securityservice.services.JwtManagerService;
import com.yuvaraj.securityservice.services.impl.JwtGenerationServiceImpl;
import com.yuvaraj.securityservice.services.impl.JwtManagerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SecurityServiceApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(getClass());


    JwtGenerationService jwtGenerationService;

    private final String TOKEN_TYPE = "TOKEN_TYPE";
    private final String ENV = "ENV";
    private final String DEV = "DEV";

    public SecurityServiceApplication() throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.jwtGenerationService = new JwtGenerationServiceImpl(new JwtManagerServiceImpl());
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> config = checkArgs(args);
        switch(TokenType.valueOf(config.get(TOKEN_TYPE))){
            case REFRESH:
                logger.info("{}", jwtGenerationService.generateRefreshToken(config.get("customerId")));
                break;
            case SESSION:
                logger.info("{}", jwtGenerationService.generateSessionToken(config.get("customerId")));
                break;
            default:
                throw new Exception("Token type not handled");
        }

    }

    private Map<String, String> checkArgs(String[] args) throws Exception {
        if(null == args || args.length < 2){
            throw new Exception("Args cannot be null or empty or not sufficient");
        }
        Map<String, String> configs = new HashMap<>();
        try{
            configs.put(TOKEN_TYPE, TokenType.valueOf(args[0].toUpperCase()).getType());
        }catch (Exception e){
            throw new Exception("Invalid Token type");
        }
        configs.put("customerId", args[1]);
        configs.put(ENV, DEV);
        if(args.length > 2){
            configs.put(ENV, args[2].toUpperCase());
        }
        return configs;
    }
}
