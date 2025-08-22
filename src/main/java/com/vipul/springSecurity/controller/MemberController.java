package com.vipul.springSecurity.controller;

import com.vipul.springSecurity.response.OperationResponse;
import com.vipul.springSecurity.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemberController{

    private MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/test")
    public String test(HttpServletRequest servletRequest){
        return "Logged In "+servletRequest.getSession().getId();
    }

    @PatchMapping("/members")
    public ResponseEntity<OperationResponse> updateMemberField(@AuthenticationPrincipal Jwt principal,
                                                               @RequestBody Map<String, Object> fields){
        return ResponseEntity.ok(memberService.updateMember(fields, Long.parseLong(principal.getSubject())));
    }

}
