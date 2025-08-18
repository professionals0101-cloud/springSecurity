package com.vipul.springSecurity.dto

data class WalletPartnerRelationDto(
    val walletId: Long,
    val memberId: Long,
    val role: String,
    val isAdmin: Boolean = false
)