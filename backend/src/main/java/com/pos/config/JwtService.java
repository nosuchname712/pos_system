package com.pos.config;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public String generateToken(String username) {
        return "demo-token";
    }
}