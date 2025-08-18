package com.vipul.springSecurity.dto

data class MemberProfileDto(
    val memberId: Long? = null,
    val memberName: String,
    val mobile: String,
    val relation: String? = null,
    val email: String? = null,
    val avatarUrl: String? = null
)