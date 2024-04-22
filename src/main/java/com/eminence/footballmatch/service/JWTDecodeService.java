package com.eminence.footballmatch.service;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class JWTDecodeService {

    public String decodeJwt(String jwtToken) {
        String[] jwtParts = jwtToken.split("\\.");

        if (jwtParts.length != 3) {
            return "Invalid JWT format";
        }

        String header = decodeBase64(jwtParts[0]);
        String payload = decodeBase64(jwtParts[1]);
        String signature = jwtParts[2];

        StringBuilder response = new StringBuilder();
        response.append("Header: ").append(header).append("\n");
        response.append("Payload: ").append(payload).append("\n");
        response.append("Signature: ").append(signature);

        return response.toString();
    }

    private String decodeBase64(String encodedString) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedString);
        return new String(decodedBytes);
    }
}
