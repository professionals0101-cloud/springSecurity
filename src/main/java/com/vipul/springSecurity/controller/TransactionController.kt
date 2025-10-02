package com.vipul.springSecurity.controller

import com.vipul.springSecurity.dto.TransactionDto
import com.vipul.springSecurity.request.ExpenseRequest
import com.vipul.springSecurity.response.OperationResponse
import com.vipul.springSecurity.service.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping("/api/expenses")
class TransactionController(
    val transactionService: TransactionService
) {

    /**must be admin or member for this **/
    @PostMapping
    fun createExpense(@AuthenticationPrincipal principal : Jwt, @RequestBody expenseRequest : ExpenseRequest) : ResponseEntity<OperationResponse>{
        val userId = principal.subject.toLong()
        transactionService.createExpense(userId = userId, expenseRequest = expenseRequest)
        return ResponseEntity.ok(OperationResponse(true))
    }

    @GetMapping("/{groupId}")
    fun getAllExpenses(@AuthenticationPrincipal principal : Jwt, @PathVariable groupId : Long) : ResponseEntity<List<TransactionDto>>{
        val userId = principal.subject.toLong()
        return ResponseEntity.ok(transactionService.getAllExpenses(userId = userId, groupId = groupId))
    }
}