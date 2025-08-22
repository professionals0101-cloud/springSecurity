package com.vipul.springSecurity.dto

data class MemberProfileDto @JvmOverloads constructor(
    val memberId: Long? = null,
    val memberName: String? = null,
    val mobile: Long,
    val relation: String? = null,
    val email: String? = null,
    val avatarUrl: String? = null
)