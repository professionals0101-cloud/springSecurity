package com.vipul.springSecurity.mapper

import com.vipul.springSecurity.model.TransactionDtl
import com.vipul.springSecurity.model.TransactionShare
import com.vipul.springSecurity.request.ExpenseRequest
import org.springframework.stereotype.Component
import kotlin.math.exp

@Component
class TransactionMapper {

    fun mapToTransaction(userId: Long, request: ExpenseRequest) : TransactionDtl {
        return TransactionDtl(
            amount = request.amount,
            payer_id = request.paidBy,
            createdBy = userId,
            description = request.description,
            groupId = request.groupId
        )
    }

    fun mapToTransactionShare(userId: Long, transactionId :Long,  expenseRequest: ExpenseRequest) : List<TransactionShare> {
        return expenseRequest.memberShares.map { memberShare -> TransactionShare(
            transactionId = transactionId,
            memberId = memberShare.memberId,
            shareAmount = memberShare.amount
        ) }
    }
}