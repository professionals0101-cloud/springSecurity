package com.vipul.springSecurity.dto

import java.math.BigDecimal

data class GroupDto(
    val groupId: Long? = null,
    val groupName: String,
    val groupAmount: BigDecimal? = BigDecimal.ZERO,
    val groupBalance: BigDecimal? = BigDecimal.ZERO,
    val groupCurrency: String = "INR",
    val usageType: String? = null,
    val category: String? = null
)