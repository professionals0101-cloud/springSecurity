package com.vipul.springSecurity.controller;

import com.vipul.springSecurity.response.OperationResponse;
import com.vipul.springSecurity.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
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

    @PatchMapping("/members/{id}")
    public ResponseEntity<OperationResponse> updateMemberField(@PathVariable Long id,
                                                               @RequestBody Map<String, Object> fields){
        return ResponseEntity.ok(memberService.updateMember(fields, id));
    }

}
