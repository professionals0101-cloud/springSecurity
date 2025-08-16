package com.vipul.springSecurity.security;
import com.vipul.springSecurity.dto.OtpRequest;
import com.vipul.springSecurity.dto.VerifyOtpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil = new JwtUtil();

    // Mock OTP store (in real life, use DB or cache like Redis)
    private final Map<String, String> otpStore = new HashMap<>();

    @PostMapping("/send-otp")
    public OtpResponse sendOtp(@RequestBody OtpRequest request) {
        String otp = String.valueOf((int)(Math.random() * 9000) + 1000);
        otpStore.put(request.getMobile(), otp);

        // In real system → send via SMS/Email
        System.out.println("OTP for " + request.getMobile() + " is: " + otp);

        return new OtpResponse(otp);
    }

    @PostMapping("/verify-otp")
    public TokenResponse verifyOtp(@RequestBody VerifyOtpRequest request) {
        String storedOtp = otpStore.get(request.getMobile());

        if (storedOtp != null && storedOtp.equals(request.getOtp())) {
            String accessToken = jwtUtil.generateAccessToken(request.getMobile());
            String refreshToken = jwtUtil.generateRefreshToken(request.getMobile());

            otpStore.remove(request.getMobile()); // clear used OTP
            return new TokenResponse(accessToken, refreshToken, "SUCCESS");
        }
        return new TokenResponse("INVALID");
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody RefreshRequest request) {
        String userId = jwtUtil.validateToken(request.getRefreshToken());
        if (userId != null) {
            String newAccessToken = jwtUtil.generateAccessToken(userId);
            return new TokenResponse(newAccessToken, request.getRefreshToken(), "SUCCESS");
        }

        return new TokenResponse("INVALID");
    }
}

