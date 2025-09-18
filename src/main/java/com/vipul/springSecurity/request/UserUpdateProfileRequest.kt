package com.vipul.springSecurity.request

data class UserUpdateProfileRequest(
    val memberName: String,
    val memberEmail: String,
    val memberAvatar: String,
    val mobile: Long,
)
