package com.vipul.springSecurity.dto;

import org.springframework.stereotype.Component;

@Component
public class VerifyOtpRequest {
    private Long mobile;
    private String otp;

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
