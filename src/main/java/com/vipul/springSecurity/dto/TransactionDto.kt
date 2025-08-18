package com.vipul.springSecurity.dto

import java.math.BigDecimal

data class TransactionDto(
    val transactionId: Long? = null,
    val walletId: Long,
    val payerId: Long,
    val amount: BigDecimal,
    val transactionType: String,
    val status: String,
    val description: String? = null,
    val includedMembers: List<Long>? = null
)