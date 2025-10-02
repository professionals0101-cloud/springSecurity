package com.vipul.springSecurity.dto

import java.math.BigDecimal

data class TransactionDto(
    val transactionId: Long? = null,
    val payer: String,
    val amount: Double,
    val description: String? = null,
    val userPayable : Double?
)