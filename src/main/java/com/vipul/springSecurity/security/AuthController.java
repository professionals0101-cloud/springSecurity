package com.vipul.springSecurity.security;
import com.vipul.springSecurity.dto.OtpRequest;
import com.vipul.springSecurity.dto.VerifyOtpRequest;
import com.vipul.springSecurity.model.MemberProfile;
import com.vipul.springSecurity.repo.MemberRepo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    MemberRepo memberRepository;

    private final JwtUtil jwtUtil = new JwtUtil();

    // Mock OTP store (in real life, use DB or cache like Redis)
    private final Map<Long, String> otpStore = new HashMap<>();

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
            Optional<MemberProfile> member = memberRepository.findByMobileNumber(request.getMobile());
            MemberProfile finalMember;
            if(!member.isPresent()) {
                finalMember = memberRepository.save(MemberProfile.withMobile(request.getMobile()));
            }else{
                finalMember = member.get();
            }
            String accessToken = jwtUtil.generateAccessToken(request.getMobile(), finalMember.getMemberId());
            String refreshToken = jwtUtil.generateRefreshToken(request.getMobile(), finalMember.getMemberId());
            otpStore.remove(request.getMobile()); // clear used OTP
            return new TokenResponse(accessToken, refreshToken, "SUCCESS");
        }
        return new TokenResponse("INVALID");
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody RefreshRequest request) {
        Claims claims = jwtUtil.validateToken(request.getRefreshToken());
        Long userId = Long.valueOf(claims.getSubject());
        Long mobile = (Long) claims.get("mobile");
        String newAccessToken = jwtUtil.generateAccessToken(mobile, userId);
        return new TokenResponse(newAccessToken, request.getRefreshToken(), "SUCCESS");
    }
}

